package article.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.DeleteArticleService;
import article.service.DeleteRequest;
import article.service.ReadArticleService;
import auth.service.AuthUser;
import common.exception.ArticleNotFoundException;
import common.exception.PermissionDeniedException;
import common.handler.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/deleteForm.jsp"; //삭제할지말지 알려주는 form

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req,resp);
		}else if(req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req,resp);
		}else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
}

	private String processForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ReadArticleService readArticleService = ReadArticleService.getInstance();
		try{
			//게시글의 아이디를 받아서 
			String noStr = req.getParameter("no");
			int no = Integer.parseInt(noStr);

			//게시글을 읽어오는 서비스를 이용하여 데이터를 받아온다.
			ArticleData articleData = readArticleService.getArticle(no, false); //조회수를 증가시키지 않기 위해 false사용
			AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
			
			//사용자가 수정을 할 수 있는 권한이 있는지 확인하고->작성자랑 로그인되어있는 아이디랑 확인해야함
			if(!canModify(authUser,articleData)) {
				//없으면 에러를 response 에 담아서 보낸다. SC_FORBIDDEN
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			//있으면 deleteRequest에 담아서 수정 화면을 보여준다.
			DeleteRequest delReq = new DeleteRequest(authUser.getUserId(), no);
			req.setAttribute("delReq", delReq);
			
			return FORM_VIEW;
			
		}catch(ArticleNotFoundException e){
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	
	}
	//사용자가 수정을 할 수 있는 권한이 있는지 확인하고->작성자(WirterId)랑 로그인되어있는 아이디(UserId)랑 확인해야함
	private boolean canModify(AuthUser authUser, ArticleData articleData) {
		int writeId = articleData.getArticle().getWriter().getWriterId();
		
		return authUser.getUserId()==writeId;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		AuthUser authUser = (AuthUser)req.getSession().getAttribute("authUser");
		String noStr = req.getParameter("no");
		int no = Integer.parseInt(noStr);
		
		DeleteRequest delReq = new DeleteRequest(authUser.getUserId(), no);
		req.setAttribute("delReq", delReq);
				
		try {
			DeleteArticleService deleteArticleService = DeleteArticleService.getInstance();
			deleteArticleService.delete(delReq);
			return "/WEB-INF/view/deleteSuccess.jsp";
		}catch(ArticleNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}catch(PermissionDeniedException e) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}

}
