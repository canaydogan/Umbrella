package net.canaydogan.umbrella.router;

import java.util.HashMap;
import java.util.Map;

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
	
	@Test
	public void testSetParams() {
		Map<String, String> params = new HashMap<>();
		params.put("key1", "value1");
		params.put("key2", "value2");
		
		routeMatch.setParams(params);
		
		assertEquals("value1", routeMatch.getParam("key1"));
		assertEquals("value2", routeMatch.getParam("key2"));
	}
	
	@Test
	public void testConstructorWithParams() {
		Map<String, String> params = new HashMap<>();
		params.put("key1", "value1");
		params.put("key2", "value2");
				
		routeMatch = new RouteMatch(params);
		
		assertEquals("value1", routeMatch.getParam("key1"));
		assertEquals("value2", routeMatch.getParam("key2"));
	}

}
