package net.canaydogan.umbrella.examples.helloworld;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.UmbrellaServer;

class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("HelloWorld Example");
		UmbrellaServer server = new UmbrellaServer();
		server.setHttpHandler(new HttpHandler() {			
			@Override
			public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
				context.getResponse().setContent("Hello world.");
				return true;
			}
		});
		
		server.setPort(8080);
		server.run();
	}

}
