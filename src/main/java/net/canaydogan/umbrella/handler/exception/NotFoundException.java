package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class NotFoundException extends HttpException {

	private static final long serialVersionUID = -1418546200508771968L;
	
	public NotFoundException() {
		super(Status.NOT_FOUND);
	}

}
