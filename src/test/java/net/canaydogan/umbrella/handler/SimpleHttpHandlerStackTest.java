package net.canaydogan.umbrella.handler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import net.canaydogan.umbrella.handler.condition.Condition;

import org.junit.Before;
import org.junit.Test;

public class SimpleHttpHandlerStackTest {

	private HttpHandlerStack stack;
	
	@Before
	public void setUp() {
		stack = new SimpleHttpHandlerStack();
	}
	
	protected Condition newCondition(HttpHandlerContext context, boolean status) {
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(status);
		
		return condition;
	}
	
	@Test
	public void testAddAndRemoteHttpHandler() throws Exception {
		Map<HttpHandler, Condition> data = (Map<HttpHandler, Condition>) stack.getClass().getDeclaredField("stack").get(stack);		
		
		stack.addHttpHandler(null, null);		
		assertEquals(1, data.size());	
		
		stack.removeHttpHandler(null);
		assertEquals(0, data.size());		
	}	
	
	@Test
	public void testHandlerHttpRequestWithOneValidHandler() throws Exception {
		//HttpContext
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		
		//Handler
		HttpHandler handler = mock(HttpHandler.class);
					
		stack.addHttpHandler(handler, newCondition(context, true));
		
		stack.handleHttpRequest(context);		
		verify(handler, times(1)).handleHttpRequest(context);
	}
	
	@Test
	public void testHandlerHttpRequestWithInvalidHandler() throws Exception {
		//HttpContext
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		
		//Handler
		HttpHandler handler = mock(HttpHandler.class);
		
		stack.addHttpHandler(handler, newCondition(context, false));
		
		stack.handleHttpRequest(context);		
		verify(handler, times(0)).handleHttpRequest(context);
	}
	
	@Test
	public void testHandlerHttpRequestForNonBlock() throws Exception {
		//HttpContext
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		
		//Condition
		Condition condition = newCondition(context, true);
		
		//Handlers
		HttpHandler handler1 = mock(HttpHandler.class);
		when(handler1.handleHttpRequest(context)).thenReturn(false);
		
		HttpHandler handler2 = mock(HttpHandler.class);
					
		stack.addHttpHandler(handler1, condition);
		stack.addHttpHandler(handler2, condition);
		
		stack.handleHttpRequest(context);		
		verify(handler1, times(1)).handleHttpRequest(context);
		verify(handler2, times(1)).handleHttpRequest(context);
	}
	
	@Test
	public void testHandlerHttpRequestForBlocking() throws Exception {
		//HttpContext
		HttpHandlerContext context = new HttpHandlerContext(null, null);
		
		//Condition
		Condition condition = newCondition(context, true);
		
		//Handlers
		HttpHandler handler1 = mock(HttpHandler.class);
		when(handler1.handleHttpRequest(context)).thenReturn(true);
		
		HttpHandler handler2 = mock(HttpHandler.class);
					
		stack.addHttpHandler(handler1, condition);
		stack.addHttpHandler(handler2, condition);
		
		stack.handleHttpRequest(context);		
		verify(handler1, times(1)).handleHttpRequest(context);
		verify(handler2, times(0)).handleHttpRequest(context);
	}	
	
}