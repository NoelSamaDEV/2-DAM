// Clase que extiende Thread
package Runnable;

class MiHilo extends Thread {
    private String nombre;

    public MiHilo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " está en ejecución, iteración: " + i);
            try {
                Thread.sleep(500); // Pausa de 500ms para simular un trabajo
            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido.");
            }
        }
        System.out.println(nombre + " ha terminado.");
    }
}

// Clase principal
public class HiloConThread {
    public static void main(String[] args) {
        MiHilo hilo1 = new MiHilo("Hilo 1");
        MiHilo hilo2 = new MiHilo("Hilo 2");

        hilo1.start(); // Inicia el hilo
        hilo2.start();
    }
}