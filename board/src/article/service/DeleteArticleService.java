package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import common.exception.ArticleNotFoundException;
import common.exception.PermissionDeniedException;
import jdbc.ConnectionProvider;

public class DeleteArticleService {

	// 싱글톤
		private static DeleteArticleService instance = new DeleteArticleService();

		public static DeleteArticleService getInstance() {
			return instance;
		}

		private DeleteArticleService() {	}

		public void delete(DeleteRequest dr) {
			ArticleDao articleDao = ArticleDao.getInstance();
			ArticleContentDao articleContentDao = ArticleContentDao.getInstance();

			try (Connection conn = ConnectionProvider.getConnection()) {
				try {
					conn.setAutoCommit(false);

					Article article = articleDao.selectById(conn, dr.getArticleId());
					// 게시글이 있는지 확인
					if (article == null) {
						throw new ArticleNotFoundException("없는 게시글");
					}
					// 사용자 권한이 있는지 확인
					if (article.getWriter().getWriterId() != dr.getUserId()) {
						throw new PermissionDeniedException("사용자 권한이 없음");
					}
					// articleDao, articleContentDao를 이용해서 게시글 삭제 메소드를 실행
					articleDao.delete(conn, dr.getArticleId());
					articleContentDao.delete(conn, dr.getArticleId());
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					throw new RuntimeException(e);
				}catch(PermissionDeniedException e) {
					conn.rollback();
					throw e;
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	
}
