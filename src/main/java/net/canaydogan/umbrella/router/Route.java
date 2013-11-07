package net.canaydogan.umbrella.router;

import net.canaydogan.umbrella.request.HttpRequest;

public interface Route {

	public RouteMatch match(HttpRequest request);
	
}
