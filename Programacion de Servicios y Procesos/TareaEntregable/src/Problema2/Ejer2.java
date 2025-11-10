package Problema2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Ejer2 {

    // Contador global sincronizado (seguro frente a concurrencia)
    private static final AtomicInteger totalErrores = new AtomicInteger(0);

    // Clase que implementa Runnable
    static class ProcesadorArchivo implements Runnable {
        private final String nombreArchivo;

        // Constructor que recibe el nombre del archivo
        public ProcesadorArchivo(String nombreArchivo) {
            this.nombreArchivo = nombreArchivo;
        }

        @Override
        public void run() {
            int ContErrores = 0;
            System.out.printf("[%s] Procesando archivo: %s%n",
                    Thread.currentThread().getName(), nombreArchivo);

            // Lectura del archivo línea a línea
            try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.contains("ERROR")) {
                    	ContErrores++;
                    }
                }
            } catch (IOException e) {
                System.out.printf("[%s] No se pudo leer %s: %s%n",
                        Thread.currentThread().getName(), nombreArchivo, e.getMessage());
            }

            // Sumar al contador global de manera segura
            int nuevoTotal = totalErrores.addAndGet(ContErrores);

            System.out.printf("[%s] %s -> %d líneas con ERROR (total global: %d)%n",
                    Thread.currentThread().getName(), nombreArchivo, ContErrores, nuevoTotal);
        }
    }

    public static void main(String[] args) {
        // Lista de archivos simulados (pueden existir o no)
        String[] archivos = {
                "logs/app1.log",
                "logs/app2.log",
                "logs/app3.log"
        };

        List<Thread> hilos = new ArrayList<>();

        System.out.println("Iniciando procesamiento concurrente de archivos...");

        long inicio = System.currentTimeMillis();

        // Crear y lanzar los hilos
        for (int i = 0; i < archivos.length; i++) {
            Thread t = new Thread(new ProcesadorArchivo(archivos[i]), "Hilo-" + (i + 1));
            hilos.add(t);
            t.start();
        }

        // Esperar a que todos los hilos terminen
        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.printf("[Main] Interrumpido esperando a %s%n", t.getName());
                Thread.currentThread().interrupt();
            }
        }

        long fin = System.currentTimeMillis();

        System.out.println("\n=== RESULTADO FINAL ===");
        System.out.printf("Total global de líneas con 'ERROR': %d%n", totalErrores.get());
        System.out.printf("Tiempo total de ejecución: %d ms%n", (fin - inicio));
    }
}
