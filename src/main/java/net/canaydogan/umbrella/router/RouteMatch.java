package net.canaydogan.umbrella.router;

import java.util.HashMap;
import java.util.Map;

public class RouteMatch {

	public Map<String, String> params = new HashMap<>();
	
	public RouteMatch() {}
	
	public RouteMatch(Map<String, String> params) {
		setAll(params);
	}
	
	public RouteMatch set(String name, String value) {
		params.put(name, value);
		return this;		
	}

	public String get(String name) {
		return params.get(name);
	}

	public RouteMatch setAll(Map<String, String> params) {
		for (String name : params.keySet()) {
			set(name, params.get(name));
		}
		
		return this;
	}

}