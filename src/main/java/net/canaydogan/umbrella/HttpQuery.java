package net.canaydogan.umbrella;

import java.util.List;

public interface HttpQuery {

	public boolean hasParam(String name);
	
	public String getParam(String name);
	
	public List<String> getParamList(String name);
	
}
