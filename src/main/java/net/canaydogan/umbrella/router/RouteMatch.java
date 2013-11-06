package net.canaydogan.umbrella.router;

import java.util.HashMap;
import java.util.Map;

public class RouteMatch {

	public Map<String, String> params = new HashMap<>();
	
	public RouteMatch setParam(String name, String value) {
		params.put(name, value);
		return this;		
	}

	public String getParam(String name) {
		return params.get(name);
	}

}
