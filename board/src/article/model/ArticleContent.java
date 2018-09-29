package article.model;

//게시글의 내용을 담는 객체
public class ArticleContent {
	private int articleId;
	private String content;

	//constructor
	public ArticleContent(int articleId, String content) {

		this.articleId = articleId;
		this.content = content;
	}

	//getter
	public int getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
