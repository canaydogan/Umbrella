package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class ForbiddenException extends HttpException {

	private static final long serialVersionUID = -4444729792975021253L;
	
	public ForbiddenException() {
		super(Status.FORBIDDEN);
	}

}