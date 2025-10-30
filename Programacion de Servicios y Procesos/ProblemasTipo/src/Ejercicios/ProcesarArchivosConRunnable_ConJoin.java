package Ejercicios;

//Archivo: ProcesarArchivosConRunnable_ConJoin.java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcesarArchivosConRunnable_ConJoin {

 // Implementamos Runnable para separar la tarea de la ejecución
 static class ProcesadorArchivo implements Runnable {
     private final File archivo;

     public ProcesadorArchivo(File archivo) {
         this.archivo = archivo;
     }

     @Override
     public void run() {
         System.out.printf("Hilo %s: empezando a procesar %s%n",
                 Thread.currentThread().getName(), archivo.getName());
         int lineas = 0;
         // Simulación: contamos líneas (si existiera archivo real)
         if (!archivo.exists()) {
             System.out.printf("Hilo %s: archivo %s no encontrado, simulando 100 líneas.%n",
                     Thread.currentThread().getName(), archivo.getName());
             lineas = 100; // simulación
         } else {
             try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                 while (br.readLine() != null) lineas++;
             } catch (IOException e) {
                 System.out.printf("Hilo %s: error leyendo %s: %s%n",
                         Thread.currentThread().getName(), archivo.getName(), e.getMessage());
             }
         }
         System.out.printf("Hilo %s: %s -> %d líneas (procesado)%n",
                 Thread.currentThread().getName(), archivo.getName(), lineas);
     }
 }

 public static void main(String[] args) {
     // Simulamos una lista de archivos (no es necesario que existan)
     File[] archivos = {
             new File("logs/app1.log"),
             new File("logs/app2.log"),
             new File("logs/app3.log")
     };

     // Guardamos referencias a los threads en una lista para poder hacer join más tarde
     List<Thread> listaHilos = new ArrayList<>();

     System.out.println("Main: lanzando tareas de procesamiento de archivos...");

     // Tomamos el tiempo de inicio (en milisegundos)
     long inicio = System.currentTimeMillis();

     // Para cada archivo creamos un Runnable y lo ejecutamos en un Thread
     for (int i = 0; i < archivos.length; i++) {
         Thread t = new Thread(new ProcesadorArchivo(archivos[i]), "Hilo-Proc-" + (i + 1));
         listaHilos.add(t); // guardamos la referencia
         t.start();
     }

     // Esperamos a que terminen todos los hilos mediante join()
     for (Thread t : listaHilos) {
         try {
             t.join(); // espera a que el hilo t termine
         } catch (InterruptedException e) {
             System.out.printf("Main: interrumpido mientras esperaba a %s%n", t.getName());
             // Restaurar el estado de interrupción por si alguien más lo maneja
             Thread.currentThread().interrupt();
         }
     }

     // Medimos tiempo total transcurrido
     long fin = System.currentTimeMillis();
     long tiempoTotalMs = fin - inicio;

     System.out.println("Main: todas las tareas de procesamiento han finalizado.");
     System.out.printf("Tiempo total transcurrido: %d ms%n", tiempoTotalMs);
 }
}
