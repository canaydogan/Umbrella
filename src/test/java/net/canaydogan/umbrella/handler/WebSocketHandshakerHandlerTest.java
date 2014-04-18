package net.canaydogan.umbrella.handler;

import org.junit.Before;
import org.junit.Test;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.WebSocketHandler;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class WebSocketHandshakerHandlerTest {

	protected HttpHandler handler;
	
	protected WebSocketHandler webSocketHandler;
	
	protected HttpHandlerContext context; 
	
	@Before
	public void setUp() {
		webSocketHandler = mock(WebSocketHandler.class);
		handler = new WebSocketHandshakerHandler(webSocketHandler);
		context = mock(HttpHandlerContext.class);
		when(context.getResponse()).thenReturn(mock(HttpResponse.class));
	}
	
	@Test
	public void testHandleHttpRequest() throws Exception {
		assertTrue(handler.handleHttpRequest(context));
		verify(context.getResponse(), times(1)).setContent(webSocketHandler);
	}
		
}