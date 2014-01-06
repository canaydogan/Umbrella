package net.canaydogan.umbrella.router;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.canaydogan.umbrella.HttpRequest;

import org.junit.Test;

public class RegexRouteTest extends AbstractRoute {

	protected Object[][] routes = {
			{
				"(.*)",
				"",
				true
			},
			{
				"(.*)",
				"/",
				true
			},
			{
				"(.*)/folder/(.*)",
				"/folder/19",
				true
			},
			//Unmatched routes
			{
				"(.*)/n/(.*)",
				"/folder/19",
				false
			},
	};
	
	@Test
	public void testMatch() {
		for (Object[] route : routes) {
			Route router = new RegexRoute((String) route[0]);
			HttpRequest request = buildRequest((String) route[1]);
			
			if (true == (boolean)route[2]) {
				assertNotNull(router.match(request));
			} else {
				assertNull(router.match(request));
			}			
		}
	}
	
	
}
