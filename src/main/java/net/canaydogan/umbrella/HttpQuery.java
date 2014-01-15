package net.canaydogan.umbrella;

import java.util.List;

public interface HttpQuery {

	public boolean contains(String name);
	
	public String get(String name);
	
	public List<String> getAll(String name);
	
}
