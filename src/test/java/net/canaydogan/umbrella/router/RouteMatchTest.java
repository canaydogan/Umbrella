package net.canaydogan.umbrella.router;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RouteMatchTest {
	
	private RouteMatch routeMatch;
	
	@Before
	public void setUp() {
		routeMatch = new RouteMatch();
	}
	
	@Test
	public void testSetAndGetParam() {
		routeMatch.setParam("name", "value");
		
		assertEquals("value", routeMatch.getParam("name"));
	}
	
	@Test
	public void testGetParamForNull() {
		assertNull(routeMatch.getParam("name"));
	}

}
