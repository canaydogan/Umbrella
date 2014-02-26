package net.canaydogan.umbrella.examples.helloworld;

import io.netty.util.ResourceLeakDetector;
import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.UmbrellaServer;

class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("HelloWorld Example");
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.SIMPLE);
		UmbrellaServer server = new UmbrellaServer();
		server.setHttpHandler(new HttpHandler() {			
			@Override
			public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
				context.getResponse().setContent("Hello world.");
				return false;
			}
		});
		
		server.setPort(8080);
		server.run();
	}

}
