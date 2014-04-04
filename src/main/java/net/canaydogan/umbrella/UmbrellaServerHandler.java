package net.canaydogan.umbrella;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.HOST;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.ReferenceCountUtil;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;
import net.canaydogan.umbrella.util.DefaultHttpResponse;
import net.canaydogan.umbrella.util.HttpResponseBuilder;
import net.canaydogan.umbrella.wrapper.FullHttpRequestWrapper;
import net.canaydogan.umbrella.wrapper.WebSocketChannelHandlerContextWrapper;

class UmbrellaServerHandler extends ChannelInboundHandlerAdapter {

	protected final HttpHandler httpHandler;

	protected HttpHandlerContext context;

	protected WebSocketHandler webSocketHandler;

	protected WebSocketChannelHandlerContextWrapper webSocketHandlerContext;

	public UmbrellaServerHandler(HttpHandler httpHandler) {
		this.httpHandler = httpHandler;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			handleWebSocketFrame((WebSocketFrame) msg);
		} else {
			ReferenceCountUtil.release(msg);
		}
	}

	private void writeStandardResponse(ChannelHandlerContext ctx) {
		boolean keepAlive = context.getRequest().isKeepAlive();
		FullHttpResponse response = HttpResponseBuilder
				.createFullHttpResponse(context.getResponse());

		if (keepAlive) {
			response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			if (response.getStatus() != NOT_MODIFIED) {
				response.headers().set(CONTENT_LENGTH,
						response.content().readableBytes());
			}
		}

		if (!keepAlive) {
			ctx.writeAndFlush(response)
					.addListener(ChannelFutureListener.CLOSE);
		} else {
			ctx.writeAndFlush(response);
		}
	}

	private void writeFileResponse(ChannelHandlerContext ctx) {
		boolean keepAlive = context.getRequest().isKeepAlive();
		if (keepAlive) {
			context.getResponse().getHeaderCollection()
					.set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		// Write the initial line and the header.
		ctx.write(HttpResponseBuilder.createDefaultHttpResponse(context.getResponse()));

		// Write file
		ctx.write(context.getResponse().getContent(),
				ctx.newProgressivePromise());
		context.getResponse().setContent(null);

		if (!keepAlive) {
			ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(
					ChannelFutureListener.CLOSE);
		} else {
			ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		}
	}

	protected void handleHttpRequest(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		context = buildHttpHandlerContext(request, ctx);

		try {
			httpHandler.handleHttpRequest(context);
			if (context.getResponse().getContent() instanceof ChunkedFile
					|| context.getResponse().getContent() instanceof DefaultFileRegion) {
				writeFileResponse(ctx);
			} else if (context.getResponse().getContent() instanceof WebSocketHandler) {
				handshake(ctx, request);
			} else {
				writeStandardResponse(ctx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.release();
			context = null;
		}
	}

	private void handshake(ChannelHandlerContext ctx, FullHttpRequest request)
			throws Exception {
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				getWebSocketLocation(context), null, false);
		WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
		if (null == handshaker) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx
					.channel());
		} else {
			handshaker.handshake(ctx.channel(), request);

			webSocketHandlerContext = new WebSocketChannelHandlerContextWrapper(
					ctx, handshaker, context.getRequest());
			webSocketHandler = (WebSocketHandler) context.getResponse()
					.getContent();
			webSocketHandler.onOpen(webSocketHandlerContext);
			ctx.channel().closeFuture()
					.addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture future)
								throws Exception {
							webSocketHandler.onClose(webSocketHandlerContext);
						}
					});
		}
	}

	private void handleWebSocketFrame(WebSocketFrame frame) throws Exception {
		if (frame instanceof CloseWebSocketFrame) {
			webSocketHandlerContext.close((CloseWebSocketFrame) frame);
			return;
		} else if (frame instanceof PingWebSocketFrame) {
			webSocketHandler.onPing(webSocketHandlerContext,
					toByte(frame.content()));
			return;
		} else if (frame instanceof PongWebSocketFrame) {
			webSocketHandler.onPong(webSocketHandlerContext,
					toByte(frame.content()));
			return;
		} else if (frame instanceof BinaryWebSocketFrame) {
			webSocketHandler.onMessage(webSocketHandlerContext, frame.content()
					.array());
			return;
		} else if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", frame.getClass().getName()));
		}

		webSocketHandler.onMessage(webSocketHandlerContext,
				((TextWebSocketFrame) frame).text());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private static HttpHandlerContext buildHttpHandlerContext(
			FullHttpRequest nettyRequest, ChannelHandlerContext context) {
		return new DefaultHttpHandlerContext(new FullHttpRequestWrapper(
				nettyRequest, context.channel().remoteAddress()), new DefaultHttpResponse());
	}

	private static byte[] toByte(ByteBuf buf) {
		byte[] byteArray = new byte[buf.readableBytes()];
		buf.readBytes(byteArray);
		return byteArray;
	}

	private static String getWebSocketLocation(HttpHandlerContext context) {
		return "ws://" + context.getRequest().getHeaderCollection().get(HOST)
				+ context.getRequest().getUri();
	}

}