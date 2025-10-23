package Logica;

import java.io.*;
import java.util.Scanner;

/**
 * Pide una cadena de texto y, a partir de una ruta, revisa todo lo que encuentra:
 * - Las carpetas busca en ella otras carpetas o .java
 * - Los .java los lee enteros buscando cadenas de texto
 * Cada vez que encuentra la cadena en un .java se muestra
 * por pantalla la ruta completa del archivo y el número de línea donde se encuentra y la fila en sí
 */
public class Main {

    public static final File raiz = new File("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM\\2-DAM");
    public static Contador contador = new Contador();

    public static void main(String[] args) {
        String cadena = null;
        if (args.length > 0) {
            cadena = args[0];
        } else {
            System.out.println("Introduce la palabra o frase a buscar:");
            Scanner sc = new Scanner(System.in);
            cadena = sc.nextLine();
            sc.close();
        }
        System.out.println("Buscando la cadena: " + cadena);
        analizaCarpeta(raiz, cadena);
        System.out.println(contador);
    }

    /**
     * Revisa una carpeta y:
     * - Las carpetas: se vuelven a llamar a sí mismas (recursivo)
     * - Los .java: llama a analizaJava
     */
    private static void analizaCarpeta(File raiz, String cadena) {
        contador.carpetaVisitada();

        for (String nombreHijo : raiz.list()) {
            File hijo = new File(raiz, nombreHijo);
            if (hijo.isDirectory()) {
                analizaCarpeta(hijo, cadena);
            } else if (hijo.isFile() && nombreHijo.endsWith(".java")) {
                analizaJava(hijo, cadena);
            }
        }
    }

    /**
     * Lee los .java buscando la cadena
     * Muestra ruta completa, número de línea y el contenido
     */
    private static void analizaJava(File java, String cadena) {
        contador.archivoVisitado();

        boolean huboCoincidenciaEnArchivo = false;
        int numLinea = 0;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(java));
            String fila;
            while ((fila = br.readLine()) != null) {
                numLinea++;
                Main.contador.lineaLeida();

                if (fila.toLowerCase().contains(cadena.toLowerCase())) {
                    if (!huboCoincidenciaEnArchivo) {
                        Main.contador.archivoConCoincidencia();
                        huboCoincidenciaEnArchivo = true;
                    }
                    System.out.printf("-> %s, línea %d: %s%n",
                            java.getAbsolutePath(), numLinea, fila);
                    Main.contador.coincidencia();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer: "+ e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Contador {
        private int carpetasVisitadas;
        private int archivosVisitados;
        private int lineasLeidas;
        private int coincidencias;
        private int archivosCoincidencias;

        public void carpetaVisitada() {
        	carpetasVisitadas++;
        }
        public void archivoVisitado() {
        	archivosVisitados++;
        }
        public void lineaLeida() {
        	lineasLeidas++;
        }
        public void coincidencia() {
        	coincidencias++;
        }
        public void archivoConCoincidencia() {
        	archivosCoincidencias++;
        }

        public String toString() {
            return String.format(
                    "Carpetas visitadas: %d%nArchivos visitados: %d%nLíneas leídas: %d%n" +
                    "Coincidencias encontradas: %d%nArchivos con coincidencias: %d",
                    carpetasVisitadas, archivosVisitados, lineasLeidas, coincidencias, archivosCoincidencias
            );
        }
    }
}

