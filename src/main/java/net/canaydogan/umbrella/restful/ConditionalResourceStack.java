package net.canaydogan.umbrella.restful;

import java.util.HashMap;
import java.util.Map;

import net.canaydogan.umbrella.condition.Condition;
import net.canaydogan.umbrella.handler.HttpHandlerContext;
import net.canaydogan.umbrella.restful.exception.InvalidRequestException;
import net.canaydogan.umbrella.restful.exception.ResourceNotFoundException;

class Container {
	
	private Resource resource;
	private Condition condition;
	
	public Container(Resource resource, Condition condition) {
		this.resource = resource;
		this.condition = condition;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public Condition getCondition() {
		return condition;
	}
	
}

public class ConditionalResourceStack implements ResourceStack {
	
	protected String identifierName = "controller";
	
	protected Map<String, Container> stack = new HashMap<>();
	
	public ConditionalResourceStack addResource(String name, Resource resource, Condition condition) {
		stack.put(name, new Container(resource, condition));
		return this;
	}
	
	public ConditionalResourceStack removeResource(String name) {
		stack.remove(name);
		return this;
	}
	
	protected String getIdentifier(HttpHandlerContext context) {		
		return context.getRouteMatch().getParam(getIdentifierName());
	}
	
	protected Resource findResource(HttpHandlerContext context) throws Exception {
		String name = getIdentifier(context);
		
		if (false == stack.containsKey(name)) {
			throw new ResourceNotFoundException();
		}
		
		Container container = stack.get(name);
		
		if (false == container.getCondition().isValid(context)) {
			throw new InvalidRequestException();
		}
		
		return container.getResource();
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

}