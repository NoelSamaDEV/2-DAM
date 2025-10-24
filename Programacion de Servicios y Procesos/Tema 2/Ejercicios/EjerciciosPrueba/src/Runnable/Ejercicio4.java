package Runnable;

import java.util.Scanner;

// Crear un programa en Java que ejecute varios hilos que impriman nombres diferentes.
// 1. La clase extiende Thread y recibe un nombre en su constructor.
// 2. Cada hilo imprime su nombre 5 veces con una pausa de 1 segundo entre cada impresión.
// 3. Los nombres se pasan por consola.

public class Ejercicio4 {

    // Clase que extiende Thread
    static class MiHilo extends Thread {
        private final String nombre;

        public MiHilo(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(nombre + " - iteración " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(nombre + " fue interrumpido.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println(nombre + " ha terminado.");
        }
    }

    // Clase principal
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("¿Cuántos hilos quieres crear?: ");
        int cantidad = sc.nextInt();
        sc.nextLine(); // limpiar el buffer

        Thread[] hilos = new Thread[cantidad];

        // Pedir los nombres por consola
        for (int i = 0; i < cantidad; i++) {
            System.out.print("Introduce el nombre del hilo " + (i + 1) + ": ");
            String nombre = sc.nextLine();
            hilos[i] = new MiHilo(nombre);
        }

        // Iniciar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }

        sc.close();
    }
}
