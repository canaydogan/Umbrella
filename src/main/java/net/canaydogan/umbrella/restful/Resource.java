package net.canaydogan.umbrella.restful;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

public interface Resource {

	public Object get(HttpHandlerContext context);
	
	public Object getList(HttpHandlerContext context);
	
	public Object create(HttpHandlerContext context);
	
	public Object update(HttpHandlerContext context);
	
	public Object delete(HttpHandlerContext context);	
	
}