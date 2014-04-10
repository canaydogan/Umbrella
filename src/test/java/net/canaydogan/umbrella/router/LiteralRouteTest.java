package net.canaydogan.umbrella.router;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.canaydogan.umbrella.HttpRequest;

import org.junit.Test;

public class LiteralRouteTest extends AbstractRoute {

	protected Object[][] routes = {
			{
				"/url",
				"/url",
				true
			},
			//Unmatched routes
			{
				"/url",
				"/notmatchedvalues",
				false
			},
	};
	
	@Test
	public void testMatch() {
		for (Object[] route : routes) {
			Route router = new LiteralRoute((String) route[0]);
			HttpRequest request = buildRequest((String) route[1]);
			
			if (true == (boolean)route[2]) {
				assertNotNull(router.match(request));
			} else {
				assertNull(router.match(request));
			}			
		}
	}
	
	@Test
	public void testMatchesWithMultipleRoutes() {
		String[] routes = {"route1", "route2"};
		
		Route router = new LiteralRoute(routes);
		
		for (String route : routes) {
			HttpRequest request = buildRequest(route);
			assertNotNull(router.match(request));
		}
		
	}
	
	
}
