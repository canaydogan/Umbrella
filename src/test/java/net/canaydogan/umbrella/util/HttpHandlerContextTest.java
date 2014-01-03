package net.canaydogan.umbrella.util;

import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HttpHandlerContextTest {

	private DefaultHttpHandlerContext context;
	
	@Before
	public void setUp() {
		HttpRequest request = mock(HttpRequest.class);
		HttpResponse response = mock(HttpResponse.class);
		context = new DefaultHttpHandlerContext(request, response);
	}
	
	@Test
	public void testSetterAndGetter() {
		HttpRequest request = mock(HttpRequest.class);
		HttpResponse response = mock(HttpResponse.class);
		Exception exception = new Exception();
		
		context.setRequest(request);
		context.setResponse(response);
		context.setException(exception);
		
		assertEquals(request, context.getRequest());
		assertEquals(response, context.getResponse());
		assertSame(exception, context.getException());
	}
	
	@Test
	public void testHasException() {
		assertFalse(context.hasException());
		
		context.setException(new Exception());
		assertTrue(context.hasException());
	}
	
}