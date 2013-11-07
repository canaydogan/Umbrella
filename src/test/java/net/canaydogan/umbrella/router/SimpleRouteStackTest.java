package net.canaydogan.umbrella.router;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class SimpleRouteStackTest {

	private RouteStack routeStack;
	
	@Before
	public void setUp() {
		routeStack = new SimpleRouteStack();
	}
	
	@Test
	public void matchForSuccess() {
		RouteMatch routeMatch = mock(RouteMatch.class);
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(routeMatch);
		
		routeStack.addRoute("default", route);
		
		RouteMatch result = routeStack.match(null);
		
		assertNotNull(result);
		assertEquals(routeMatch, result);
	}
	
	@Test
	public void matchForUnsuccess() {
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(null);
		
		routeStack.addRoute("default", route);
		
		RouteMatch result = routeStack.match(null);
		
		assertNull(result);
	}
	
	@Test
	public void matchWithNoRoute() {
		assertNull(routeStack.match(null));
	}
	
	@Test
	public void remove() {
		RouteMatch routeMatch = mock(RouteMatch.class);
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(routeMatch);
		
		routeStack.addRoute("default", route);
		routeStack.removeRoute("default");
		
		assertNull(routeStack.match(null));
	}
	
	
}
