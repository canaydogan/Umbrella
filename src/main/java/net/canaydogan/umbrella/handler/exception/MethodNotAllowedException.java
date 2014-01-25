package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class MethodNotAllowedException extends HttpException {
	
	private static final long serialVersionUID = 2928237945066424637L;
	
	public MethodNotAllowedException() {
		super(Status.METHOD_NOT_ALLOWED);
	}

}
