package Problema1;

import java.util.concurrent.atomic.AtomicInteger;

public class Ejer1 {

    // Valores compartidos de los 3 sensores
    static AtomicInteger valorS1 = new AtomicInteger(0);
    static AtomicInteger valorS2 = new AtomicInteger(0);
    static AtomicInteger valorS3 = new AtomicInteger(0);

    // Variable de control para detener los hilos
    static volatile boolean activo = true;

    // Clase Sensor que extiende Thread
    static class Sensor extends Thread {
        private final String nombre;
        private final AtomicInteger valorCompartido;

        public Sensor(String nombre, AtomicInteger valorCompartido) {
            this.nombre = nombre;
            this.valorCompartido = valorCompartido;
        }

        @Override
        public void run() {
            while (activo) {
                int valor = 20 + (int) (Math.random() * 81); // temperatura entre 20-100
                valorCompartido.set(valor); 
                System.out.printf("%s lee %.1f ºC%n", nombre, (double) valor);
                try {
                    Thread.sleep(500); // lectura cada 0.5 s
                } catch (InterruptedException e) {
                    System.out.println(nombre + " interrumpido.");
                    break;
                }
            }
            System.out.println(nombre + " finalizado.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Iniciando monitorización de sensores...");

        // Crear los tres sensores
        Sensor s1 = new Sensor("Sensor 1", valorS1);
        Sensor s2 = new Sensor("Sensor 2", valorS2);
        Sensor s3 = new Sensor("Sensor 3", valorS3);

        // Iniciar los hilos
        s1.start();
        s2.start();
        s3.start();

        long inicio = System.currentTimeMillis();

        // Mostrar promedio cada 2 segundos durante 10 segundos
        while (System.currentTimeMillis() - inicio < 10000) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int v1 = valorS1.get();
            int v2 = valorS2.get();
            int v3 = valorS3.get();
            double media = (v1 + v2 + v3) / 3.0;

            System.out.printf("Promedio actual: %.2f ºC %n", media);
        }

        // Finalizar hilos
        activo = false;

        try {
            s1.join();
            s2.join();
            s3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Monitorización finalizada");
    }
}
