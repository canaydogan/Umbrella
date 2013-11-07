package net.canaydogan.umbrella.handler;

import io.netty.handler.codec.http.HttpResponse;
import net.canaydogan.umbrella.request.HttpRequest;
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
		
		context.setRequest(request);
		context.setResponse(response);
		context.setRouteMatch(routeMatch);
		
		assertEquals(request, context.getRequest());
		assertEquals(response, context.getResponse());
		assertEquals(routeMatch, context.getRouteMatch());
	}
	
}
