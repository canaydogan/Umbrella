package net.canaydogan.umbrella.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.canaydogan.umbrella.HttpHeaderCollection;

public class SimpleHttpHeaderCollection implements HttpHeaderCollection {
	
	protected Map<String, List<String>> map = new HashMap<>();

	@Override
	public HttpHeaderCollection set(String name, String value) {
		List<String> list = new ArrayList<>();
		list.add(value);
		map.put(name, list);
		return this;
	}

	@Override
	public String get(String name) {
		if (contains(name)) {
			return map.get(name).get(0);
		}
		
		return null;
	}

	@Override
	public List<String> getAll(String name) {
		if (contains(name)) {
			return map.get(name);
		}
		
		return new ArrayList<String>();
	}

	@Override
	public HttpHeaderCollection add(String name, String value) {
		if (contains(name)) {
			map.get(name).add(value);
		} else {
			set(name, value);
		}
		return this;
	}

	@Override
	public boolean contains(String name) {
		return map.containsKey(name);
	}

	@Override
	public Set<String> nameSet() {
		return map.keySet();
	}

}