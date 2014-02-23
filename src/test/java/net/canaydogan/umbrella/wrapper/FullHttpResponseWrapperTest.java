package net.canaydogan.umbrella.wrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.netty.handler.codec.http.HttpHeaders;

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
	public void testGetNettyResponse() {
		assertSame(nettyResponse, response.getNettyResponse());
	}
	
	@Test
	public void testGetCookieCollection() {
		assertNotNull(response.getCookieCollection());
	}
	
}
