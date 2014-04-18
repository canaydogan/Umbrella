package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.WebSocketHandler;

public class WebSocketHandshakerHandler implements HttpHandler {

	protected WebSocketHandler handler;
	
	public WebSocketHandshakerHandler(WebSocketHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context)
			throws Exception {		
		if (context.hasException()) {
			return false;
		}
		
		context.getResponse().setContent(handler);
		
		return true;
	}

}
