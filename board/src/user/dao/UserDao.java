package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import user.model.User;

//데이터 베이스(DB)의 user 테이블에 쿼리를 실행할 클래스
public class UserDao {
	
	
	
	//싱글톤으로 만들기
	private static UserDao instance = new UserDao();
	public static UserDao getInstance() {
		return instance;
	}
	private UserDao() {}
	
	
	//회원가입 시 필요한 쿼리 메소드 작성
	//insert(등록 용), select(중복 체크 용)
	
	//안에 내용물이 있는 user객체를 받아서 데이터베이스에 user을 삽입
	public int inset(Connection conn, User user) throws SQLException {
		String sql = "insert into user(loginId,name,password) values(?,?,?)";
		try (PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, user.getLoginId());
			pst.setString(2, user.getName());
			pst.setString(3, user.getPassword());
			return pst.executeUpdate();
		}
	}
	
	//loginId가 중복인가를 확인하려면 loginId값이 전달되어야 한다.
	public User selectByLoginId(Connection conn,String loginId) throws SQLException {
		String sql ="select * from user where loginId =?";
		try (PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1,loginId);
			User user = null;
			//결과값을 받아와야함 ResultSet에 받아야한다.
			try(ResultSet rs = pst.executeQuery()){
				if(rs.next()) {
					
				user = new User(rs.getInt("userId"),
						rs.getString("loginId"),
						rs.getString("name"),
						rs.getString("password"),
						rs.getTimestamp("regdate").toLocalDateTime());
				}
			}
			return user;
		}
		
	}
	
	//사용자 정보 수정
	public void update(Connection conn, User user) throws SQLException {
		String sql ="update user set name =?, password =? where userId=?";
		try (PreparedStatement pst = conn.prepareStatement(sql)){
			pst.setString(1, user.getName());
			pst.setString(2, user.getPassword());
			pst.setInt(3, user.getUserId());
			pst.executeUpdate();
		}
	}
	
}
