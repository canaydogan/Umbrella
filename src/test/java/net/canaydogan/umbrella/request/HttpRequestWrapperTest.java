package net.canaydogan.umbrella.request;

import io.netty.handler.codec.http.HttpMethod;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class HttpRequestWrapperTest {

	protected HttpRequest request;
	
	protected io.netty.handler.codec.http.HttpRequest nettyRequest;
	
	@Before
	public void setUp() {
		nettyRequest = mock(io.netty.handler.codec.http.HttpRequest.class);
		request = new HttpRequestWrapper(nettyRequest);
	}
	
	@Test
	public void testGetUri() {		
		when(nettyRequest.getUri()).thenReturn("/uri");					
		assertEquals("/uri", request.getUri());		
	}
	
	@Test
	public void testGetMethod() {
		//GET
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.GET);		
		assertEquals(HttpRequest.Method.GET, request.getMethod());
		
		//POST
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.POST);		
		assertEquals(HttpRequest.Method.POST, request.getMethod());
		
		//PUT
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.PUT);		
		assertEquals(HttpRequest.Method.PUT, request.getMethod());

		//DELETE
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.DELETE);		
		assertEquals(HttpRequest.Method.DELETE, request.getMethod());
		
		//HEAD
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.HEAD);		
		assertEquals(HttpRequest.Method.HEAD, request.getMethod());
		
		//PATCH
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.PATCH);		
		assertEquals(HttpRequest.Method.PATCH, request.getMethod());
		
		//OPTONS
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.OPTIONS);		
		assertEquals(HttpRequest.Method.OPTIONS, request.getMethod());
		
		//CONNECT
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.CONNECT);		
		assertEquals(HttpRequest.Method.CONNECT, request.getMethod());
		
		//CONNECT
		when(nettyRequest.getMethod()).thenReturn(HttpMethod.TRACE);		
		assertEquals(HttpRequest.Method.TRACE, request.getMethod());
		
		//NULL
		when(nettyRequest.getMethod()).thenReturn(null);		
		assertEquals(null, request.getMethod());
	}
	
}
