package net.canaydogan.umbrella.request;

public class HttpRequestWrapper implements HttpRequest {
	
	protected io.netty.handler.codec.http.HttpRequest request;
	
	public HttpRequestWrapper(io.netty.handler.codec.http.HttpRequest request) {
		this.request = request;
	}

	@Override
	public String getUri() {
		return request.getUri();
	}

	@Override
	public Method getMethod() {
		if (null == request.getMethod()) {
			return null;
		}
		String methodAsString = request.getMethod().toString();
		
		for (HttpRequest.Method method : HttpRequest.Method.values()) {
			if (method.toString() == methodAsString) {
				return method;
			}
		}		
		
		return null;
	}

}
