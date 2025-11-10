package synchroThread;

class Contador {
    private int cuenta = 0;

    public synchronized void incrementar() {
        cuenta++;
        System.out.println("Cuenta actual: " + cuenta);
    }
}

class HiloContador extends Thread {
    private Contador contador;

    public HiloContador(Contador contador) {
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            contador.incrementar();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido.");
            }
        }
    }
}

public class synchroThread {
    public static void main(String[] args) {
        Contador contador = new Contador();

        HiloContador hilo1 = new HiloContador(contador);
        HiloContador hilo2 = new HiloContador(contador);
        HiloContador hilo3 = new HiloContador(contador);
        
        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
}