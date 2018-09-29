package user.service;

import java.util.Map;

//폼에서 받은 데이터를 담고 무결성 체크하는 클래스
public class JoinRequest {
	//폼에서 입력되는 데이터
	private String loginId;
	private String name;
	private String password;
	private String confirmPassword; //패스워드 확인
	
	//getter,setter
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	//패스워드가 같은지 확인하는 메소드
	public boolean isEqPassword() {
		return password != null && password.equals(confirmPassword);
	}
	
	//입력받은 데이터가 비어있는지 확인하는 메소드
	//결과를 보여줄 에러맵, 값, 폼필드이름을 인자로 받는 메소드
	private void checkEmpty(Map<String,Boolean> errors,String value,String fieldName) {
		//값이 없거나 비어있을 때 true값을 넣어준다
		if(value == null || value.isEmpty()) {
			//비거나 문제가 있다면 그 결과를 화면으로 다시 전송하기 위해서 
			//객체에 담아서 결과 화면에 보내줌
			errors.put(fieldName, true);			
		}
	}
	
	//입력받은 데이터가 제대로 들어왔는지 검사하는 메소드
	public void validate (Map<String,Boolean>errors) {
		//입력받은 갑이 비어있는게 있는지 확인해서 있다면 errors에 true를 넣음
		//문제가 있음을 알려주는 것이다.
		checkEmpty(errors,loginId,"loginId");
		checkEmpty(errors,name,"name");
		checkEmpty(errors,password,"password");
		checkEmpty(errors,confirmPassword,"confirmPassword");
		//패스워드 확인 부분이 입력되어 있다면 
		if(!errors.containsKey("confirmPassword")) {
			//두 개의 패스워드를 비교
			if(!isEqPassword()) {
				errors.put("notMatch", true);
			}
		}
	}
	
}
