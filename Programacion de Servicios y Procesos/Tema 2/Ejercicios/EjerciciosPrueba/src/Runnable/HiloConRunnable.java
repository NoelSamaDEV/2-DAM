package Runnable;

// Clase que implementa Runnable
class Tarea implements Runnable {
    private String nombre;

    public Tarea(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre + " está ejecutando la tarea, iteración: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido.");
            }
        }
        System.out.println(nombre + " ha terminado la tarea.");
    }
}

// Clase principal
public class HiloConRunnable {
    public static void main(String[] args) {
        Thread hilo1 = new Thread(new Tarea("Hilo 1"));
        Thread hilo2 = new Thread(new Tarea("Hilo 2"));
        Thread hilo3 = new Thread(new Tarea("Hilo 3"));

        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
}