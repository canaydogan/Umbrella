package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class InternalServerErrorException extends HttpException {

	private static final long serialVersionUID = 1934332199073302736L;
	
	public InternalServerErrorException() {
		super(Status.INTERNAL_SERVER_ERROR);
	}	

}
