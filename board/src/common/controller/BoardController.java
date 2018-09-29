package common.controller;
//common.controller패키지 생성후 
//0914 controller복사 붙여넣기

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.handler.CommandHandler;
import common.handler.NullHandler;

//핸들러 정보를 담고있는 프로퍼티에서 커맨드와 핸들러 클래스 정보를 가져와야 한다.
//uri요청에 따라 핸들러 객체로 process메소드를 실행

public class BoardController extends HttpServlet {
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();

	@Override
	public void init() throws ServletException {
		//핸들러와 커맨드 명령어 정보가 있는 프로퍼티 파일을 프로퍼티 객체에 담기
		Properties prop = new Properties();
		
		String configFilePath = getServletContext().getRealPath(getInitParameter("handlerConfigFile"));
		
		try (FileReader fr = new FileReader(configFilePath)) {
			prop.load(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//프로퍼티에 담긴 것을 Map에 객체로 만들어서 담음
		for (Object key : prop.keySet()) {
			String command = (String) key; 
			try {
				
				Class handlerClass = Class.forName(prop.getProperty(command));
				
				CommandHandler handlerInstance = 
						(CommandHandler) handlerClass.getDeclaredConstructor().newInstance();
				
				commandHandlerMap.put(command, handlerInstance);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	//여기서는 하나로 통합하여 작업을 하고 
	//추후에 핸들러에서 구분을 하여 로직을 분기처리 할 예정
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String command = req.getRequestURI();
		System.out.println("command :"+ command);
		
		//앞을 잘라내고 주소 뒤 명령부분만 남김
		if(command.indexOf(req.getContextPath())==0) {
			command = command.substring(req.getContextPath().length());
		}
		System.out.println("수정 후 command: "+command);

		
		String viewPage = null;

		
		CommandHandler handler = null;
		//null일 때 null핸들러로 빈 페이지를 반환
		if(command == null) {
			handler = new NullHandler();
		}else {
			handler = commandHandlerMap.get(command);
		}
		try {
			//핸들러로부터 결과 페이지 정보를 받고
			viewPage = handler.process(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(viewPage != null) {
			//결과 페이지로 전환시킴
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, resp);
		}
	}

}
