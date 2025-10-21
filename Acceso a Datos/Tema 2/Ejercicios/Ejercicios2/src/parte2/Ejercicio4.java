package parte2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.File;

public class Ejercicio4 {
	
	public static void main(String[] args) {
		
		String input = Ejercicio1.RESTAURANTES.getPath();
		String output = "restaurantes2.csv";
		//saco del fichero una lista de restaurantes
		try {
			List<Restaurante> original = leeFichero(input);
			
			//FORMA 1
			// necesito aquellos cuyo zipcode no empiza por 6
			/*
			List<Restaurante> copia = new ArrayList<>();
			//me quedo con los buenos del original
			for(Restaurante r: original) {
				if(r.getZipCode()/10000 != 6) {
					copia.add(r);
				}
			}*/
			
			//FORMA 2
			//copio fuerte del original
			List<Restaurante> copia = new LinkedList<>(original);
			//saco iterador
			Iterator<Restaurante> iterator = copia.iterator();
			//uso el iterador mientras haya elementos
			while(iterator.hasNext()) {
				//saco el siguiente
				Restaurante r = iterator.next();
				if(r.getZipCode()/10000 == 6) {//no me gusta
					iterator.remove();//borro
					
					
				}
			}
			//vuelco fichero
			escribeFichero(output, copia);
			
		} catch (LeeFicheroException e) {
			e.printStackTrace();
		} catch (EscribeFicheroException e) {
			e.printStackTrace();
		}
		
	
		
	}

	
	/** Este metodo recibe un nombre de fichero similar a restaurantes.csv
	 * y devuelve una lista de objetos
	 * Todas las posibles excepciones se lanzan como 
	 * LeeFicheroException
	 * @throws LeeFicheroException 
	 */
	public static List<Restaurante> leeFichero(String nombre) throws LeeFicheroException{
		List<Restaurante> lista = new ArrayList<>();
	    BufferedReader buffer = null;
		
		try {
			buffer= new BufferedReader(
					new FileReader(
							new File(nombre))
					);
			// leo linea a linea y parseo
			String linea = buffer.readLine();
			linea = buffer.readLine(); // cabeceras
			while(linea!=null) {
				// linea a objeto
				Restaurante r = Restaurante.parse(linea);
				lista.add(r);
				// leo la siguiente 
				linea = buffer.readLine();
			}
			
		} catch (FileNotFoundException e) {
			throw new LeeFicheroException(e.getMessage());
		} catch (IOException e) {
			throw new LeeFicheroException(e.getMessage());
		}
		finally {
			if(buffer!=null) {
				try {
					buffer.close();
				} catch (Exception e) {
					throw new LeeFicheroException(e.getMessage());
				}
			}
		}
		return lista;
	}
	
	/** Dado un nombre de fichero y un coleccion de restaurantes
	 * los escribe fila a fila. Todas las excepciones se 
	 * propagan como EscribeFicheroException
	 * @throws LeeFicheroException 
	 * @throws EscribeFicheroException 
	 */
	public static void escribeFichero(String nombre,
			Collection<Restaurante> restaurantes) throws EscribeFicheroException {
		BufferedWriter bw =null;
		
		try {
			bw =new BufferedWriter(
				   new FileWriter(
					    new File(nombre)
						)
							);
			//cabeceras
			bw.write("restaurant,Address,City,State,Zipcode\n");
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