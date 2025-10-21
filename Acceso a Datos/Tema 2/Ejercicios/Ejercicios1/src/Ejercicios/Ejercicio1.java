package Ejercicios;

import java.io.File;

/*
  * Escribir un programa en Java que para cualquier ruta indicada por el usuario, muestre:
• Si el fichero existe o no
• Si se trata de un directorio o de un fichero
  */
public class Ejercicio1 {
	public static final String RUTA= "C:\\Users\\noels\\OneDrive\\Desktop";
	
	public static void main(String[] args) {
		
		File f= new File (RUTA);
		
		System.out.println("¿Existe?");
		System.out.println(f.exists());
		
		//Directorio o Fichero
		System.out.println("Directorio?");
		System.out.println(f.isDirectory());
		System.out.println("Fichero?");
		System.out.println(f.isFile());
	}
}
