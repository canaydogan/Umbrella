package net.canaydogan.umbrella.router;

import net.canaydogan.umbrella.HttpRequest;

public class LiteralRoute implements Route {

	protected final String[] routes;
	
	public LiteralRoute(String... routes) {
		this.routes = routes;
	}
	
	@Override
	public RouteMatch match(HttpRequest request) {
		for (String route : routes) {
			if (route.equals(request.getUriWithoutQuery())) {
				return new RouteMatch();
			}
		}
		
		return null;
	}
	
}
