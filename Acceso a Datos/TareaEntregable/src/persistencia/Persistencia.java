package persistencia;

import java.util.List;

import excepciones.LineaMalFormadaException;

public interface Persistencia {
	
	/**Necesita el nombre del fichero de origen de los datos y 
	 * una lista donde dejar cada uno de los registros.
	 * Modifica el contenido de la lista y la "retorna" 
	 * con los registros introducidos a partir del fichero de datos
	 * @param <T> un tipo que sea documentable
	 * @param nombreFichero nombre del fichero a leer
	 * @param constructor constructor de objetos del tipo T
	 * @return una lista de objetos del tipo indicado*/
	<T extends Documentable> List<T> leerDocumentables(
			String nombreFichero,
			Constructor<String, T, LineaMalFormadaException> constructor);
	
	/**Necesita el nombre del fichero destino donde dejar 
	 * la información de los coches que recibe a través de la 
	 * listacoches. Crea un fichero con los nuevos datos
	 * @param <T> un tipo que sea documentable
	 * @param nombreFichero nombre del fichero a escribir
	 * @param lista la lista de objetos del tipo T*/
	<T extends Documentable> void escribirDocumentables(
			String nombreFichero, 
			List<T> lista, String cabecera);
	
	/**Parsea una línea del documentable a partir del constructor
	 * @param <T> un tipo que sea documentable
	 * @param linea la línea a leer y a convertir en objeto
	 * @param constructor el constructor de objetos de tipo T
	 * @return el objeto creado o null si hubo un error*/
	default <T extends Documentable> T parseaLíneaDocumentable(
			String linea, 
			Constructor<String, T, LineaMalFormadaException> constructor) {
		T objeto = null;
		try {
			objeto = constructor.apply(linea);
		} 
		catch (LineaMalFormadaException e) {
			e.printStackTrace();
		}
		return objeto;
	}
	
}
