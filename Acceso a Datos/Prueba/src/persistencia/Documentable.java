package persistencia;

import excepciones.LineaMalFormadaException;

/**Interface de todos aquellos elementos que sean documentables,
 * es decir, que se puedan leer y escribir de un fichero*/
public interface Documentable {
	
	/**Devuelve la cadena de texto que irá en el documento
	 * al que vaya a volcar los datos
	 * @return la línea de datos del objeto*/
	public String marshalling();
	
	/**Devuelve la cabecera asociada al objeto
	 * @return la cabecera del archivo*/
	public String cabecera();
	
	/**Recibe una línea del fichero y crea un objeto
	 * a partir de la misma o lanza excepción si la línea
	 * no es correcta
	 * @param linea la línea a parsear
	 * @throws LineaMalFormadaException*/
	public void parseLínea(String linea) 
			throws LineaMalFormadaException;

}
