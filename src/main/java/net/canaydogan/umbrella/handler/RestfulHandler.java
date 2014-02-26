package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.HttpResponse.Status;
import net.canaydogan.umbrella.handler.exception.ConfigurationException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.restful.Resource;

public class RestfulHandler implements HttpHandler {

	protected String identifierName = "id";
	
	protected Resource resource;
	
	public RestfulHandler(Resource resource) {
		setResource(resource);
	}
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
		if (context.hasException()) {
			return false;
		}
		
		HttpRequest request = context.getRequest();
		HttpResponse response = context.getResponse();
		
		if (null == context.getRequest().getRouteMatch()) {
			throw new ConfigurationException();
		}
		
		if (null == context.getRequest().getRouteMatch()) {
			return false;
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
				response.setStatus(Status.CREATED);
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
		
		return false;
	}
	
	public String getIdentifierName() {
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}
	
	protected String getIdentifier(HttpHandlerContext context) {
		return context.getRequest().getRouteMatch().get(getIdentifierName());
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}