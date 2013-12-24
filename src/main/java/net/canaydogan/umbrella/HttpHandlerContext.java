package net.canaydogan.umbrella;

import net.canaydogan.umbrella.router.RouteMatch;

public interface HttpHandlerContext {

	public HttpRequest getRequest();

	public HttpResponse getResponse();

	public RouteMatch getRouteMatch();

	public void setRouteMatch(RouteMatch routeMatch);

	public void setException(Exception exception);

	public Exception getException();

	public boolean hasException();
	
}
