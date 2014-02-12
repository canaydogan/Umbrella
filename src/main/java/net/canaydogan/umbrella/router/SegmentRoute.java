package net.canaydogan.umbrella.router;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.canaydogan.umbrella.HttpRequest;

public class SegmentRoute implements Route {

	protected Set<String> parts;
	protected Map<String, String> defaults;
	protected Pattern pattern;

	public SegmentRoute(String route, Map<String, String> defaults) {
		this.parts = parseRouteDefinition(route);
		this.defaults = defaults;
		this.pattern = Pattern.compile(buildRegex(route));
	}

	public SegmentRoute(String route) {
		this(route, new HashMap<String, String>());
	}

	@Override
	public RouteMatch match(HttpRequest request) {
		Matcher matcher = pattern.matcher(request.getUriWithoutQuery());

		if (matcher.matches()) {
			RouteMatch match = new RouteMatch(defaults);
			Iterator<String> partIterator = parts.iterator();

			for (int i = 1; i <= matcher.groupCount(); i++) {
				String value = matcher.group(i);
				String key = partIterator.next();
				
				if (null == value && defaults.containsKey(key)) {
					value = defaults.get(key);
				}				
				if (null != value) {
					match.setParam(key, value);	
				}				
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
		route = route.replace(".", "\\.");
		
		route = Pattern.compile("(\\[.*\\])")
				.matcher(route)
				.replaceAll("/?+([^/.]{1,})?");
		
		route = Pattern.compile("(:[a-z]*)")
					.matcher(route)
					.replaceAll("([^/.]{1,})");
		
		return ".*" + route + '$';
	}

}