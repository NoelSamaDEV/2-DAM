package Logica;

import java.io.File;

/**
 * Pide una cadena de texto y, a partir de una ruta, revisa todo lo que encuentra:
 * -Las carpetas buesca en ella otras carpetas o .java
 * -Los .java los lee enteros buscando cadenas de texto
 * Cada vez que encuentra la cadena en un .java se muestra
 * por pantalla la ruta completa del archivo y el numero de lnea donde se encuentra y la fila en si
 */
public class Main {

	public static final File raiz = new File("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM");
	
	public static void main(String[] args) {
		
		analizaCarpeta(raiz);
	}

	/**
	 * Revisa una carpeta y:
	 * -Las carpetas: se vuelven a llamar a si mismas (recusivo)
	 * -Los .java: llama a analizaJava
	 */
	private static void analizaCarpeta(File raiz2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * -Los .java los lee enteros buscando cadenas de texto
	 * Cada vez que encuentra la cadena en un .java se muestra
	 * por pantalla la ruta completa del archivo y el numero de lnea donde se encuentra y la fila en si
	 */
	private static void analizaJava(File fichero, String cadena) {

	}
}
