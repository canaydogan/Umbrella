package net.canaydogan.umbrella;

public interface HttpHandlerContext {

	public HttpRequest getRequest();

	public HttpResponse getResponse();

	public void setException(Exception exception);

	public Exception getException();

	public boolean hasException();
	
	public boolean release();
	
}
