package net.canaydogan.umbrella.router;

public interface RouteStack extends Route {

	public RouteStack addRoute(String name, Route route);
	
	public RouteStack removeRoute(String name);
	
}
