package net.canaydogan.umbrella.restful;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import net.canaydogan.umbrella.HttpHandlerContext;

abstract public class AbstractResourceStack {
	
	public Resource newResource(HttpHandlerContext context, String output) throws Exception {
		Resource resource = mock(Resource.class);
		when(resource.get(context)).thenReturn("get " + output);
		when(resource.getList(context)).thenReturn("get list " + output);
		when(resource.create(context)).thenReturn("create " + output);
		when(resource.update(context)).thenReturn("update " + output);
		when(resource.delete(context)).thenReturn("delete " + output);
		
		return resource;
	}
	
}