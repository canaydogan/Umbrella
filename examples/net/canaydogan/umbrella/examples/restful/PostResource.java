package net.canaydogan.umbrella.examples.restful;

import net.canaydogan.umbrella.handler.HttpHandlerContext;
import net.canaydogan.umbrella.restful.Resource;

public class PostResource implements Resource {

	@Override
	public Object get(HttpHandlerContext context) throws Exception {
		System.out.println(context.getRouteMatch().getParam("id"));
		return "PostResource Get";
	}

	@Override
	public Object getList(HttpHandlerContext context) throws Exception {
		return "PostResource Get List";
	}

	@Override
	public Object create(HttpHandlerContext context) throws Exception {
		return "PostResource Create";
	}

	@Override
	public Object update(HttpHandlerContext context) throws Exception {
		return "PostResource Update";
	}

	@Override
	public Object delete(HttpHandlerContext context) throws Exception {
		return "PostResource Delete";
	}

}
