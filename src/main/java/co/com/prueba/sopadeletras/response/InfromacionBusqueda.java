package co.com.prueba.sopadeletras.response;

public class InfromacionBusqueda {

	private boolean found;
	private int startRow;
	private int startColumn;

	public InfromacionBusqueda(boolean found, int startRow, int startColumn) {
		this.found = found;
		this.startRow = startRow;
		this.startColumn = startColumn;
	}

	public boolean isFound() {
		return found;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

}
