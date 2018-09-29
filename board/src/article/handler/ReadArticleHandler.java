package article.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.service.ArticleData;
import article.service.ReadArticleService;
import common.exception.ArticleContentNotFoundException;
import common.exception.ArticleNotFoundException;
import common.handler.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		 
		//사용자에게 어떤 요청을 받고
		int articleId =Integer.parseInt(req.getParameter("no"));
		//서비스를 이용해서 화면에 보여줄 데이터를 생성하고 
		ReadArticleService articleService = ReadArticleService.getInstance();
		try {
			ArticleData articleData = articleService.getArticle(articleId, true);
			articleData.setContent(articleData.getContent());
			//여기에서 set대신 getContent해서 바꿔줄 수도 있다.
			req.setAttribute("articleData", articleData);
		
			System.out.println(articleData.getContent());
			//화면으로 리턴
			return "/WEB-INF/view/readArticle.jsp";
		}catch(ArticleNotFoundException | ArticleContentNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
	}
	/*private String icon(String articleData) {
		return articleData.replaceAll("\n", "<br/>");	
	}*/

}
