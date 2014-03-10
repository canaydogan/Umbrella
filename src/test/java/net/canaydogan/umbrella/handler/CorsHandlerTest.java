package net.canaydogan.umbrella.handler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpRequest.Method;
import net.canaydogan.umbrella.HttpResponse;

import org.junit.Before;
import org.junit.Test;

public class CorsHandlerTest {

	protected HttpHandler handler;

	protected HttpHandlerContext context;

	@Before
	public void setUp() throws Exception {
		handler = new CorsHandler("*", "PUT");
		context = mock(HttpHandlerContext.class);
		when(context.getResponse()).thenReturn(mock(HttpResponse.class));
		when(context.getRequest()).thenReturn(mock(HttpRequest.class));
		when(context.getRequest().getHeaderCollection()).thenReturn(
				mock(HttpHeaderCollection.class));
		when(context.getResponse().getHeaderCollection()).thenReturn(
				mock(HttpHeaderCollection.class));
	}

	@Test
	public void testHandleHttpRequestWithOptionsMethod() throws Exception {
		HttpHeaderCollection requestHc = context.getRequest()
				.getHeaderCollection();
		HttpHeaderCollection responseHc = context.getResponse()
				.getHeaderCollection();
		String headers = "X-Custom-Header";
		
		when(context.getRequest().getMethod()).thenReturn(Method.OPTIONS);
		when(requestHc.get(CorsHandler.REQUEST_HEADERS)).thenReturn(headers);
		when(requestHc.get(CorsHandler.REQUEST_METHOD)).thenReturn("PUT");
		when(requestHc.contains(CorsHandler.REQUEST_HEADERS)).thenReturn(true);
		when(requestHc.contains(CorsHandler.REQUEST_METHOD)).thenReturn(true);

		assertTrue(handler.handleHttpRequest(context));
		verify(responseHc, times(1)).add(CorsHandler.ALLOW_ORIGIN, "*");
		verify(responseHc, times(1)).add(CorsHandler.ALLOW_METHODS, "PUT");
		verify(responseHc, times(1)).add(CorsHandler.ALLOW_HEADERS, headers);
	}
	
	@Test
	public void testHandleHttpRequestWithGetMethod() throws Exception {
		HttpHeaderCollection responseHc = context.getResponse()
				.getHeaderCollection();
		
		when(context.getRequest().getMethod()).thenReturn(Method.GET);

		assertFalse(handler.handleHttpRequest(context));
		verify(responseHc, times(1)).add(CorsHandler.ALLOW_ORIGIN, "*");
	}
}
