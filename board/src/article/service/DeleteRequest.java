package article.service;

import java.util.Map;

public class DeleteRequest {

	private int userId;
	private int articleId;
	
	//constructor
	public DeleteRequest(int userId, int articleId) {
		this.userId = userId;
		this.articleId = articleId;
	}

	//getter
	public int getUserId() {
		return userId;
	}

	public int getArticleId() {
		return articleId;
	}

	
}
