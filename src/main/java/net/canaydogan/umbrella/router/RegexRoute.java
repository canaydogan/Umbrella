package net.canaydogan.umbrella.router;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.canaydogan.umbrella.HttpRequest;

public class RegexRoute implements Route {

	protected Pattern pattern;
	
	public RegexRoute(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	
	@Override
	public RouteMatch match(HttpRequest request) {

		Matcher matcher = pattern.matcher(request.getUri());
		
		if (matcher.matches()) {
			return new RouteMatch();
		}
		
		return null;
	}

}
