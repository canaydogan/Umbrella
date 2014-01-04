package net.canaydogan.umbrella.handler;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpResponse.Status;
import net.canaydogan.umbrella.handler.exception.ForbiddenException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.handler.exception.NotFoundException;

public class SimpleExceptionHandler implements HttpHandler {

	@Override
	public boolean handleHttpRequest(HttpHandlerContext context)
			throws Exception {
		
		if (context.hasException()) {
			if (context.getException() instanceof ForbiddenException) {
				context.getResponse().setStatus(Status.FORBIDDEN);
				return true;
			} else if (context.getException() instanceof MethodNotAllowedException) {
				context.getResponse().setStatus(Status.METHOD_NOT_ALLOWED);
				return true;
			} else if (context.getException() instanceof NotFoundException) {
				context.getResponse().setStatus(Status.NOT_FOUND);
				return true;
			}
		}
		
		return false;
	}

}
