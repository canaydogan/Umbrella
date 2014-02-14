package net.canaydogan.umbrella.router;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.canaydogan.umbrella.HttpRequest;

abstract class AbstractRoute {

	public AbstractRoute() {
		super();
	}

	public HttpRequest buildRequest(String path) {
		HttpRequest request = mock(HttpRequest.class);
		when(request.getUri()).thenReturn(path);
		when(request.getUriWithoutQuery()).thenReturn(path);
		
		return request;
	}

}