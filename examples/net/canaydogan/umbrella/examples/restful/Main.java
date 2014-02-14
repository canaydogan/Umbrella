package net.canaydogan.umbrella.examples.restful;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.UmbrellaServer;
import net.canaydogan.umbrella.condition.RouteCondition;
import net.canaydogan.umbrella.handler.ConditionalHttpHandlerStack;
import net.canaydogan.umbrella.handler.RestfulHandler;
import net.canaydogan.umbrella.restful.SimpleResourceStack;
import net.canaydogan.umbrella.router.SegmentRoute;

public class Main {

	public static void main(String[] args) throws Exception {
		UmbrellaServer server = new UmbrellaServer();

		SimpleResourceStack resourceStack = new SimpleResourceStack();
		resourceStack.addResource("posts", new PostResource());
		resourceStack.addResource("comments", new CommentResource());

		HttpHandler restfulHandler = new RestfulHandler(resourceStack);
		ConditionalHttpHandlerStack handlerStack = new ConditionalHttpHandlerStack();
		handlerStack.addHttpHandler(restfulHandler, new RouteCondition(
				new SegmentRoute("/:resource[/:id]")));

		server.setHttpHandler(handlerStack);

		server.setPort(8080);
		server.run();
	}

}
