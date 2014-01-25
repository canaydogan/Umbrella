package net.canaydogan.umbrella.handler.exception;

import net.canaydogan.umbrella.HttpResponse.Status;

public class HttpException extends Exception {

	private static final long serialVersionUID = 2337352316528970780L;
	
	protected final Status httpStatus;
	
	public HttpException(Status httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Status getHttpStatus() {
		return httpStatus;
	}

}
