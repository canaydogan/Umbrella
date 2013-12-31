package net.canaydogan.umbrella.util;

import static org.junit.Assert.*;
import net.canaydogan.umbrella.HttpResponse;

import org.junit.Before;
import org.junit.Test;

public class DefaultHttpResponseTest {

	protected HttpResponse response;
	
	@Before
	public void setUp() {
		response = new DefaultHttpResponse();
	}
	
	@Test
	public void testCreation() {
		assertNotNull(response.getHeaderCollection());
		assertNotNull(response.getCookieCollection());
		assertEquals(HttpResponse.Status.NOT_FOUND, response.getStatus());
	}
	
	@Test
	public void testContent() {
		response.setContent("content");
		
		assertEquals("content", response.getContent());
	}
	
	@Test
	public void testSetAndGetStatus() {
		response.setStatus(HttpResponse.Status.NOT_FOUND);
		
		assertEquals(HttpResponse.Status.NOT_FOUND, response.getStatus());
	}
	
}
