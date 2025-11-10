package persistencia;

import excepciones.LineaMalFormadaException;

/**Implementación parcial de Documentable, con dos constructores 
 * disponibles que recibe una línea de un fichero y la parsea*/
public abstract class AbstractDocumentable implements Documentable {

	/**Constructor de la clase
	 * @param línea la línea leída del fichero
	 * @throws LineaMalFormadaException*/
	public AbstractDocumentable(String línea) 
			throws LineaMalFormadaException {
		parseLínea(línea);
	}

	/**Constructor copia. Se entiende que los objetos siempre
	 * serán "correctos" (no darán excepciones)
	 * @param ad otro objeto de tipo {@link AbstractDocumentable}*/
	public AbstractDocumentable(AbstractDocumentable ad) {
		try {
			parseLínea(ad.marshalling());
		} 
		catch (LineaMalFormadaException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	/**Constructor creado literalmente para no poder usar un 
	 * constructor por defecto en ninguna clase que herede de
	 * esta*/
	private AbstractDocumentable() {}
	
}
