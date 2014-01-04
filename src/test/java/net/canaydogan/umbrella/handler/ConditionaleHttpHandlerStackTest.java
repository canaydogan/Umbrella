package net.canaydogan.umbrella.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.condition.Condition;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

import org.junit.Before;
import org.junit.Test;

public class ConditionaleHttpHandlerStackTest {

	private ConditionalHttpHandlerStack stack;
	
	@Before
	public void setUp() {
		stack = new ConditionalHttpHandlerStack();
	}
	
	protected Condition newCondition(HttpHandlerContext context, boolean status) {
		Condition condition = mock(Condition.class);
		when(condition.isValid(context)).thenReturn(status);
		
		return condition;
	}
	
	@Test
	public void testAddAndRemoteHttpHandler() throws Exception {
		Map<HttpHandler, Condition> data = (Map<HttpHandler, Condition>) stack.getClass().getDeclaredField("stack").get(stack);		
		
		stack.addHttpHandler(null);		
		assertEquals(1, data.size());			
		stack.removeHttpHandler(null);
		assertEquals(0, data.size());	
		
		stack.addHttpHandler(null, null);		
		assertEquals(1, data.size());			
		stack.removeHttpHandler(null);
		assertEquals(0, data.size());		
	}	
	
	@Test
	public void testHandlerHttpRequestWithOneValidHandler() throws Exception {
		//HttpContext
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);
		
		//Handler
		HttpHandler handler = mock(HttpHandler.class);
					
		stack.addHttpHandler(handler, newCondition(context, true));
		
		stack.handleHttpRequest(context);		
		verify(handler, times(1)).handleHttpRequest(context);
	}
	
	@Test
	public void testHandlerHttpRequestWithInvalidHandler() throws Exception {
		//HttpContext
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);
		
		//Handler
		HttpHandler handler = mock(HttpHandler.class);
		
		stack.addHttpHandler(handler, newCondition(context, false));
		
		stack.handleHttpRequest(context);		
		verify(handler, times(0)).handleHttpRequest(context);
	}
	
	@Test
	public void testHandlerHttpRequestForNonBlock() throws Exception {
		//HttpContext
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);
		
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
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);
		
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
	
	@Test
	public void testHandleHttpRequestForException() throws Exception {
		//HttpContext
		HttpHandlerContext context = new DefaultHttpHandlerContext(null, null);		
		
		//Exception
		Exception exception = new Exception();
		//Handlers
		HttpHandler handler = mock(HttpHandler.class);
		when(handler.handleHttpRequest(context)).thenThrow(exception);
							
		stack.addHttpHandler(handler, newCondition(context, true));
		
		stack.handleHttpRequest(context);		
		assertSame(exception, context.getException());
	}
	
}