package net.canaydogan.umbrella.handler;

import static io.netty.handler.codec.http.HttpHeaders.Names.CACHE_CONTROL;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE;
import static io.netty.handler.codec.http.HttpHeaders.Names.EXPIRES;
import static io.netty.handler.codec.http.HttpHeaders.Names.IF_MODIFIED_SINCE;
import static io.netty.handler.codec.http.HttpHeaders.Names.LAST_MODIFIED;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import net.canaydogan.umbrella.HttpHandler;
import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.HttpRequest;
import net.canaydogan.umbrella.HttpResponse;
import net.canaydogan.umbrella.HttpResponse.Status;
import net.canaydogan.umbrella.handler.exception.ForbiddenException;
import net.canaydogan.umbrella.handler.exception.MethodNotAllowedException;
import net.canaydogan.umbrella.handler.exception.NotFoundException;

public class StaticFileHandler implements HttpHandler {

	protected static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
	
	protected String directory = "";
	
	protected String defaultFile = "";
	
	protected int cacheSeconds = 60;
	
	protected boolean useSendFile = false;
	
	protected String cacheControlType = "private";
	
	protected Map<String, String> mimeTypeMap = getDefaultMimeTypeMap();
	
	public StaticFileHandler() {}
	
	public StaticFileHandler(String directory) {
		this();
		this.directory = directory.replace('/', File.separatorChar);
	}
	
	public StaticFileHandler(String directory, String defaultFile) {
		this(directory);
		this.defaultFile = defaultFile.replace('/', File.separatorChar);
	}
	
	public StaticFileHandler(String directory, String defaultFile, boolean useSendFile) {
		this(directory, defaultFile);
		this.useSendFile = useSendFile;
	}
	
	public StaticFileHandler(String directory, String defaultFile, boolean useSendFile, int cacheSeconds) {
		this(directory, defaultFile, useSendFile);
		this.cacheSeconds = cacheSeconds;
	}
	
	public StaticFileHandler(String directory, String defaultFile, boolean useSendFile, int cacheSeconds, String cacheControlType) {
		this(directory, defaultFile, useSendFile, cacheSeconds);
		this.cacheControlType = cacheControlType;
	}
	
	public StaticFileHandler(String directory, String defaultFile, boolean useSendFile, 
			int cacheSeconds, String cacheControlType, Map<String, String> mimeTypeMap) {
		this(directory, defaultFile, useSendFile, cacheSeconds, cacheControlType);
		this.mimeTypeMap = mimeTypeMap;
	}
	
	@Override
	public boolean handleHttpRequest(HttpHandlerContext context) throws Exception {
		
		if (context.getRequest().getMethod() != HttpRequest.Method.GET) {
			throw new MethodNotAllowedException();
		}
		
		final String uri = sanitizeUri(context.getRequest().getUri());
		
		if (null == uri) {
			throw new ForbiddenException();
		}
		
		final String path = buildPath(uri, directory, defaultFile);
		File file = new File(path);
		
		if (!file.exists()) {
			throw new NotFoundException();
		}
		
		if (file.isDirectory() || !file.isFile()) {
			throw new ForbiddenException();
		}		
		
		String ifModifiedSince = context.getRequest().getHeaderCollection().get(IF_MODIFIED_SINCE);
		
		if (isNotModified(ifModifiedSince, file.lastModified())) {
			context.getResponse().setStatus(Status.NOT_MODIFIED);
			return false;
		}
		
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException fnfe) {
        	throw new NotFoundException();
        }
        long fileLength = raf.length();
        
        context.getResponse().getHeaderCollection().set(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(fileLength));
        context.getResponse().getHeaderCollection().set(HttpHeaders.Names.CONTENT_TYPE, findMimeType(path));
        context.getResponse().setStatus(Status.OK);
        setDateAndCacheHeaders(context.getResponse(), file);        
        
        if (useSendFile) {
        	context.getResponse().setContent(new DefaultFileRegion(raf.getChannel(), 0, fileLength));
        } else {
        	context.getResponse().setContent(new ChunkedFile(raf, 0, fileLength, 8192));
        }        
		
		return false;
	}
	
	public String sanitizeUri(String uri) {
		if (null == uri) {
			return "/";
		}
        // Decode the path.
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                throw new Error();
            }
        }

        if (!uri.startsWith("/")) {
            return null;
        }

        // Convert file separators.
        uri = uri.replace('/', File.separatorChar);
        
        // Simplistic dumb security check.
        // TODO I have to do something serious in the production environment.
        if (uri.contains(File.separator + '.') ||
            uri.contains('.' + File.separator) ||
            uri.startsWith(".") || uri.endsWith(".") ||
            INSECURE_URI.matcher(uri).matches()) {
            return null;
        }       
        
        return uri;
    }
	
	public String buildPath(String uri, String directory, String defaultFile) {
		if (null == uri || uri.equals("") || uri.equals("/") || uri.equals("\\")) {
			uri = defaultFile;
		}
		
		return directory + uri;
	}
	
	protected boolean isNotModified(String ifModifiedSince, long fileLastModified) throws ParseException {
        if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
            Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

            long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
            long fileLastModifiedSeconds = fileLastModified / 1000;
            if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                return true;
            }
        }
        
        return false;
	}
	
	private void setDateAndCacheHeaders(HttpResponse response, File file) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

        // Date header
        Calendar time = new GregorianCalendar();
        response.getHeaderCollection().set(DATE, dateFormatter.format(time.getTime()));

        // Add cache headers
        time.add(Calendar.SECOND, cacheSeconds);
        response.getHeaderCollection().set(EXPIRES, dateFormatter.format(time.getTime()));
        response.getHeaderCollection().set(CACHE_CONTROL, cacheControlType + ", max-age=" + cacheSeconds);
        response.getHeaderCollection().set(LAST_MODIFIED, dateFormatter.format(new Date(file.lastModified())));
    }
	
	protected Map<String, String> getDefaultMimeTypeMap() {
		Map<String, String> map = new HashMap<>();
		
		map.put("txt", "text/plain");
        map.put("css", "text/css");
        map.put("csv", "text/csv");
        map.put("htm", "text/html");
        map.put("html", "text/html");
        map.put("xml", "text/xml");
        map.put("js", "text/javascript");
        map.put("xhtml", "application/xhtml+xml");
        map.put("json", "application/json");
        map.put("pdf", "application/pdf");
        map.put("zip", "application/zip");
        map.put("tar", "application/x-tar");
        map.put("gif", "image/gif");
        map.put("jpeg", "image/jpeg");
        map.put("jpg", "image/jpeg");
        map.put("tiff", "image/tiff");
        map.put("tif", "image/tiff");
        map.put("png", "image/png");
        map.put("swf", "application/x-shockwave-flash");
        map.put("svg", "image/svg+xml");
        map.put("ico", "image/vnd.microsoft.icon");
        
        return map;
	}

	public String findMimeType(String uri) {
		String ext = uri.lastIndexOf(".") != -1 ? uri.substring(uri.lastIndexOf(".") + 1) : null;
        String mimeType = mimeTypeMap.get(ext);
        
        if (mimeType == null) {
        	mimeType = "text/plain";
        }
        
        return mimeType;
	}

}
