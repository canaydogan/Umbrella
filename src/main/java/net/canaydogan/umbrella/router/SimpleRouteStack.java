package net.canaydogan.umbrella.router;

import java.util.LinkedHashMap;
import java.util.Map;

import net.canaydogan.umbrella.HttpRequest;

public class SimpleRouteStack implements RouteStack {

	protected Map<String, Route> routeMap = new LinkedHashMap<>(); 
			
	@Override
	public RouteMatch match(HttpRequest request) {
		RouteMatch result = null;
		
		for (Route route : routeMap.values()) {
			if (null != (result = route.match(request))) {
				return result;
			}
		}
		
		return result;
	}

	@Override
	public RouteStack addRoute(String name, Route route) {
		routeMap.put(name, route);		
		return this;
	}

	@Override
	public RouteStack removeRoute(String name) {
		routeMap.remove(name);
		return this;
	}

}
