package net.canaydogan.umbrella.condition;

import net.canaydogan.umbrella.handler.HttpHandlerContext;
import net.canaydogan.umbrella.router.Route;

public class RouteCondition implements Condition {

	protected Route route;
	
	public RouteCondition(Route route) {
		this.route = route;
	}
	
	@Override
	public boolean isValid(HttpHandlerContext context) {
		if (null != route.match(context.getRequest())) {
			return true;
		}
		
		return false;		
	}

}
