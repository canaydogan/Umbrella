package net.canaydogan.umbrella;

public interface WebSocketHandler {
	
	public void onOpen(WebSocketHandlerContext context) throws Exception;
	
	public void onMessage(WebSocketHandlerContext context, String message) throws Exception;
	
	public void onMessage(WebSocketHandlerContext context, byte[] message) throws Exception;
	
	public void onPing(WebSocketHandlerContext context, byte[] message) throws Exception;	
	
	public void onPong(WebSocketHandlerContext context, byte[] message) throws Exception;
	
	public void onClose(WebSocketHandlerContext context);
	
}