package net.canaydogan.umbrella.restful;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.restful.exception.ResourceNotFoundException;

public class SimpleResourceStack implements ResourceStack {
	
	protected String identifierName = "controller";
	
	protected Map<String, Resource> stack = new HashMap<>();
	
	protected Resource findResource(HttpHandlerContext context) throws Exception {
		String name = getIdentifier(context);
		
		if (false == stack.containsKey(name)) {
			throw new ResourceNotFoundException();
		}
		
		return stack.get(name);
	}
	
	protected String getIdentifier(HttpHandlerContext context) {		
		return context.getRouteMatch().getParam(getIdentifierName());
	}

	@Override
	public Object get(HttpHandlerContext context) throws Exception {
		return findResource(context).get(context);
	}

	@Override
	public Object getList(HttpHandlerContext context) throws Exception {
		return findResource(context).getList(context);
	}

	@Override
	public Object create(HttpHandlerContext context) throws Exception {
		return findResource(context).create(context);
	}

	@Override
	public Object update(HttpHandlerContext context) throws Exception {
		return findResource(context).update(context);
	}

	@Override
	public Object delete(HttpHandlerContext context) throws Exception {
		return findResource(context).delete(context);
	}

	public String getIdentifierName() {
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	public SimpleResourceStack addResource(String name, Resource resource) {
		stack.put(name, resource);
		return this;
	}
	
	public SimpleResourceStack removeResource(String name) {
		stack.remove(name);
		return this;
	}
	
}
