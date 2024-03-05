package co.com.prueba.sopadeletras.response;

public class ResultadoBusqueda {

	private String searchWord;
	private int rows;
	private String word;
	private boolean contains;
	private int startRow;
	private int startColumn;

	public ResultadoBusqueda(String searchWord, int rows, String word, boolean contains, int startRow,
			int startColumn) {
		this.searchWord = searchWord;
		this.rows = rows;
		this.word = word;
		this.contains = contains;
		this.startRow = startRow;
		this.startColumn = startColumn;
	}

	public ResultadoBusqueda(String searchWord, int rows, String word, boolean contains) {
		this.searchWord = searchWord;
		this.rows = rows;
		this.word = word;
		this.contains = contains;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public int getRows() {
		return rows;
	}

	public String getWord() {
		return word;
	}

	public boolean isContains() {
		return contains;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

}
