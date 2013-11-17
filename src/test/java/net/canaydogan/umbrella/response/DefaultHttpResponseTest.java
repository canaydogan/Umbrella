package net.canaydogan.umbrella.response;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import net.canaydogan.umbrella.response.DefaultHttpResponse;
import net.canaydogan.umbrella.response.HttpResponse;

public class DefaultHttpResponseTest {

	protected HttpResponse response;
	
	@Before
	public void setUp() {
		response = new DefaultHttpResponse();
	}
	
	@Test
	public void testSetterAndGetter() {
		response.setContent("content");
		
		assertEquals("content", response.getContent());
	}
	
}
