package net.canaydogan.umbrella.examples.helloworld;

import net.canaydogan.umbrella.UmbrellaServer;
import net.canaydogan.umbrella.handler.HttpHandler;
import net.canaydogan.umbrella.handler.HttpHandlerContext;

class Main {

	public static void main(String[] args) throws Exception {
		UmbrellaServer server = new UmbrellaServer();
		server.setHttpHandler(new HttpHandler() {			
			@Override
			public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
				System.out.println(context.getRequest().getHeaderCollection().get("Accept"));
				context.getResponse().setContent("Hello world");
				return true;
			}
		});
		
		server.setPort(8080);
		server.run();
	}

}
