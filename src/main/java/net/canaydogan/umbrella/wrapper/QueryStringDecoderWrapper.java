package net.canaydogan.umbrella.wrapper;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import net.canaydogan.umbrella.HttpQuery;

public class QueryStringDecoderWrapper implements HttpQuery {

	protected QueryStringDecoder decoder;
	protected Map<String, List<String>> params;
	
	public QueryStringDecoderWrapper(QueryStringDecoder decoder) {
		this.decoder = decoder;
		this.params = decoder.parameters();
	}
	
	@Override
	public boolean contains(String name) {
		return params.containsKey(name);
	}

	@Override
	public String get(String name) {
		if (contains(name)) {
			return params.get(name).get(0);
		}
		
		return null;
	}

	@Override
	public List<String> getAll(String name) {
		if (contains(name)) {
			return params.get(name);
		}
		return null;
	}

}
