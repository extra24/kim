package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import common.exception.ArticleContentNotFoundException;
import common.exception.ArticleNotFoundException;
import jdbc.ConnectionProvider;

public class ReadArticleService {
	
	//싱글톤
	private static ReadArticleService instance = new ReadArticleService();
	public static ReadArticleService getInstance() {
		return instance;
	}
	private ReadArticleService () {}
	
	public ArticleData getArticle(int articleId, boolean increaseReadCount) {
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			Article article = articleDao.selectById(conn, articleId);
			if(article == null) {
				throw new ArticleNotFoundException("article이 없습니다.");
			}
			ArticleContent articleContent = articleContentDao.selectById(conn, articleId);
			if(articleContent == null) {
				throw new ArticleContentNotFoundException("articleContent가 없습니다.");
			}
			if(increaseReadCount) {
				articleDao.increaseReadCount(conn, articleId);
			}
			
			return new ArticleData(article, articleContent);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
}
