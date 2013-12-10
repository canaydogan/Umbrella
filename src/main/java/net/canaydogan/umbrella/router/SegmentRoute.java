package net.canaydogan.umbrella.router;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.canaydogan.umbrella.HttpRequest;

public class SegmentRoute implements Route {
	
	protected String regex;
	protected Set<String> parts;
	
	public SegmentRoute(String route) {
		regex = buildRegex(route);
		parts = parseRouteDefinition(route);
	}

	@Override
	public RouteMatch match(HttpRequest request) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(request.getUri());
		
		if (matcher.matches()) {
			RouteMatch match = new RouteMatch();
			Iterator<String> partIterator = parts.iterator();
			
			for (int i = 1; i <= matcher.groupCount(); i++) {
				match.setParam(partIterator.next(), matcher.group(i));
			}					
			
			return match;
		}
		
		return null;
	}
	
	protected Set<String> parseRouteDefinition(String route) {
		Pattern pattern = Pattern.compile(":([a-z0-9]*)");
		Matcher matcher = pattern.matcher(route);
		Set<String> parts = new LinkedHashSet<>();
		
		while (matcher.find()) {
			parts.add(matcher.group(1));
		}
		
		return parts;
	}
	
	protected String buildRegex(String route) {
		Pattern pattern = Pattern.compile("(:[a-z]*)");
		Matcher matcher = pattern.matcher(route.replace(".", "\\."));
		
		return ".*" + matcher.replaceAll("([^/.]{1,})") + '$';
	}

}
