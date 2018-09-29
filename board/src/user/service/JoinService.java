package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import common.exception.DuplicateException;
import jdbc.ConnectionProvider;
import user.dao.UserDao;
import user.model.User;

//회원 가입 처리하는 서비스
public class JoinService {

	//싱글톤
	private static JoinService instance = new JoinService();
	public static JoinService getInstance() {
		return instance;
	}
	private JoinService() {}
	
	//join 회원가입하는 로직을 구현
	//입력되는 폼에 있는 데이터가 잘 들어왔는지 무결성 체크도 해야함
	//그래서 폼에서 입력받은 데이터는 user가 아니라 joinRequest라는 객체로 받아서 처리
	public void join(JoinRequest joinReq) {
		UserDao userDao = UserDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			//트랜젝션 처리로 로그인 아이디가 중복이 있는지 없는지 화긴하여 결과를 반환해줌
			//없으면 삽입, 있으면 예외를 발생시킴
			conn.setAutoCommit(false);
			try {
				User user = userDao.selectByLoginId(conn, joinReq.getLoginId());
				if(user != null) {
					conn.rollback();
					throw new DuplicateException("아이디 중복");
				}
				userDao.inset(conn, new User(joinReq.getLoginId(), joinReq.getName(), joinReq.getPassword()));
				conn.commit();
			}catch(SQLException e) {
				conn.rollback();
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
