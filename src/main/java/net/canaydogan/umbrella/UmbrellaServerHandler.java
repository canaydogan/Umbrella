package net.canaydogan.umbrella;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static io.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

import java.util.Set;

import net.canaydogan.umbrella.handler.HttpHandler;
import net.canaydogan.umbrella.handler.HttpHandlerContext;
import net.canaydogan.umbrella.wrapper.FullHttpResponseWrapper;
import net.canaydogan.umbrella.wrapper.HttpRequestWrapper;

class UmbrellaServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpRequest request;
    
    protected HttpHandlerContext context;
    
    protected HttpHandler httpHandler;
    
    public UmbrellaServerHandler(HttpHandler httpHandler) {
    	this.httpHandler = httpHandler;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx) {
        // Decide whether to close the connection or not.
        boolean keepAlive = isKeepAlive(request);
        // Build the response object.
        FullHttpResponse response = ((FullHttpResponseWrapper) context.getResponse()).getNettyResponse();

        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");        

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        // Encode the cookie.
        String cookieString = request.headers().get(COOKIE);
        if (cookieString != null) {
            Set<Cookie> cookies = CookieDecoder.decode(cookieString);
            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                for (Cookie cookie: cookies) {
                    response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
                }
            }
        } else {
            // Browser sent no cookie.  Add some.
            response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
            response.headers().add(SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));
        }

        // Write the response.
        ctx.write(response);

        return keepAlive;
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
    	return new HttpHandlerContext(
			new HttpRequestWrapper(nettyRequest), 
			new FullHttpResponseWrapper(new DefaultFullHttpResponse(HTTP_1_1, OK))
		);
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {			
            HttpRequest request = this.request = (HttpRequest) msg;
            context = buildHttpHandlerContext(request);            

            if (is100ContinueExpected(request)) {
                send100Continue(ctx);
            }

        }

        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            
            if (content.isReadable()) {
            	context.getRequest().setContent(content.toString(CharsetUtil.UTF_8));
            }            
            
            if (msg instanceof LastHttpContent) {
                LastHttpContent trailer = (LastHttpContent) msg;                
                httpHandler.handleHttpRequest(context);
                context.getResponse().finish();
                writeResponse(trailer, ctx);
            }
        }		
	}
}