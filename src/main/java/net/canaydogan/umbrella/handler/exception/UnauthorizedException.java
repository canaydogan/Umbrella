package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class UnauthorizedException extends HttpException {
	
	private static final long serialVersionUID = -8245277678791711538L;

	public UnauthorizedException() {
		super(Status.UNAUTHORIZED);
	}

}
