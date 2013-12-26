package net.canaydogan.umbrella;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import net.canaydogan.umbrella.handler.HttpHandler;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;
import net.canaydogan.umbrella.util.DefaultHttpResponse;
import net.canaydogan.umbrella.util.HttpResponseBuilder;
import net.canaydogan.umbrella.wrapper.HttpRequestWrapper;

class UmbrellaServerHandler extends ChannelInboundHandlerAdapter {

    private FullHttpRequest request;
    
    protected HttpHandlerContext context;
    
    protected HttpHandler httpHandler;
    
    public UmbrellaServerHandler(HttpHandler httpHandler) {
    	this.httpHandler = httpHandler;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private boolean writeStandardResponse(ChannelHandlerContext ctx) {
        // Decide whether to close the connection or not.
        boolean keepAlive = isKeepAlive(request);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        HttpResponseBuilder.build(response, context.getResponse());

        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");        

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // Write the response.
        ctx.write(response);

        System.out.println(keepAlive);
        return keepAlive;
    }
    
    private void writeFileResponse(ChannelHandlerContext ctx) {
    	boolean keepAlive = isKeepAlive(request);
    	
    	if (keepAlive) {
            context.getResponse().getHeaderCollection().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
    	
        // Write the initial line and the header.
        ctx.write(
    		HttpResponseBuilder.build(new io.netty.handler.codec.http.DefaultHttpResponse(HTTP_1_1, OK), context.getResponse())
		);

        ctx.write(context.getResponse().getContent(), ctx.newProgressivePromise());
        
        context.getResponse().setContent(null);
        
        // Write the end marker
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

        // Decide whether to close the connection or not.
        if (!keepAlive) {
            // Close the connection when the whole content is written out.
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }    	
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    
    protected HttpHandlerContext buildHttpHandlerContext(HttpRequest nettyRequest) {    	
    	return new DefaultHttpHandlerContext(
			new HttpRequestWrapper(nettyRequest), 
			new DefaultHttpResponse()
		);
    }
    
    protected void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        context = buildHttpHandlerContext(request);

        //Buna gerek olmayabilir. HttpObjectAggregator'da da bu kontrol var.
        if (is100ContinueExpected(request)) {
            send100Continue(ctx);
        }
        
        ByteBuf content = request.content();
        
        if (content.isReadable()) {
        	context.getRequest().setContent(content.toString(CharsetUtil.UTF_8));
        }            
        
        httpHandler.handleHttpRequest(context);
        
        if (context.getResponse().getContent() instanceof ChunkedFile
    		|| context.getResponse().getContent() instanceof DefaultFileRegion) {
        	writeFileResponse(ctx);
        } else {
        	writeStandardResponse(ctx);	
        }                
    }

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("channel read..");
		if (msg instanceof FullHttpRequest) {
			request = (FullHttpRequest) msg;
            handleHttpRequest(ctx, request);
        } else if (msg instanceof WebSocketFrame) {
            //handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        } else {
            ReferenceCountUtil.release(msg);
        }
		super.channelRead(ctx, msg);
	}
}