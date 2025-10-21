package parte2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Ejercicio3 {
	
	/**
	 * Partiendo del fichero de restaurantes.csv, crear un programa de Java que permita
	 *  al usuario añadir datos nuevos a ese fichero, utilizando el mismo formato que los
	 *   ya existentes.
	 */
	public static void main(String[] args) {
		
		/*
		 * A partir del ejercicio 4
		 * -Hago un objeto restaurante (o varios)
		 * -Los meto en una coleccion (list,set...)
		 * -Llamo a escribeFichero
		 */
		
		Restaurante r1 = new Restaurante("La flotilla ambulante", "Grecia", "Mikonos", "de parranda", 12345);
		
		//Hago una lista inmutable
		Collection<Restaurante> c = List.of(r1);
		//escribeFichero
		try {
			añadeFichero(Ejercicio1.RESTAURANTES.getPath(), c);
		} catch (EscribeFicheroException e) {
			e.printStackTrace();
		}
	}
	
	/** Dado un nombre de fichero y un coleccion de restaurantes
	 * los escribe fila a fila. Todas las excepciones se 
	 * propagan como EscribeFicheroException
	 * @throws LeeFicheroException 
	 * @throws EscribeFicheroException 
	 */
	public static void añadeFichero(String nombre,
			Collection<Restaurante> restaurantes) throws EscribeFicheroException {
		BufferedWriter bw =null;
		
		try {
			boolean append = true;
			bw =new BufferedWriter(
				   new FileWriter(
					    new File(nombre), append
					    
						)
							);
			// sin cabeceras
			for(Restaurante r: restaurantes) {
				bw.write(r.aFichero()+"\n");
			}
		} catch (IOException e) {
			throw new EscribeFicheroException(e.getMessage());
		}
		finally {
			if(bw!=null) {
				try {
					bw.close();
				} catch (IOException e) {
					throw new EscribeFicheroException(e.getMessage());
				}
			}
		}
	}
}
