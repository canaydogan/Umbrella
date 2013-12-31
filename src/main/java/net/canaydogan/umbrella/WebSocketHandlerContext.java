package net.canaydogan.umbrella;

public interface WebSocketHandlerContext {
	
	public HttpRequest getRequest();
	
	public WebSocketHandlerContext send(String message);
	
	public WebSocketHandlerContext send(byte[] message);
	
	public WebSocketHandlerContext ping(byte[] message);
	
	public WebSocketHandlerContext pong(byte[] message);
	
	public WebSocketHandlerContext close();
	
}
