package net.canaydogan.umbrella.util;

import static org.junit.Assert.*;

import java.net.HttpCookie;
import java.util.List;

import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.util.DefaultHttpCookieCollection;

import org.junit.Before;
import org.junit.Test;

public class DefaultHttpCookieCollectionTest {

	protected HttpCookieCollection collection;
	
	@Before
	public void setUp() {
		collection = new DefaultHttpCookieCollection();
	}
	
	@Test
	public void testAddAndGetAndRemoveCookie() {
		HttpCookie cookie = new HttpCookie("name", "value");
		collection.add(cookie);
		
		assertEquals(cookie, collection.get("name"));
		
		assertTrue(collection.remove(cookie));
		assertNull(collection.get("name"));
	}
	
	@Test
	public void testConstructorWithEmptyString() {
		new DefaultHttpCookieCollection("");		
	}
	
	@Test
	public void testConstructorWithNull() {
		new DefaultHttpCookieCollection(null);		
	}
	
	@Test
	public void testConstructorWithStringCookie() {
		HttpCookieCollection collection = new DefaultHttpCookieCollection("key1=value1;key2=value2");
		
		HttpCookie key1 = collection.get("key1");
		HttpCookie key2 = collection.get("key2");
		
		assertEquals("key1", key1.getName());
		assertEquals("value1", key1.getValue());
		assertEquals("key2", key2.getName());
		assertEquals("value2", key2.getValue());
	}
	
	@Test
	public void testToStringListWithNoCookie() {
		List<String> list = collection.toStringList();
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testToStringListWithTwoCookie() {
		HttpCookie cookie1 = new HttpCookie("cookie1", "value1");
		HttpCookie cookie2 = new HttpCookie("cookie2", "value2");
		cookie2.setMaxAge(11);
		cookie2.setPath("/path");
		cookie2.setDomain("www.example.com");
		cookie2.setHttpOnly(true);
		cookie2.setSecure(true);
		cookie2.setVersion(1);
		
		collection.add(cookie1);
		collection.add(cookie2);
		
		List<String> list = collection.toStringList();
		
		assertEquals(2, list.size());
		assertEquals("cookie1=value1; Max-Age=-1; Version=1", list.get(0));
		assertEquals("cookie2=value2; Max-Age=11; Path=\"/path\"; Domain=www.example.com; Secure; HTTPOnly; Version=1", list.get(1));
	}
	
	
	
}