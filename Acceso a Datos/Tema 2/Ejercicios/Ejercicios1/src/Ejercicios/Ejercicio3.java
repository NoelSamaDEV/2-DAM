package Ejercicios;

import java.io.File;

/*
 * Escribir un programa que muestre los archivos (SOLO LOS ARCHIVOS) de un directorio introducido por el usuario
 */
public class Ejercicio3 {

	public static void main(String[] args) {
		
		File f =new File(Ejercicio1.RUTA);
		for (String hijo : f.list()) {
			File aux = new File(Ejercicio1.RUTA, hijo);
			if(aux.isFile()) {
				System.out.println(aux);
			}
		}
		
	}

}
