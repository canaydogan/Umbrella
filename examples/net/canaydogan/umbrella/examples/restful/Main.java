package net.canaydogan.umbrella.examples.restful;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.UmbrellaServer;
import net.canaydogan.umbrella.handler.RestfulHandler;
import net.canaydogan.umbrella.restful.SimpleResourceStack;
import net.canaydogan.umbrella.router.RouteStack;
import net.canaydogan.umbrella.router.SegmentRoute;
import net.canaydogan.umbrella.router.SimpleRouteStack;

public class Main {

	public static void main(String[] args) throws Exception {
		UmbrellaServer server = new UmbrellaServer();
		
		SimpleResourceStack resourceStack = new SimpleResourceStack();
		resourceStack.addResource("posts", new PostResource());
		resourceStack.addResource("comments", new CommentResource());
		
		RouteStack routeStack = new SimpleRouteStack();		
		routeStack.addRoute("controllerplusid", new SegmentRoute("/:controller/:id"));
		routeStack.addRoute("justcontroller", new SegmentRoute("/:controller"));		
		
		HttpHandler restfulHandler = new RestfulHandler(routeStack, resourceStack);

		server.setHttpHandler(restfulHandler);
		
		server.setPort(8080);
		server.run();
	}

}
