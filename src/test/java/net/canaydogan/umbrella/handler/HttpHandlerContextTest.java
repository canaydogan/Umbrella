package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.router.RouteMatch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HttpHandlerContextTest {

	private HttpHandlerContext context;
	
	@Before
	public void setUp() {
		HttpRequest request = mock(HttpRequest.class);
		HttpResponse response = mock(HttpResponse.class);
		context = new HttpHandlerContext(request, response);
	}
	
	@Test
	public void testSetterAndGetter() {
		HttpRequest request = mock(HttpRequest.class);
		HttpResponse response = mock(HttpResponse.class);
		RouteMatch routeMatch = new RouteMatch();
		Exception exception = new Exception();
		
		context.setRequest(request);
		context.setResponse(response);
		context.setRouteMatch(routeMatch);
		context.setException(exception);
		
		assertEquals(request, context.getRequest());
		assertEquals(response, context.getResponse());
		assertEquals(routeMatch, context.getRouteMatch());
		assertSame(exception, context.getException());
	}
	
	@Test
	public void testHasException() {
		assertFalse(context.hasException());
		
		context.setException(new Exception());
		assertTrue(context.hasException());
	}
	
}