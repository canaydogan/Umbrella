package net.canaydogan.umbrella;

public interface HttpResponse {

	public enum Status {
		OK(200), CONTINUE(100), NOT_MODIFIED(304), NOT_FOUND(404), 
		FORBIDDEN(403), METHOD_NOT_ALLOWED(405), BAD_REQUEST(400);
		
		private final int code;
		
		Status(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
		
		public static Status valueOf(int code) {
			for (Status status : Status.values()) {
				if (code == status.getCode()) {
					return status;
				}
			}
			
			return null;
		}
	}
	
	public HttpResponse setContent(Object content);
	
	public Object getContent();
	
	public HttpHeaderCollection getHeaderCollection();
	
	public HttpCookieCollection getCookieCollection();
	
	public HttpResponse setStatus(Status status);
	
	public Status getStatus();
	
}
