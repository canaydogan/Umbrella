package net.canaydogan.umbrella.examples.websocket;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.UmbrellaServer;
import net.canaydogan.umbrella.condition.RouteCondition;
import net.canaydogan.umbrella.handler.ConditionalHttpHandlerStack;
import net.canaydogan.umbrella.handler.StaticFileHandler;
import net.canaydogan.umbrella.handler.WebSocketHandshakerHandler;
import net.canaydogan.umbrella.router.SegmentRoute;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		String directory = System.getProperty("user.dir") + "/src/test/resources";		
		UmbrellaServer server = new UmbrellaServer();		
		
		HttpHandler handshaker = new WebSocketHandshakerHandler(new SimpleWebSocketHandler());
		HttpHandler staticFileHandler = new StaticFileHandler(directory, "/index.html", true);
		ConditionalHttpHandlerStack handlerStack = new ConditionalHttpHandlerStack();
		handlerStack.addHttpHandler(handshaker, new RouteCondition(new SegmentRoute("/socket")));
		handlerStack.addHttpHandler(staticFileHandler, new RouteCondition(new SegmentRoute("/websocket/index.html")));		
		
		server.setHttpHandler(handlerStack);
		server.setPort(8080);
		server.run();
	}
	
}
