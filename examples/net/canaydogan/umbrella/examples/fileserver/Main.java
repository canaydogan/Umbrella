package net.canaydogan.umbrella.examples.fileserver;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.UmbrellaServer;
import net.canaydogan.umbrella.handler.StaticFileHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		String directory = System.getProperty("user.dir") + "/src/test/resources/content";		
		UmbrellaServer server = new UmbrellaServer();
		HttpHandler staticFileHandler = new StaticFileHandler(directory, "/index.html", false);
				
		server.setHttpHandler(staticFileHandler);
		server.setPort(8080);
		server.run();
	}
	
}
