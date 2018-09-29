package article.model;

//작성자의 정보를 담는 객체
public class Writer {

	private int writerId;
	private String writerName;
	
	//constructor
	public Writer(int writerId, String writerName) {
	
		this.writerId = writerId;
		this.writerName = writerName;
	}

	//getter만
	public int getWriterId() {
		return writerId;
	}

	public String getWriterName() {
		return writerName;
	}
	
	
	
	
}
