package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import article.model.Article;
import article.model.Writer;

public class ArticleDao {

	// 싱글톤
	private static ArticleDao instance = new ArticleDao();

	public static ArticleDao getInstance() {
		return instance;
	}

	private ArticleDao() {
	}

	// 게시글 insert 구현
	public Article insert(Connection conn, Article article) throws SQLException {
		String sql = "insert into article(writerId,writerName,title) values(?,?,?) ";
		try (PreparedStatement pst = conn.prepareStatement(sql); Statement st = conn.createStatement()) {
			pst.setInt(1, article.getWriter().getWriterId());
			pst.setString(2, article.getWriter().getWriterName());
			pst.setString(3, article.getTitle());
			int insertedCount = pst.executeUpdate();

			if (insertedCount > 0) {
				// 최근에 insert(등록된) 아이디값을 가져온다.
				try (ResultSet rs = st.executeQuery("select last_insert_id() from article")) {
					if (rs.next()) {
						int articleId = rs.getInt(1);
						article.setArticleId(articleId);
						return article;
					}

				}
			}
			return null;

		}

	}

	// 게시글 개수를 가져오는 메소드
	public int selectCount(Connection conn) throws SQLException {
		String sql = "select count(*) from article";
		try (PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		}

	}
	//리미트를 이용한 리스트를 가져오는 쿼리
	public List<Article> select(Connection conn, int startRow, int size) throws SQLException{
		String sql = "select * from article order by articleId desc limit ?,?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, startRow);
			pst.setInt(2, size);
			
			try(ResultSet rs = pst.executeQuery()){
				List<Article> artList = new ArrayList<Article>();
				while(rs.next()) {
					artList.add(convArticle(rs));
				}
				return artList;
			}
		}
		
	}
	//조회수를 올리는 메소드
	public void increaseReadCount(Connection conn, int articleId) throws SQLException {
		String sql = "update article set readCnt = readCnt+1 where articleId=?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, articleId);
			pst.executeUpdate();
		}
	}
	
	
	//게시글 번호로 특정 게시글을 가져오는 메소드
	public Article selectById(Connection conn, int no) throws SQLException {
		String sql = "select * from article where articleId = ?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, no);
			try(ResultSet rs = pst.executeQuery()){
				Article article = null;
				if(rs.next()) {
					article = convArticle(rs);
				}
				return article;
			}
		}
	}
	
	//제목을 수정하는 update
	public int update(Connection conn, int articleId, String title) throws SQLException {
		String sql = "update article set title = ? where articleId =?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, title);
			pst.setInt(2, articleId);
			return pst.executeUpdate();
		}
	}
	
	//삭제하는 메소드
	public int delete(Connection conn, int articleId) throws SQLException {
		String sql = "delete from article where articleId =?";
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setInt(1, articleId);
			return pst.executeUpdate();
		}
	}
	
	
	//resultSet으로 나온 결과를 article객체로 생성해서 담는 메소드
	private Article convArticle(ResultSet rs) throws SQLException {
		Article article = new Article(rs.getInt("articleId"), 
				new Writer(rs.getInt("writerId"), rs.getString("writerName")), 
				rs.getString("title"), 
				rs.getTimestamp("wdate").toLocalDateTime(), 
				rs.getTimestamp("udate").toLocalDateTime(), 
				rs.getInt("readCnt")); 
		return article;
	}
	
	

}
