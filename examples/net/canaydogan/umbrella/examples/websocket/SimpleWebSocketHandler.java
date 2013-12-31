package net.canaydogan.umbrella.examples.websocket;

import java.util.ArrayList;
import java.util.List;

import net.canaydogan.umbrella.WebSocketHandler;
import net.canaydogan.umbrella.WebSocketHandlerContext;

public class SimpleWebSocketHandler implements WebSocketHandler {

	List<WebSocketHandlerContext> contextList = new ArrayList<>();
	
	@Override
	public void onOpen(WebSocketHandlerContext context) throws Exception {		
		contextList.add(context);
		System.out.println(contextList.size());
		for (WebSocketHandlerContext ctx : contextList) {
			ctx.send("Selam");
		}
		
		
	}

	@Override
	public void onMessage(WebSocketHandlerContext context, String message) throws Exception {
		for (WebSocketHandlerContext ctx : contextList) {
			ctx.send(message);
		}
	}

	@Override
	public void onMessage(WebSocketHandlerContext context, byte[] message)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPing(WebSocketHandlerContext context, byte[] message)
			throws Exception {
		System.out.println("ON PING");
		context.ping(message);
	}

	@Override
	public void onPong(WebSocketHandlerContext context, byte[] message)
			throws Exception {
		System.out.println("ON PONG");
		context.pong(message);
	}

	@Override
	public void onClose(WebSocketHandlerContext context) {
		System.out.println("Close");
		contextList.remove(context);
	}

}
