package article.service;

import java.util.List;

import article.model.Article;

//한 페이지에서 보여줄 게시글 관련 정보와 페이지 관련 정보를 담는 클래스
//페이지(게시글 목록의 한 화면)에 여기서 담겨있는 내용을 출력할 예정
public class ArticlePage {

	// 게시글 정보를 담고있는 객체들의 리스트
	// Article안에는 게시글 아이디,제목, 작성자 아이디, 작성일, 수정일, 조회수가 있다.
	private List<Article> artList;

	// 사용자가 요청한 페이지 번호
	private int currentPage;

	// 전체 페이지 수. 한페이지에 게시글 10개씩 보여준다. ex) 11개일 때는 2페이지가 됨
	private int totalPages;

	// 게시글의 전체 개수
	private int total;

	// 화면 하단에 보여줄 페이지링크의 시작번호
	private int startPage;

	// 화면 하단에 보여줄 페이지링크의 끝 번호
	private int endPage;

	// constructor artList,currentPage,total + int size, int blockSize
	public ArticlePage(List<Article> artList, int currentPage, int total, int size, int blockSize) {
		// size는 한 페이지에 보여줄 게시글의 개수
		// blockSize는 한 페이지에서 보여줄 하단 페이지 링크 블럭 개수
		this.artList = artList;
		this.currentPage = currentPage;
		this.total = total;
		if (total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total / size;
			if (total % size > 0) {
				totalPages++;
			}
			// 예를 들어 현재 페이지번호가 3이고 block이 5일 경우에
			// 무엇부터 시작할지.. 지정해주는 것이다.
			// [1][2][3][4][5] ....[6][7][8][9][10]
			startPage = currentPage / blockSize * blockSize + 1;
			// 페이지 번호와 블럭사이즈가 같아 페이작 넘어가는 것을 막기위함
			if ((currentPage % blockSize) == 0) {
				startPage -= blockSize;
			}
			endPage = startPage + (blockSize - 1);
			// 페이지 수가 5까지 가지않고 3페이지 까지 밖에 없어
			// [1][2][3]과 같이 페이지가 잘릴려면 다음과 같이한다.
			if (endPage > totalPages) {
				endPage = totalPages;
			}

		}
	}
	public boolean hasArticle() {
		return total > 0;
	}
	
	//getter
	public List<Article> getArtList() {
		return artList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotal() {
		return total;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	
	

}
