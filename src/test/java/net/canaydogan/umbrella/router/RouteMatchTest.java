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
		routeMatch.set("name", "value");
		
		assertEquals("value", routeMatch.get("name"));
	}
	
	@Test
	public void testGetParamForNull() {
		assertNull(routeMatch.get("name"));
	}
	
	@Test
	public void testSetParams() {
		Map<String, String> params = new HashMap<>();
		params.put("key1", "value1");
		params.put("key2", "value2");
		
		routeMatch.setAll(params);
		
		assertEquals("value1", routeMatch.get("key1"));
		assertEquals("value2", routeMatch.get("key2"));
	}
	
	@Test
	public void testConstructorWithParams() {
		Map<String, String> params = new HashMap<>();
		params.put("key1", "value1");
		params.put("key2", "value2");
				
		routeMatch = new RouteMatch(params);
		
		assertEquals("value1", routeMatch.get("key1"));
		assertEquals("value2", routeMatch.get("key2"));
	}
	
	@Test
	public void testContains() {
		assertFalse(routeMatch.contains("key"));
		routeMatch.set("key", "value");	
		assertTrue(routeMatch.contains("key"));
	}
	
	@Test
	public void testGetInt() {
		routeMatch.set("int", "1");
		assertEquals((Integer) 1, routeMatch.getInt("int"));
		
		routeMatch.set("int", "1a");
		assertNull(routeMatch.getInt("int"));
	}

}