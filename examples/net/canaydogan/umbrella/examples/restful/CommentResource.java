package net.canaydogan.umbrella.examples.restful;

import net.canaydogan.umbrella.HttpHandlerContext;
import net.canaydogan.umbrella.restful.Resource;

public class CommentResource implements Resource {

	@Override
	public Object get(HttpHandlerContext context) throws Exception {
		return "CommentResource Get";
	}

	@Override
	public Object getList(HttpHandlerContext context) throws Exception {
		return "CommentResource Get List";
	}

	@Override
	public Object create(HttpHandlerContext context) throws Exception {
		return "CommentResource Create";
	}

	@Override
	public Object update(HttpHandlerContext context) throws Exception {
		return "CommentResource Update";
	}

	@Override
	public Object delete(HttpHandlerContext context) throws Exception {
		return "CommentResource Delete";
	}

}
