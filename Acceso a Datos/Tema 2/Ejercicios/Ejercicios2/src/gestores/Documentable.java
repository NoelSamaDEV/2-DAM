package gestores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import parte2.EscribeFicheroException;

public interface Documentable {
	
	/**Recibe una línea del fichero y devuelve un objeto
	 * del tipo*/
	static Documentable parse(String linea) {
		throw new UnsupportedOperationException("¡Impleméntame!"); 
	}
	
	/**Cabeceras del fichero*/
	static String cabeceras() {
		throw new UnsupportedOperationException("¡Impleméntame!"); 
	}
	
	/**Devuelve la línea que iría al fichero*/
	abstract String aFichero();

	/**Dado un nombre de fichero y un colección de documentables,
	 * los escribe fila a fila. Todas las excepciones se
	 * propagan como EscribeFicheroException 
	 * @throws LeeFicheroException */
	public static void escribeFichero(String nombre, Collection<Documentable> documentables, String cabeceras) 
					throws EscribeFicheroException {
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(
					new FileWriter(
							new File(nombre)
							)
					);
			//cabeceras!!!
			bw.write(cabeceras());
			// lo de verdad
			for(Documentable d: documentables) {
				bw.write(d.aFichero()+"\n");
			}
		} 
		catch (IOException e) {
			throw new EscribeFicheroException(e.getMessage());
		}
		finally {
			if (bw!=null) {
				try {
					bw.close();
				} 
				catch (IOException e) {
					throw new EscribeFicheroException(e.getMessage());
				}
			}
		}
	}
	
	
	

}

