package actividad1;

public class MatriculaException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public static final String ERROR_LONGITUD = "Longitud incorrecta de matrícula";

	public static final String ERROR_DIGITOS = "Lo primero no son dígitos";

	public static final String ERROR_MATRICULA_NULA = "Matricula nula";
	
	public MatriculaException(String string) {
		super(string);
	}

	

}
