package co.com.prueba.sopadeletras.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.prueba.sopadeletras.model.BuscaPalabra;
import co.com.prueba.sopadeletras.response.InfromacionBusqueda;
import co.com.prueba.sopadeletras.response.ResultadoBusqueda;

@RestController
public class SopaDeLetrasRestController {

	@PostMapping("/search-horandvert")
	public ResponseEntity<ResultadoBusqueda> searchWord(@RequestBody BuscaPalabra buscaPalabra) {

		if (!isValid(buscaPalabra)) {
			return new ResponseEntity<ResultadoBusqueda>(HttpStatus.FORBIDDEN);
		}

		/**
		 * Llena la matriz con los datos obtenido desde el JSON
		 */
		char[][] matrix = llenarMatriz(buscaPalabra.getSearchword(), buscaPalabra.getRows());
		InfromacionBusqueda busquedaInformacionEstado = buscarPalabraEnMatriz(matrix, buscaPalabra.getWord());

		/**
		 * Valida si encuentra la palabra y retorna la respuesta con su respectivo
		 * estado
		 */
		if (busquedaInformacionEstado.isFound()) {
			ResultadoBusqueda encontrado = new ResultadoBusqueda(buscaPalabra.getSearchword(), buscaPalabra.getRows(),
					buscaPalabra.getWord(), true, busquedaInformacionEstado.getStartRow(),
					busquedaInformacionEstado.getStartColumn());
			return new ResponseEntity<ResultadoBusqueda>(encontrado, HttpStatus.OK);
		} else {
			ResultadoBusqueda noEncontrado = new ResultadoBusqueda(buscaPalabra.getSearchword(), buscaPalabra.getRows(),
					buscaPalabra.getWord(), false);
			return new ResponseEntity<ResultadoBusqueda>(noEncontrado, HttpStatus.OK);

		}
	}

	@PostMapping("/search")
	public ResponseEntity<ResultadoBusqueda> search(@RequestBody BuscaPalabra buscaPalabra) {
		if (!isValid(buscaPalabra)) {
			return new ResponseEntity<ResultadoBusqueda>(HttpStatus.FORBIDDEN);
		}

		/**
		 * Llena la matriz con los datos obtenido desde el JSON
		 */
		char[][] matrix = llenarMatriz(buscaPalabra.getSearchword(), buscaPalabra.getRows());
		InfromacionBusqueda busquedaInformacionEstado = buscarPalabraEnMatriz(matrix, buscaPalabra.getWord());
		InfromacionBusqueda busquedaInformacionEstadoDiagonal = buscarPalabraEnMatrizDiagonal(matrix,
				buscaPalabra.getWord());

		if (busquedaInformacionEstadoDiagonal.isFound()) {
			ResultadoBusqueda encontrado = new ResultadoBusqueda(buscaPalabra.getSearchword(), buscaPalabra.getRows(),
					buscaPalabra.getWord(), true, busquedaInformacionEstado.getStartRow(),
					busquedaInformacionEstado.getStartColumn());
			return new ResponseEntity<ResultadoBusqueda>(encontrado, HttpStatus.OK);

		}

		/**
		 * Valida si encuentra la palabra y retorna la respuesta con su respectivo
		 * estado
		 */
		if (busquedaInformacionEstado.isFound()) {
			ResultadoBusqueda encontrado = new ResultadoBusqueda(buscaPalabra.getSearchword(), buscaPalabra.getRows(),
					buscaPalabra.getWord(), true, busquedaInformacionEstado.getStartRow(),
					busquedaInformacionEstado.getStartColumn());
			return new ResponseEntity<ResultadoBusqueda>(encontrado, HttpStatus.OK);
		} else {
			ResultadoBusqueda noEncontrado = new ResultadoBusqueda(buscaPalabra.getSearchword(), buscaPalabra.getRows(),
					buscaPalabra.getWord(), false);
			return new ResponseEntity<ResultadoBusqueda>(noEncontrado, HttpStatus.OK);

		}
	}

	/**
	 * 
	 * @param searchword
	 * @param rows
	 * @return Crea la mtriz con los datos que obtiene desde el JSON organizando
	 *         según la cantidad de filas indicadas en el parámetro "rows"
	 */
	private char[][] llenarMatriz(String searchword, int rows) {
		char[][] matrix = new char[rows][searchword.length() / rows];
		for (int i = 0; i < searchword.length(); i++) {
			matrix[i / matrix[0].length][i % matrix[0].length] = searchword.charAt(i);
		}
		return matrix;
	}

	/**
	 * 
	 * @param matrix
	 * @param word
	 * @return
	 * 
	 *         Hace las búsqueda de la palabra en la matriz ya creada y retorna la
	 *         información de fila y columna donde empieza la palabra buscada
	 */
	private InfromacionBusqueda buscarPalabraEnMatriz(char[][] matrix, String word) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		int len = word.length();

		// Busqueda Horizontal
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j <= cols - len; j++) {
				if (coincidencia(matrix, i, j, 0, 1, word)) {
					return new InfromacionBusqueda(true, i, j);
				}
			}
		}

		// Busqueda Vertical
		for (int i = 0; i <= rows - len; i++) {
			for (int j = 0; j < cols; j++) {
				if (coincidencia(matrix, i, j, 1, 0, word)) {
					return new InfromacionBusqueda(true, i, j);
				}
			}
		}

		// Busqueda en diagonal
//		for (int i = 0; i <= rows - len; i++) {
//			for (int j = 0; j <= cols - len; j++) {
//				if (coincidencia(matrix, i, j, 1, 1, word)) {
//					return new InfromacionBusqueda(true, i, j);
//				}
//			}
//		}

		return new InfromacionBusqueda(false, -1, -1);
	}

	private InfromacionBusqueda buscarPalabraEnMatrizDiagonal(char[][] matrix, String word) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		int len = word.length();

		// Busqueda en diagonal
		for (int i = 0; i <= rows - len; i++) {
			for (int j = 0; j <= cols - len; j++) {
				if (coincidencia(matrix, i, j, 1, 1, word)) {
					return new InfromacionBusqueda(true, i, j);
				}
			}
		}

		return new InfromacionBusqueda(false, -1, -1);
	}

	/**
	 * 
	 * @param matrix
	 * @param startX
	 * @param startY
	 * @param deltaX
	 * @param deltaY
	 * @param word
	 * @return
	 *
	 *         Valida si los caracteres ingresados coinciden en la fila de ubicacion
	 *         y retorna true si los encuentra
	 */
	private boolean coincidencia(char[][] matrix, int startX, int startY, int deltaX, int deltaY, String word) {
		int len = word.length();
		for (int i = 0; i < len; i++) {
			if (matrix[startX][startY] != word.charAt(i)) {
				return false;
			}
			startX += deltaX;
			startY += deltaY;
		}
		return true;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * 
	 *         valida si los datos ingresados son valido para formar la sopa de
	 *         letras previo a la busqueda de las palabras
	 */
	private boolean isValid(BuscaPalabra request) {
		if (request == null || request.getSearchword() == null || request.getWord() == null || request.getRows() <= 0) {
			return false;
		}
		return true;
	}
}
