package parte2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1 {
	
	public static final File RAIZ = new File("src", "parte2");
	public static final File RESTAURANTES = new File(RAIZ, "restaurantes.csv");

	public static void main(String[] args) {
	
		
		File fichero = RESTAURANTES;
		//System.out.println(fichero.exists());
		FileReader reader = null;
		BufferedReader  buffer = null;
		try {
			reader = new FileReader(fichero);
			buffer = new BufferedReader(reader);
			imprimeFicher(buffer);
		} 
		catch (FileNotFoundException e) {
			System.out.println("Cagaste");
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(buffer!=null) {
				try {
					buffer.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(reader!=null) {
				try {
					reader.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void imprimeFicher(BufferedReader buffer) throws IOException {
		// imprimir con número de fila
		int contador = 0;
		// leo la primera linea si hay
		String linea = buffer.readLine();
		while (linea!=null){
			//imprimo su contador
			System.out.printf("%d %s \n", contador, linea);
			// avanzo contador
			contador ++;
			// leo siguiente linea
			linea = buffer.readLine();
		}

	}
}

