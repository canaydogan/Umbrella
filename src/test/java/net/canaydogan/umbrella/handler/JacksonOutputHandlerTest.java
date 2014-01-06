package net.canaydogan.umbrella.handler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpResponse;

import org.junit.Before;
import org.junit.Test;

public class JacksonOutputHandlerTest {

	protected HttpHandler handler;
	
	protected HttpHandlerContext context;
	
	@Before
	public void setUp() {
		handler = new JacksonOutputHandler();
		context = mock(HttpHandlerContext.class);
		when(context.getResponse()).thenReturn(mock(HttpResponse.class));
	}
	
	@Test
	public void testHandleHttpRequestWithMapContent() throws Exception {
		Map<String, String> content = new HashMap<>();
		content.put("key1", "value1");
		content.put("key2", "value2");
		
		when(context.getResponse().getContent()).thenReturn(content);		
		assertFalse(handler.handleHttpRequest(context));
		
		verify(context.getResponse(), times(1)).setContent("{\"key2\":\"value2\",\"key1\":\"value1\"}");
	}
	
}
