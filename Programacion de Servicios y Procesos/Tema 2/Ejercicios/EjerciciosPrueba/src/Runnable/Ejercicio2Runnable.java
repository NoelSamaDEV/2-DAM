package Runnable;

public class Ejercicio2Runnable {

    // Clase interna que implementa Runnable
    static class Tarea implements Runnable {
        private final String nombre;

        public Tarea(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            for (char c = 'A'; c <= 'E'; c++) {
                System.out.println(nombre + " imprime: " + c);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(nombre + " fue interrumpido.");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(nombre + " ha terminado la tarea.");
        }
    }

    // main
    public static void main(String[] args) {
        Thread hilo1 = new Thread(new Tarea("Hilo 1"));
        hilo1.start();
    }
}
