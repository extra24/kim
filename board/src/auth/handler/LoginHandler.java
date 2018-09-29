package auth.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.AuthUser;
import auth.service.LoginFailException;
import auth.service.LoginService;
import common.handler.CommandHandler;

public class LoginHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";

	// get,post를 나눔
	// get은 로그인 화면요청일 것이고, post는 로그인 요청일 것이다.
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equals("GET")) {
			return processForm(req, resp);
		} else if (req.getMethod().equals("POST")) {
			return processSubmit(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		//서비스 객체 받기
		LoginService loginService = LoginService.getInstance(); 
		
		//request에서 파라미터를 받음
		String loginId = ((req.getParameter("loginId")==null)?null:req.getParameter("loginId").trim());//여백을 지우는trim()사용
		String password=((req.getParameter("password")==null)?null:req.getParameter("password").trim());
		
		Map<String,Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		//비었는지 확인하고
		if(loginId == null || loginId.isEmpty()) {
			errors.put("loginId", true);
		}
		if(password == null || password.isEmpty()) {
			errors.put("password", true);
		}
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		try {
			
			//서비스를 통해서 로그인을 실행 및 검증
			//문제가 발생하면 ex가 발생하고 바로 catch로 들어감
			
			AuthUser authUser = loginService.login(loginId, password);
			//성공하면 auth객체를 만들어서 세션에 넣고
			req.getSession().setAttribute("authUser", authUser);
			//첫 화면으로 돌려준다.
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		}catch(LoginFailException e) {
			System.out.println(e.getMessage());
			//실패하면 에러에 실패정보를 넣고 로그인 화면으로 다시 보낸다.
			errors.put("IdOrPwdNotMatch", true);
			return FORM_VIEW;
		}
		
		
		
		return null;
	}

}
