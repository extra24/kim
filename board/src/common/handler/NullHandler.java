package common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NullHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//response 에 응답을 404로 응답!!!
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

}
