package net.canaydogan.umbrella.wrapper;

import org.junit.Before;
import org.junit.Test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import net.canaydogan.umbrella.HttpRequest;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class WebSocketChannelHandlerContextWrapperTest {

	protected WebSocketChannelHandlerContextWrapper context;
	
	protected HttpRequest request;
	
	protected ChannelHandlerContext ctx;
	
	protected WebSocketServerHandshaker handshaker;
	
	@Before
	public void setUp() {
		ctx = mock(ChannelHandlerContext.class);
		handshaker = mock(WebSocketServerHandshaker.class);
		request = mock(HttpRequest.class);
		context = new WebSocketChannelHandlerContextWrapper(ctx, handshaker, request);
	}
	
	@Test
	public void testSetterAndGetter() {
		assertEquals(request, context.getRequest());
	}
	
	@Test
	public void testSendWithString() {
		context.send("message");
		verify(ctx, times(1)).writeAndFlush(any(TextWebSocketFrame.class), any(ChannelPromise.class));
	}
	
	@Test
	public void testSendWithByte() {
		byte[] message = {'a'};
		context.send(message);
		verify(ctx, times(1)).writeAndFlush(any(BinaryWebSocketFrame.class), any(ChannelPromise.class));
	}
	
	@Test
	public void testPingWithByte() {
		byte[] message = {'a'};
		context.ping(message);
		verify(ctx, times(1)).writeAndFlush(any(PingWebSocketFrame.class), any(ChannelPromise.class));
	}
	
	@Test
	public void testPongWithByte() {
		byte[] message = {'a'};
		context.pong(message);
		verify(ctx, times(1)).writeAndFlush(any(PongWebSocketFrame.class), any(ChannelPromise.class));
	}
	
	@Test
	public void testClose() {
		context.close();
		verify(handshaker, times(1)).close(eq(ctx.channel()), any(CloseWebSocketFrame.class));
	}
	
	public void testCloseWithCloseWebSocketFrame() {
		CloseWebSocketFrame frame = new CloseWebSocketFrame();
		context.close(frame);
		verify(handshaker, times(1)).close(ctx.channel(), frame);
	}
	
}
