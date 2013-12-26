package net.canaydogan.umbrella;

public interface HttpResponse {

	public enum Status {
		OK, CONTINUE, NOT_MODIFIED, NOT_FOUND
	}
	
	public HttpResponse setContent(Object content);
	
	public Object getContent();
	
	public HttpHeaderCollection getHeaderCollection();
	
	public boolean finish();
	
	public HttpCookieCollection getCookieCollection();
	
	public HttpResponse setStatus(Status status);
	
	public Status getStatus();
	
}
