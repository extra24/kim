package user.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.exception.DuplicateException;
import common.handler.CommandHandler;
import user.service.JoinRequest;
import user.service.JoinService;

//사용자의 요청을 받아서 폼 화면을 보여줄지, 데이터로 유저를 삽입할지 구분하여 처리

public class JoinHandler implements CommandHandler {

	// 회원가입 페이지 주소를 상수로 만든것
	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";

	// 사용자는 url을 이렇게 칠건데 board/join
	// form에서 전송할 action역시 board에 join으로 보낼 것이다.
	// 같은 명령을 보내지만 명령이 온 방식에 따라서 분기처리를 한다.
	// get방식으로 요청이 오면 폼을 보여주는 뷰로 리턴을 하고
	// post방식(submit을 눌렀다는 의미)으로 요청이 오면 회원가입을 처리하고 겨과를 보여주는 뷰로 리턴
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (req.getMethod().equals("GET")) {
			return processForm(req, resp);
		} else if (req.getMethod().equals("POST")) {
			return processSubmit(req, resp);
		} else {
			resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

	}

	// 페이지를 다시 돌려줌
	private String processForm(HttpServletRequest req, HttpServletResponse resp) {

		return FORM_VIEW;
	}

	// 회원가입페이지로 돌아감
	// 사용자로부터 회원가입데이터를 입력받아
	// submit버튼을 클릭해서 데이터가 넘어왔을 때 실행하는 메소드
	private String processSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// 파라미터를 통해서 입력받은 데이터를 JoinRequest 객체에 담음
		JoinRequest joinRequest = new JoinRequest();
		joinRequest.setLoginId(req.getParameter("loginId"));
		joinRequest.setName(req.getParameter("name"));
		joinRequest.setPassword(req.getParameter("password"));
		joinRequest.setConfirmPassword(req.getParameter("confirmPassword"));

		// JoinRequest를 통해 입력받은 데이터가 제대로 입력되어있는지.
		// 잘못된 정보는 errors라는 맵에 넣어놓기 위해 errors라는 맵을 생성
		Map<String, Boolean> errors = new HashMap<String, Boolean>();

		// errors는 view에서 표출해주기 위해 request속성 값으로 넣어준다.
		req.setAttribute("errors", errors);

		// 데이터 검증,무결성 체크 (패스워드와 패스워드 확인 두 개가 서로 같은지를 확인)
		joinRequest.validate(errors);
		// validate메소드가 지나오면, errors맵에는 잘못된 데이터필드명과 함께 true value값이 추가되어 있음

		// 잘못 들어왔으면 다시 폼화면으로 반환
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		// 그래서 잘 입력되면 JoinService를 통해 회원가입 로직 수행
		JoinService joinService = JoinService.getInstance();

		try {

			// join인 로직은 아이디가 중복일때 예외를 여기로 던져줌
			joinService.join(joinRequest);
			// 성공화면으로 반환
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateException e) {
			// 아이디가 중복일 때 service가 발생시킨 예외를 받아서 처리해줌
			errors.put("duplicateId", true);
			return FORM_VIEW;
		}

	}

}
