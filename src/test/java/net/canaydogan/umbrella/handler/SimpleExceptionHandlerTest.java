package net.canaydogan.umbrella.handler;

import org.junit.Before;
import org.junit.Test;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.HttpResponse.Status;
import net.canaydogan.umbrella.handler.exception.ForbiddenException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.handler.exception.NotFoundException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimpleExceptionHandlerTest {

	protected HttpHandler handler;
	
	protected HttpHandlerContext context;
	
	@Before
	public void setUp() {
		handler = new SimpleExceptionHandler();
		context = mock(HttpHandlerContext.class);
		when(context.getResponse()).thenReturn(mock(HttpResponse.class));
		when(context.hasException()).thenReturn(true);
	}
	
	@Test
	public void testHandleHttpRequestForNoException() throws Exception {
		when(context.hasException()).thenReturn(false);
		
		assertFalse(handler.handleHttpRequest(context));
		verify(context.getResponse(), times(0)).setStatus(any(Status.class));
	}
	
	@Test
	public void testHandleHttpRequestForForbiddenException() throws Exception {
		Exception exception = new ForbiddenException();
		when(context.getException()).thenReturn(exception);
		
		assertTrue(handler.handleHttpRequest(context));
		verify(context.getResponse(), times(1)).setStatus(Status.FORBIDDEN);
	}
	
	@Test
	public void testHandleHttpRequestForMethodNotAllowedException() throws Exception {
		Exception exception = new MethodNotAllowedException();
		when(context.getException()).thenReturn(exception);
		
		assertTrue(handler.handleHttpRequest(context));
		verify(context.getResponse(), times(1)).setStatus(Status.METHOD_NOT_ALLOWED);
	}
	
	@Test
	public void testHandleHttpRequestForNotFoundException() throws Exception {
		Exception exception = new NotFoundException();
		when(context.getException()).thenReturn(exception);
		
		assertTrue(handler.handleHttpRequest(context));
		verify(context.getResponse(), times(1)).setStatus(Status.NOT_FOUND);
	}
	
}