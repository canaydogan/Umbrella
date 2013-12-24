package net.canaydogan.umbrella.examples.helloworld;

import java.net.HttpCookie;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.UmbrellaServer;
import net.canaydogan.umbrella.handler.HttpHandler;

class Main {

	public static void main(String[] args) throws Exception {
		UmbrellaServer server = new UmbrellaServer();
		server.setHttpHandler(new HttpHandler() {			
			@Override
			public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
				HttpCookie counter = context.getRequest().getCookieCollection().get("counter");
				if (null != counter) {
					Integer value = (Integer.parseInt(counter.getValue()) + 1);
					counter.setValue(value.toString());
				} else {
					counter = new HttpCookie("counter", "1");
				}
				System.out.println(context.getRequest().getHeaderCollection().get("Accept"));				
				context.getResponse().getHeaderCollection().set("HeaderName", "Value");
				context.getResponse().getCookieCollection().add(counter);
				context.getResponse().setContent("Hello world. Counter: " + counter.getValue());
				return true;
			}
		});
		
		server.setPort(8080);
		server.run();
	}

}
