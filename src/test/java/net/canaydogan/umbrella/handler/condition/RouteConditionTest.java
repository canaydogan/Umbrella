package net.canaydogan.umbrella.handler.condition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.canaydogan.umbrella.handler.HttpHandlerContext;
import net.canaydogan.umbrella.router.Route;
import net.canaydogan.umbrella.router.RouteMatch;
import static org.mockito.Mockito.*;

public class RouteConditionTest extends AbstractCondition {

	@Override
	public Condition newValidCondition() {
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(new RouteMatch());
		
		return new RouteCondition(route);
	}
	
	@Override
	public Condition newInvalidCondition() {
		Route route = mock(Route.class);
		when(route.match(null)).thenReturn(null);
		
		return new RouteCondition(route);
	}

	@Override
	public Collection<HttpHandlerContext> getValidCollection() {
		Set<HttpHandlerContext> collection = new HashSet<>();
		collection.add(new HttpHandlerContext(null, null));		
		
		return collection;
	}

	@Override
	public Collection<HttpHandlerContext> getInvalidCollection() {
		Set<HttpHandlerContext> collection = new HashSet<>();
		collection.add(new HttpHandlerContext(null, null));		
		
		return collection;
	}	
		
}