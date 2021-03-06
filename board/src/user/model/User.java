package user.model;

import java.time.LocalDateTime;

//user테이블에 있는 데이터를 담을 클래스 = DTO
public class User {
	private int userId;
	private String loginId;
	private String name;
	private String password;
	private LocalDateTime regdate;
	
	
	//생성자. 하나는 디비에서 가져올 때 사용할 생성자 또 하나는 입력할 때 쓸 생성자
	//디비에서 가져오는 것은 5개 데이터를 담을 수 있게 만들면 되지만 
	public User(int userId, String loginId, String name, String password, LocalDateTime regdate) {
		this.userId = userId;
		this.loginId = loginId;
		this.name = name;
		this.password = password;
		this.regdate = regdate;
	}


	//입력할 데이터는 자동으로 들어갈 데이터를 넣을 필요가 없어서 둘로 나눈것이다.
	public User(String loginId, String name, String password) {
		this.loginId = loginId;
		this.name = name;
		this.password = password;
	}


	//getter, setter
	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LocalDateTime getRegdate() {
		return regdate;
	}


	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}
	
	//입력받은 데이터와 필드의(기존의) 데이터가 같은지 확인하는 메소드
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
	
	
}
