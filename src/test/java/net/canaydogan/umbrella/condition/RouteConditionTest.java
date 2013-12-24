package net.canaydogan.umbrella.condition;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.condition.Condition;
import net.canaydogan.umbrella.condition.RouteCondition;
import net.canaydogan.umbrella.router.Route;
import net.canaydogan.umbrella.router.RouteMatch;
import net.canaydogan.umbrella.util.DefaultHttpHandlerContext;

public class RouteConditionTest extends AbstractCondition {

	@Override
	public Map<Condition, HttpHandlerContext> newValidData() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();		
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(new RouteMatch());		
		data.put(new RouteCondition(route), new DefaultHttpHandlerContext(null, null));
		
		return data;
	}

	@Override
	public Map<Condition, HttpHandlerContext> newInvalidData() {
		Map<Condition, HttpHandlerContext> data = new HashMap<>();		
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(null);
		
		data.put(new RouteCondition(route), new DefaultHttpHandlerContext(null, null));
		
		return data;
	}

}