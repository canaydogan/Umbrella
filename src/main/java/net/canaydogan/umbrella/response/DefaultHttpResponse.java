package net.canaydogan.umbrella.response;

import net.canaydogan.umbrella.HttpResponse;


public class DefaultHttpResponse implements HttpResponse {

	protected Object content;
	
	@Override
	public HttpResponse setContent(Object content) {
		this.content = content;
		return this;
	}

	@Override
	public Object getContent() {
		return content;
	}

}
