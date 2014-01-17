package net.canaydogan.umbrella.handler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.stream.ChunkedFile;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpHeaderCollection;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.HttpResponse.Status;
import net.canaydogan.umbrella.handler.exception.ForbiddenException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.handler.exception.NotFoundException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StaticFileHandlerTest {

	protected StaticFileHandler handler;
	protected HttpHandlerContext context;
	protected String directory;
	
	@Before
	public void setUp() {
		directory = System.getProperty("user.dir") + "/src/test/resources"; 
		handler = new StaticFileHandler(directory);
		context = mock(HttpHandlerContext.class);
		when(context.getRequest()).thenReturn(mock(HttpRequest.class));
		when(context.getResponse()).thenReturn(mock(HttpResponse.class));
		when(context.getRequest().getMethod()).thenReturn(HttpRequest.Method.GET);
		when(context.getResponse().getHeaderCollection()).thenReturn(mock(HttpHeaderCollection.class));
		when(context.getRequest().getHeaderCollection()).thenReturn(mock(HttpHeaderCollection.class));
	}
	
	@Test(expected = MethodNotAllowedException.class)
	public void testHttpHandlerWithPostMethod() throws Exception {
		when(context.getRequest().getMethod()).thenReturn(HttpRequest.Method.POST);
		
		handler.handleHttpRequest(context);
	}
	
	@Test(expected = ForbiddenException.class)
	public void testHttpHandlerMethodWithInvalidUri() throws Exception {
		when(context.getRequest().getUri()).thenReturn("/../test.txt");
		
		handler.handleHttpRequest(context);
	}
	
	@Test(expected = NotFoundException.class)
	public void testHttpHandlerWithNonExistFile() throws Exception {
		when(context.getRequest().getUri()).thenReturn("/content/nonexist.txt");
		
		handler.handleHttpRequest(context);
	}
	
	@Test(expected = ForbiddenException.class)
	public void testHttpHandlerWithDirectory() throws Exception {
		when(context.getRequest().getUri()).thenReturn("/content");
		
		handler.handleHttpRequest(context);
	}
	
	@Test(expected = ForbiddenException.class)
	public void testHttpHandlerWithDirectory2() throws Exception {
		when(context.getRequest().getUri()).thenReturn("/content/");
		
		handler.handleHttpRequest(context);
	}
	
	@Test
	public void testHttpHandlerForNotModifiedWithValidDate() throws Exception {
		File file = new File(directory + "/content/test.txt");
		Date date = new Date(file.lastModified());
		SimpleDateFormat dateFormat = new SimpleDateFormat(StaticFileHandler.HTTP_DATE_FORMAT, Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone(StaticFileHandler.HTTP_DATE_GMT_TIMEZONE));

		when(context.getRequest().getHeaderCollection()
				.get(HttpHeaders.Names.IF_MODIFIED_SINCE))
				.thenReturn(dateFormat.format(date));
		
		when(context.getRequest().getUri()).thenReturn("/content/test.txt");
		
		assertFalse(handler.handleHttpRequest(context));
		verify(context.getResponse(), times(1)).setStatus(Status.NOT_MODIFIED);
	}
	
	@Test
	public void testHttpHandlerWithValidFileName() throws Exception {
		when(context.getRequest().getUri()).thenReturn("/content/test.txt");
		
		handler.handleHttpRequest(context);
		verify(context.getResponse().getHeaderCollection(), times(1)).set(HttpHeaders.Names.CONTENT_LENGTH, "13");
		verify(context.getResponse().getHeaderCollection(), times(1)).set(eq(HttpHeaders.Names.CONTENT_TYPE), anyString());
		verify(context.getResponse(), times(1)).setStatus(Status.OK);
		
		//Verify cache values
		verify(context.getResponse().getHeaderCollection(), times(1)).set(eq(HttpHeaders.Names.DATE), anyString());
		verify(context.getResponse().getHeaderCollection(), times(1)).set(eq(HttpHeaders.Names.EXPIRES), anyString());
		verify(context.getResponse().getHeaderCollection(), times(1)).set(eq(HttpHeaders.Names.CACHE_CONTROL), anyString());
		verify(context.getResponse().getHeaderCollection(), times(1)).set(eq(HttpHeaders.Names.LAST_MODIFIED), anyString());
		
		verify(context.getResponse()).setContent(any(ChunkedFile.class));
	}
	
	@Test
	public void testHttpHandlerWithValidFileNameForUseSendFile() throws Exception {
		handler = new StaticFileHandler(directory, "", true);
		when(context.getRequest().getUri()).thenReturn("/content/test.txt");
		
		handler.handleHttpRequest(context);
		verify(context.getResponse()).setContent(any(DefaultFileRegion.class));
	}
	
	@Test
	public void testSanitizeUrlWithInvalidValues() {
		String[] values = {"test.txt", "/../test.txt", "\\dir\\..\\etc\\password\\test.txt", "/../../etc/password"};
		
		for (String value : values) {
			assertNull(handler.sanitizeUri(value));
		}
	}
		
	@Test
	public void testSanitizeUrlWithValidValues() {
		String[] values = {"/test.txt", "/directory/test.txt", null};
		
		for (String value : values) {
			assertNotNull(handler.sanitizeUri(value));
		}		
	}
	
	@Test
	public void testBuildPath() {
		assertEquals("/directory/test.txt", handler.buildPath("/test.txt", "/directory", ""));
		assertEquals("/directory/index.html", handler.buildPath("/", "/directory", "/index.html"));
		assertEquals("/directory/index.html", handler.buildPath("", "/directory", "/index.html"));
		assertEquals("/directory/index.html", handler.buildPath(null, "/directory", "/index.html"));
		assertEquals("/directory/index.html", handler.buildPath("\\", "/directory", "/index.html"));
	}
	
	@Test
	public void testFindMimeTypeWithValidValues() {
		assertEquals("text/plain", handler.findMimeType("/test.txt"));
		assertEquals("image/png", handler.findMimeType("/directory/image.png"));
	}
	
	@Test
	public void testFindMimeTypeWithInvalidValues() {
		assertEquals("text/plain", handler.findMimeType(""));
		assertEquals("text/plain", handler.findMimeType("/directory/image"));
	}
	
}
