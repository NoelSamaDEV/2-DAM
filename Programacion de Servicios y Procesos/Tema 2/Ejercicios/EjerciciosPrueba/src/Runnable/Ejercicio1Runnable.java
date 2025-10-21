package Runnable;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ejercicio1Runnable implements Runnable {
    private String nombre;
    private int duracion;

    public Ejercicio1Runnable(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " está ejecutando la tarea, iteración: " + i);
            try {
                Thread.sleep(duracion);
            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido.");
                return;
            }
        }
        System.out.println(nombre + " ha terminado la tarea.");
    }

    // Clase principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Crea un scheduler con 5 hilos
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

        for (int i = 1; i <= 5; i++) {
            System.out.print("Introduce el retraso inicial (en segundos) para el Hilo " + i + ": ");
            int retraso = sc.nextInt();

            System.out.print("Introduce la duración de pausa entre iteraciones (en milisegundos) para el Hilo " + i + ": ");
            int duracion = sc.nextInt();

            // Programamos cada hilo para que empiece después de su retraso
            scheduler.schedule(
                new Ejercicio1Runnable("Hilo " + i, duracion),
                retraso,
                TimeUnit.SECONDS
            );
        }

        // Cerramos el scheduler después de ejecutar todo
        scheduler.shutdown();
        sc.close();
    }
}

