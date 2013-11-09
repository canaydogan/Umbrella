package net.canaydogan.umbrella.router;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SimpleRouteStackTest {

	private RouteStack routeStack;
	
	@Before
	public void setUp() {
		routeStack = new SimpleRouteStack();
	}
	
	@Test
	public void testAddAndRemoveRoute() throws Exception {
		Map<String, Route> data = (Map<String, Route>) routeStack.getClass().getDeclaredField("routeMap").get(routeStack);
		
		routeStack.addRoute("route", null);
		assertEquals(1, data.size());
		
		routeStack.removeRoute("route");
		assertEquals(0, data.size());
	}
	
	@Test
	public void testMatchForSuccess() {
		RouteMatch routeMatch = mock(RouteMatch.class);
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(routeMatch);
		
		routeStack.addRoute("default", route);
		
		RouteMatch result = routeStack.match(null);
		
		assertNotNull(result);
		assertEquals(routeMatch, result);
	}
	
	@Test
	public void testMatchForUnsuccess() {
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(null);
		
		routeStack.addRoute("default", route);
		
		RouteMatch result = routeStack.match(null);
		
		assertNull(result);
	}
	
	@Test
	public void testMatchWithNoRoute() {
		assertNull(routeStack.match(null));
	}	
	
}