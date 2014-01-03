package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.handler.exception.ConfigurationException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.restful.Resource;
import net.canaydogan.umbrella.router.Route;

public class RestfulHandler implements HttpHandler {

	protected String identifierName = "id";
	
	protected Resource resource;
	
	protected Route route;
	
	public RestfulHandler(Route route, Resource resource) {
		setRoute(route);
		setResource(resource);
	}
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
		HttpRequest request = context.getRequest();
		HttpResponse response = context.getResponse();
		Route route = getRoute();
		
		if (null == route) {
			throw new ConfigurationException();
		}
		
		context.getRequest().setRouteMatch(route.match(request));
		
		if (null == context.getRequest().getRouteMatch()) {
			return true;
		}

		switch (request.getMethod()) {
			case GET:
				if (null != getIdentifier(context)) {
					response.setContent(resource.get(context));
				} else {
					response.setContent(resource.getList(context));	
				}				
				break;					
			case POST:
				response.setContent(resource.create(context));
				break;
			case PUT:
				response.setContent(resource.update(context));
				break;
			case DELETE:
				response.setContent(resource.delete(context));
				break;
			default:
				throw new MethodNotAllowedException();
		}
		
		return true;
	}
	
	public String getIdentifierName() {
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}
	
	protected String getIdentifier(HttpHandlerContext context) {
		return context.getRequest().getRouteMatch().getParam(getIdentifierName());
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}	

}