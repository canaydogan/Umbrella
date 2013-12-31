package net.canaydogan.umbrella.wrapper;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.WebSocketHandlerContext;

public class WebSocketChannelHandlerContextWrapper implements WebSocketHandlerContext {

	protected ChannelHandlerContext context;
	
	protected HttpRequest request;
	
	protected WebSocketServerHandshaker handshaker;
	
	public WebSocketChannelHandlerContextWrapper(
			ChannelHandlerContext context, 
			WebSocketServerHandshaker handshaker, 
			HttpRequest request) {
		this.context = context;
		this.handshaker = handshaker;
		this.request = request;
	}
	
	@Override
	public HttpRequest getRequest() {
		return request;
	}

	@Override
	public WebSocketHandlerContext send(String message) {
		context.writeAndFlush(new TextWebSocketFrame(message));
		return this;
	}

	@Override
	public WebSocketHandlerContext send(byte[] message) {
		context.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(message)));
		return this;
	}

	@Override
	public WebSocketHandlerContext ping(byte[] message) {
		context.writeAndFlush(new PingWebSocketFrame(Unpooled.wrappedBuffer(message)));
		return this;
	}

	@Override
	public WebSocketHandlerContext pong(byte[] message) {
		context.writeAndFlush(new PongWebSocketFrame(Unpooled.wrappedBuffer(message)));
		return this;
	}

	@Override
	public WebSocketHandlerContext close() {
		close(new CloseWebSocketFrame());
		return this;
	}

	public WebSocketHandlerContext close(CloseWebSocketFrame frame) {
		handshaker.close(context.channel(), frame);
		return this;
	}
	

}