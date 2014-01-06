package net.canaydogan.umbrella.router;

import java.util.HashMap;
import java.util.Map;

public class RouteMatch {

	public Map<String, String> params = new HashMap<>();
	
	public RouteMatch() {}
	
	public RouteMatch(Map<String, String> params) {
		setParams(params);
	}
	
	public RouteMatch setParam(String name, String value) {
		params.put(name, value);
		return this;		
	}

	public String getParam(String name) {
		return params.get(name);
	}

	public RouteMatch setParams(Map<String, String> params) {
		for (String name : params.keySet()) {
			setParam(name, params.get(name));
		}
		
		return this;
	}

}