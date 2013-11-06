package net.canaydogan.umbrella.router;

import net.canaydogan.umbrella.request.HttpRequest;

public interface Router {

	public RouteMatch match(HttpRequest request);
	
}
