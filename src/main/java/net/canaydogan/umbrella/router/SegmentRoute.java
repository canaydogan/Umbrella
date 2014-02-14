package net.canaydogan.umbrella.router;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.canaydogan.umbrella.HttpRequest;

public class SegmentRoute implements Route {

	protected Set<Set<String>> parts = new HashSet<>();
	protected Map<String, String> defaults;
	protected Set<Pattern> patterns = new HashSet<>();

	public SegmentRoute(Map<String, String> defaults, String... routes) {
		this.defaults = defaults;
		for (String route : routes) {
			this.parts.add(parseRouteDefinition(route));
			this.patterns.add(Pattern.compile(buildRegex(route)));
		}
	}

	public SegmentRoute(String... route) {
		this(new HashMap<String, String>(), route);
	}

	@Override
	public RouteMatch match(HttpRequest request) {
		Iterator<Set<String>> partsIterator = parts.iterator();
		
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(request.getUriWithoutQuery());

			if (matcher.matches()) {
				RouteMatch match = new RouteMatch(defaults);
				Iterator<String> partIterator = partsIterator.next().iterator();

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
		}		

		return null;
	}

	protected Set<String> parseRouteDefinition(String route) {
		Pattern pattern = Pattern.compile(":([a-zA-Z0-9]*)");
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
				.replaceAll("/?([^/.]+)?");
		
		route = Pattern.compile("(:[a-zA-Z0-9]*)")
					.matcher(route)
					.replaceAll("([^/.]+){1}");

		return route;
	}

}