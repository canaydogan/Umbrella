package net.canaydogan.umbrella.wrapper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.net.HttpCookie;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import org.junit.Before;
import org.junit.Test;

public class FullHttpResponseWrapperTest {

	protected FullHttpResponseWrapper response;
	
	protected io.netty.handler.codec.http.FullHttpResponse nettyResponse;
	
	@Before
	public void setUp() {
		nettyResponse = mock(io.netty.handler.codec.http.FullHttpResponse.class);
		response = new FullHttpResponseWrapper(nettyResponse);
	}
	
	@Test
	public void testSetAndGetContent() {		
		response.setContent("My Content");
		
		assertEquals("My Content", response.getContent());
	}
	
	@Test
	public void testGetHeaderCollection() {		
		HttpHeaders httpHeaders = mock(HttpHeaders.class);
		when(nettyResponse.headers()).thenReturn(httpHeaders);
		when(httpHeaders.get("name")).thenReturn("value");
		response = new FullHttpResponseWrapper(nettyResponse);
		
		assertEquals("value", response.getHeaderCollection().get("name"));
	}
	
	@Test
	public void testFinishForContent() {
		ByteBuf byteBuf = Unpooled.buffer();
		when(nettyResponse.content()).thenReturn(byteBuf);
		response = new FullHttpResponseWrapper(nettyResponse);		
		response.setContent("My Content");		
		
		assertEquals(10, byteBuf.readableBytes());
	}
	
	@Test
	public void testFinishForCookie() {
		response = new FullHttpResponseWrapper(new DefaultFullHttpResponse(
			HttpVersion.HTTP_1_1, 
			HttpResponseStatus.ACCEPTED
		));
		
		HttpCookie cookie1 = new HttpCookie("cookie1", "value1");
		HttpCookie cookie2 = new HttpCookie("cookie2", "value2");
		response.getCookieCollection().add(cookie1);
		response.getCookieCollection().add(cookie2);		
		
		List<String> cookieList = response.getHeaderCollection().getAll(HttpHeaders.Names.SET_COOKIE);
		
		assertEquals(2, cookieList.size());
		assertEquals("cookie1=value1; Max-Age=-1; Version=1", cookieList.get(0));
		assertEquals("cookie2=value2; Max-Age=-1; Version=1", cookieList.get(1));
	}
	
	@Test
	public void testGetNettyResponse() {
		assertSame(nettyResponse, response.getNettyResponse());
	}
	
	@Test
	public void testGetCookieCollection() {
		assertNotNull(response.getCookieCollection());
	}
	
}
