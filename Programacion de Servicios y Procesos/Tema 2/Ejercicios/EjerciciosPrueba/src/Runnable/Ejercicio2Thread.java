package Runnable;

// Crear un programa en Java que ejecute un hilo que imprima caracteres de la A a la E. Realizar con Thread
public class Ejercicio2Thread {

    // Clase interna ESTÁTICA que extiende Thread
    static class MiHilo extends Thread {
        private final String nombre;

        public MiHilo(String nombre) {
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
                    // restablece el estado de interrupción:
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println(nombre + " ha terminado.");
        }
    }

    // Punto de entrada
    public static void main(String[] args) {
        MiHilo hilo1 = new MiHilo("Hilo 1");
        hilo1.start();
    }
}

