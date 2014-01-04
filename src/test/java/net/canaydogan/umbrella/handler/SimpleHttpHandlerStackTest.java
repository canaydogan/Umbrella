package net.canaydogan.umbrella.handler;

import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpHandlerStack;

public class SimpleHttpHandlerStackTest {

	protected HttpHandlerStack stack;
	
	@Before
	public void setUp() {
		stack = new SimpleHttpHandlerStack();
	}
	
	@Test
	public void testHandleHttpRequestWithOneHandler() throws Exception {
		HttpHandler handler = mock(HttpHandler.class);
		when(handler.handleHttpRequest(null)).thenReturn(false);		
		stack.addHttpHandler(handler);
		
		assertFalse(stack.handleHttpRequest(null));
		verify(handler, times(1)).handleHttpRequest(null);
	}
	
	@Test
	public void testHandleHttpRequestWithTwoHandlerForTrue() throws Exception {
		HttpHandler handler1 = mock(HttpHandler.class);
		HttpHandler handler2 = mock(HttpHandler.class);
		when(handler1.handleHttpRequest(null)).thenReturn(true);
		when(handler2.handleHttpRequest(null)).thenReturn(false);
		stack.addHttpHandler(handler1);
		stack.addHttpHandler(handler2);
		
		assertTrue(stack.handleHttpRequest(null));
		verify(handler1, times(1)).handleHttpRequest(null);
		verify(handler2, times(0)).handleHttpRequest(null);
	}
	
	@Test
	public void testHandleHttpRequestForException() throws Exception {
		Exception exception = new Exception();
		HttpHandlerContext context = mock(HttpHandlerContext.class);
		HttpHandler handler = mock(HttpHandler.class);
		when(handler.handleHttpRequest(context)).thenThrow(exception);
		stack.addHttpHandler(handler);
		
		assertFalse(stack.handleHttpRequest(context));
		verify(handler, times(1)).handleHttpRequest(context);
		verify(context, times(1)).setException(exception);
	}
	
}
