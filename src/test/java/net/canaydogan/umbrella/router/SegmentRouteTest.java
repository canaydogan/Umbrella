package net.canaydogan.umbrella.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.HttpRequest;

import org.junit.Test;

public class SegmentRouteTest extends AbstractRoute {
		
	private Object[][] routes = {
		{
			"/",
			"/",
			new HashMap<String, String>()
		},
		{
			"/:foo",
			"/bar",
			new HashMap<String, String>(){{
				put("foo", "bar");
			}}
		},
		{
			"/:foo/:bar",
			"/segment1/segment2",
			new HashMap<String, String>(){{
				put("foo", "segment1");
				put("bar", "segment2");
			}}
		},
		{
			"/:foo/segment/:bar",
			"/segment1/segment/segment2",
			new HashMap<String, String>(){{
				put("foo", "segment1");
				put("bar", "segment2");
			}}
		},
		{
			"/:foo/segment/:bar.:format",
			"/segment1/segment/segment2.json",
			new HashMap<String, String>(){{
				put("foo", "segment1");
				put("bar", "segment2");
				put("format", "json");
			}}
		},//With Default Values
		{
			"/",
			"/",
			new HashMap<String, String>(){{
				put("foo", "default");
			}},
			new HashMap<String, String>(){{
				put("foo", "default");
			}}
		},
		//Unmatching Routes
		{
			"/",
			"",
			null
		},		
		{
			"/:foo",
			"/",
			null
		},
		{
			"/segment",
			"/",
			null
		},
		{
			"/:foo/segment/:bar.:format",
			"/segment1/segment/segment2",
			null
		},
		//Conditional
		{
			"/:foo/segment[/:bar]",
			"/segment1/segment/custom2",
			new HashMap<String, String>(){{
				put("foo", "segment1");
				put("bar", "custom2");
			}}
		},
		{
			"/:foo/segment[/:bar]",
			"/segment1/segment",
			new HashMap<String, String>(){{
				put("foo", "segment1");
			}}
		},
		{
			"/:foo/segment[/:bar]",
			"/segment1/segment",
			new HashMap<String, String>(){{
				put("foo", "segment1");
				put("bar", "default");
			}},
			new HashMap<String, String>(){{
				put("bar", "default");
			}},
		}
	};
	
	@Test
	public void testMatch() {
		for (Object[] route : routes) {
			Route router;
			if (4 == route.length) {
				router = new SegmentRoute((String) route[0], (Map<String, String>) route[3]);	
			} else {
				router = new SegmentRoute((String) route[0]);
			}
			
			HttpRequest request = buildRequest((String) route[1]);
			RouteMatch match = router.match(request);
			
			if (null == route[2]) {
				assertNull(match);
			} else {
				assertNotNull(match);
				
				Map<String, String> params = (Map<String, String>) route[2];
				for (String name : params.keySet()) {
					assertEquals(params.get(name), match.getParam(name));
				}
			}
		}
	}			
	
}
