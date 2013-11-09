package net.canaydogan.umbrella.router;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.request.HttpRequest;

import org.junit.Test;

public class SegmentRouteTest {
	
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
	};
	
	public HttpRequest buildRequest(String path) {
		HttpRequest request = mock(HttpRequest.class);		
		when(request.getUri()).thenReturn("http://example.com" + path);
		
		return request;
	}
	
	
	@Test
	public void testMatch() {
		for (Object[] route : routes) {
			Route router = new SegmentRoute((String) route[0]);
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
