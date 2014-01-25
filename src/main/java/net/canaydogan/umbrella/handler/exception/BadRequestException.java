package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class BadRequestException extends HttpException {

	private static final long serialVersionUID = -2691499661209870773L;
	
	public BadRequestException() {
		super(Status.BAD_REQUEST);
	}

}
