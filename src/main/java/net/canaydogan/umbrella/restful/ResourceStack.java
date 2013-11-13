package net.canaydogan.umbrella.restful;

public interface ResourceStack extends Resource {

	public ResourceStack addResource(String name, Resource resource);
	
	public ResourceStack removeResource(String name);
	
}