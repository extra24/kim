package article.service;

import java.util.Map;

import article.model.Writer;

public class WriteRequest {
	private Writer writer;
	private String title;
	private String content;
	
	//constructor 생성자
	public WriteRequest(Writer writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
	}

	//getter
	public Writer getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	//무결성 체크하는 메소드
	public void validate(Map<String, Boolean>errors) {
		if(title == null || title.trim().isEmpty()) {
			errors.put("title", true);
		}
	}
	
	
	
}
