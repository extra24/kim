package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import user.dao.UserDao;
import user.model.User;

//사용자로 부터 입력받은 데이터를 통해 
//정보가 정확한지 또는 사용자가 존재하는지 확인
public class LoginService {
	
	//싱글톤 사용
	private static LoginService instance = new LoginService();
	public static LoginService getInstance() {
		return instance;
	}
	private LoginService() {}
	
	//성공시 AuthUser객체를 반환시켜주는 메소드
	//id, pwd가 정상적이라면 autoUser을 반환 아니면 exception처리할것이다.
	public AuthUser login(String loginId, String password) throws SQLException {
		
		
		UserDao userDao = UserDao.getInstance();
		try(Connection conn = ConnectionProvider.getConnection()){
			//아이디를 통해 사용자 객체를 select해서 가져옴
			User user = userDao.selectByLoginId(conn, loginId);
			
			//사용자가 없다면(아이디가 디비에 없을 시)
			//객체가 있으면 비밀번호를 비교하고 객체가 없으면 없는 사용자라고 예외를 날린다.
			if(user == null) {
				throw new LoginFailException("없는 사용자");
			}
			//비밀번호랑 아이디가 맞는지 확인
			//데이터베이스의 패스워드와 입력받은 패스워드가 같은지 확인했는데 같지 않을 때
			if(!user.matchPassword(password)) {
				throw new LoginFailException("비밀번호가 틀림");
			}
			
			//인증이 완료되었으니 auth객체를 반환
			return new AuthUser(user.getUserId(),user.getLoginId(),user.getName());
		}
		
		
	}
	
}
