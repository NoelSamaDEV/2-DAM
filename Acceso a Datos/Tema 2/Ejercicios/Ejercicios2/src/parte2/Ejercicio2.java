package parte2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio2 {

	public static void main(String[] args) {
		
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader (new FileReader(Ejercicio1.RESTAURANTES));
			try {
				imprimeCodigoPostal6(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (buffer!=null) {
				try {
					buffer.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private static void imprimeCodigoPostal6(BufferedReader buffer) 
			throws IOException {
		// similar al anterior
		String linea = buffer.readLine();// cabeceras
		linea = buffer.readLine();// primera linea
		while ( linea!=null) {
			// trato la linea
			// informacion separada por comas: rompo por comas
			String[] trozos = linea.split(",");
			// saco lo primero y lo ultimo
			String restaurante = trozos[0];
			String zipcode = trozos[trozos.length-1];
			// miro si el codigo postal empieza por 6
			if(zipcode.charAt(0)=='6') {
				System.out.println(restaurante+" "+zipcode);
			}
			linea = buffer.readLine();
		}
		
		
		
	}

}
