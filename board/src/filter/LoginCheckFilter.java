package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {

	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//세션을 받아서 세션이 살아있는지 확인을 한다.(다운캐스팅)
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);

		//세션이 없다면 로그인 페이지로 이동.
		if(session == null || session.getAttribute("authUser")==null) {
			HttpServletResponse response = (HttpServletResponse)resp;
			response.sendRedirect(request.getContextPath()+"/login");
		}else {
			//세션이 있으면 요청한 기능이 있는 곳으로 보낸다.
			chain.doFilter(req, resp);			
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	@Override
	public void destroy() {

	}

}
