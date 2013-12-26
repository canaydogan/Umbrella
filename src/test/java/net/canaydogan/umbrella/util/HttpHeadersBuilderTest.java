package net.canaydogan.umbrella.util;

import static org.junit.Assert.*;

import java.net.HttpCookie;
import java.util.List;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import net.canaydogan.umbrella.HttpCookieCollection;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.util.DefaultHttpCookieCollection;
import net.canaydogan.umbrella.util.SimpleHttpHeaderCollection;

import org.junit.Test;

public class HttpHeadersBuilderTest {

	@Test
	public void testBuildWithHttpHeaderCollection() {
		HttpHeaders foundation = new DefaultHttpHeaders();
		HttpHeaderCollection collection = new SimpleHttpHeaderCollection();
		collection.add("name1", "value1");
		collection.add("name1", "value2");
		collection.add("name2", "value3");
		
		HttpHeadersBuilder.build(foundation, collection);
		
		assertEquals("value1", foundation.getAll("name1").get(0));
		assertEquals("value2", foundation.getAll("name1").get(1));
		assertEquals("value3", foundation.get("name2"));
	}
	
	@Test
	public void testBuildWithHttpCookieCollection() {
		HttpHeaders foundation = new DefaultHttpHeaders();
		HttpCookieCollection collection = new DefaultHttpCookieCollection();		
		HttpCookie cookie1 = new HttpCookie("cookie1", "value1");
		HttpCookie cookie2 = new HttpCookie("cookie2", "value2");
		
		collection.add(cookie1);
		collection.add(cookie2);
		
		HttpHeadersBuilder.build(foundation, collection);
		
		List<String> cookieList = foundation.getAll(HttpHeaders.Names.SET_COOKIE);
		
		assertEquals(2, cookieList.size());
		assertEquals("cookie1=value1; Max-Age=-1; Version=1", cookieList.get(0));
		assertEquals("cookie2=value2; Max-Age=-1; Version=1", cookieList.get(1));
	}
	
}
