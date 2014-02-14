package net.canaydogan.umbrella.condition;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.router.Route;
import net.canaydogan.umbrella.router.RouteMatch;

public class RouteConditionTest extends AbstractCondition {

	@Override
	public Map<Condition, HttpHandlerContext> createDataForSuccess() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();
		HttpRequest request = mock(HttpRequest.class);		
		Route route = mock(Route.class);
		when(route.match(request)).thenReturn(new RouteMatch());
		
		HttpHandlerContext context = mock(HttpHandlerContext.class);
		when(context.getRequest()).thenReturn(request);
		data.put(new RouteCondition(route), context);
		
		return data;
	}

	@Override
	public Map<Condition, HttpHandlerContext> createDataForUnsuccess() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();		
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(null);
		
		HttpHandlerContext context = mock(HttpHandlerContext.class);
		when(context.getRequest()).thenReturn(null);
		data.put(new RouteCondition(route), context);
		
		return data;
	}

}