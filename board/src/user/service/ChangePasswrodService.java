package user.service;

import java.sql.Connection;
import java.sql.SQLException;

import common.exception.InvalidPasswordException;
import common.exception.UserNotFoundException;
import jdbc.ConnectionProvider;
import user.dao.UserDao;
import user.model.User;

public class ChangePasswrodService {
	//싱글톤
	private static ChangePasswrodService instance = new ChangePasswrodService();
	public static ChangePasswrodService getInstance() {
		return instance;
	}
	private ChangePasswrodService () {}
	
	//비밀번호를 병경하기 위한 비즈니스 로직을 수행
	//로그인아이디, 현 비번, 새 비번을 인자로 받고
	//그것을 통해서 비번이 제대로 되었는지, 현재 있는 사용자인지 확인하고 
	//로직을 수행한다.
	
	public void changePassword(String loginId, String oldPwd, String newPwd) {
		UserDao userDao = UserDao.getInstance();
		try (Connection conn = ConnectionProvider.getConnection()){
			
			try {
				conn.setAutoCommit(false);
				//user객체를 받아오고
				User user = userDao.selectByLoginId(conn, loginId);
				
				//없는 유저라면
				if(user ==null ) {
					throw new UserNotFoundException("없는 유저");
				}
				//user 객체와 입력받은 비밀번호를 비교
				//잘못된 비밀번호를 입력했다면
				if(!user.matchPassword(oldPwd)) {
					throw new InvalidPasswordException("잘못된 비밀번호");
				}
				//업데이트에 보낼 객체의 비번을 새로 넣어줌
				//정상이면 update
				user.setPassword(newPwd);
				userDao.update(conn, user);
				conn.commit();
				
			}catch (SQLException e) {
				//아니면 예외날리고 롤백
				conn.rollback();
				throw new RuntimeException(e);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
