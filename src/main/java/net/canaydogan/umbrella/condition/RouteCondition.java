package net.canaydogan.umbrella.condition;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.router.Route;
import net.canaydogan.umbrella.router.RouteMatch;

public class RouteCondition implements Condition {

	protected Route route;
	
	public RouteCondition(Route route) {
		this.route = route;
	}
	
	@Override
	public boolean isValid(HttpHandlerContext context) {
		RouteMatch routeMatch;
		
		if (null != (routeMatch = route.match(context.getRequest()))) {
			context.getRequest().setRouteMatch(routeMatch);			
			return true;
		}
		
		return false;		
	}

}
