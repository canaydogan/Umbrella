package net.canaydogan.umbrella.handler;

import org.codehaus.jackson.map.ObjectMapper;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;

public class JacksonOutputHandler implements HttpHandler {

	protected ObjectMapper objectMapper;
	
	public JacksonOutputHandler() {
		this(new ObjectMapper());
	}
	
	public JacksonOutputHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context)
			throws Exception {
		
		if (null != context.getResponse().getContent()) {
			context.getResponse().setContent(
				objectMapper.writeValueAsString(context.getResponse().getContent())
			);	
		}
		
		return false;
	}

}
