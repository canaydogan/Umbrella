package net.canaydogan.umbrella.restful;

import net.canaydogan.umbrella.handler.HttpHandlerContext;

public interface Resource {

	public Object get(HttpHandlerContext context) throws Exception;
	
	public Object getList(HttpHandlerContext context) throws Exception;
	
	public Object create(HttpHandlerContext context) throws Exception;
	
	public Object update(HttpHandlerContext context) throws Exception;
	
	public Object delete(HttpHandlerContext context) throws Exception;	
	
}