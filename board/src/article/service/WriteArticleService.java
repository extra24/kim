package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.ConnectionProvider;

public class WriteArticleService {

	// 싱글톤
	private static WriteArticleService instance = new WriteArticleService();
	public static WriteArticleService getInstance() {
		return instance;
	}
	private WriteArticleService() {	}

	public int write(WriteRequest wr) {
		System.out.println(wr.getTitle()+"testestsetsetsetse");
		ArticleDao articleDao = ArticleDao.getInstance();
		ArticleContentDao articleContentDao = ArticleContentDao.getInstance();

		try (Connection conn = ConnectionProvider.getConnection()) {
			try {
				conn.setAutoCommit(false);
				Article article = new Article(wr.getWriter(), wr.getTitle());
				Article savedArticle = articleDao.insert(conn, article);
				if (savedArticle == null) {
					throw new RuntimeException("게시글 삽입 실패");
				}
				// 아티클 아이디는 반환된 아티클 정보를 가지고 사용한다.
				ArticleContent content = new ArticleContent(savedArticle.getArticleId(), wr.getContent());
				ArticleContent savedContent = articleContentDao.insert(conn, content);
				if (savedContent == null) {
					throw new RuntimeException("content 삽입 실패");
				}
				conn.commit();
				return savedArticle.getArticleId();
			} catch (SQLException e) {
				conn.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				conn.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}

}
