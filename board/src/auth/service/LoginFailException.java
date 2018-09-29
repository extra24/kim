package auth.service;

//로그인 실패시 던져줄 예외
public class LoginFailException extends RuntimeException {
	public LoginFailException(String message) {
		super(message);
	}
}
