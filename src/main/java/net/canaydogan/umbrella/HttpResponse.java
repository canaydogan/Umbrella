package net.canaydogan.umbrella;

public interface HttpResponse {

	public HttpResponse setContent(Object content);
	
	public Object getContent();
	
	public HttpHeaderCollection getHeaderCollection();
	
	public boolean finish();
	
	public HttpCookieCollection getCookieCollection();
	
}
