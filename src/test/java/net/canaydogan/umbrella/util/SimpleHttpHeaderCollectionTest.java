package net.canaydogan.umbrella.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import net.canaydogan.umbrella.HttpHeaderCollection;

public class SimpleHttpHeaderCollectionTest {

	protected HttpHeaderCollection collection;
	
	@Before
	public void setUp() {
		collection = new SimpleHttpHeaderCollection();
	}
	
	@Test
	public void testContains() {
		assertFalse(collection.contains("name"));
		collection.set("name", "value");
		assertTrue(collection.contains("name"));
	}
	
	@Test
	public void testGet() {
		assertNull(collection.get("name"));
		collection.set("name", "value");
		assertEquals("value", collection.get("name"));
	}
	
	@Test
	public void testSet() {
		collection.set("name", "value");
		assertEquals("value", collection.get("name"));
	}
	
	@Test
	public void testGetAll() {
		assertEquals(0, collection.getAll("name").size());
		
		collection.add("name", "value");
		assertEquals(1, collection.getAll("name").size());
	}
	
	@Test
	public void testAdd() {
		collection.add("name", "value");		
		assertEquals("value", collection.get("name"));
		
		collection.add("name", "value2");
		assertEquals(2, collection.getAll("name").size());
	}
	
	@Test
	public void testNameSet() {
		collection.add("name", "value");
		
		assertEquals(1, collection.nameSet().size());
		assertEquals("name", collection.nameSet().iterator().next());
	}
	
}
