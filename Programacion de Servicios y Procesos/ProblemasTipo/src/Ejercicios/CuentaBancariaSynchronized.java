package Ejercicios;

// Archivo: CuentaBancariaSynchronized.java
public class CuentaBancariaSynchronized {
    
    // Esta es la clase del recurso que van a compartir los hilos (la cuenta)
    static class Cuenta {
        private int saldo;

        public Cuenta(int saldoInicial) {
            this.saldo = saldoInicial;
        }

        // COMENTARIO IMPORTANTE:
        // Uso 'synchronized' para que solo pueda entrar un hilo a la vez.
        // Si uno entra, los demás se quedan fuera esperando su turno (bloqueados).
        public synchronized void depositar(int cantidad) {
            int nuevo = saldo + cantidad;
            
            // Hago una pausa de 50ms para simular que la operación tarda un poco
            try {
                Thread.sleep(50); 
            } catch (InterruptedException ignored) {
            }
            
            // Actualizo el saldo. Al ser synchronized, estoy seguro de que nadie más lo ha tocado mientras dormía.
            saldo = nuevo;
            System.out.printf("Hilo %s: depositado %d, saldo = %d%n", Thread.currentThread().getName(), cantidad,
                    saldo);
        } 

        // Este método también es sincronizado para proteger la retirada de dinero.
        // Evita que dos hilos entren a la vez y saquen más dinero del que hay.
        public synchronized boolean retirar(int cantidad) {
            System.out.printf("Hilo %s: intentando retirar %d (saldo actual %d)%n", Thread.currentThread().getName(),
                    cantidad, saldo);
            
            // Compruebo si hay dinero suficiente
            if (saldo >= cantidad) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
                // Resto la cantidad. Es seguro porque estoy "solo" dentro del método gracias al synchronized.
                saldo -= cantidad;
                System.out.printf("Hilo %s: retirada ok %d, saldo = %d%n", Thread.currentThread().getName(), cantidad,
                        saldo);
                return true;
            } else {
                System.out.printf("Hilo %s: fondos insuficientes para %d%n", Thread.currentThread().getName(),
                        cantidad);
                return false;
            }
        }

        public synchronized int getSaldo() {
            return saldo;
        }
    }

    public static void main(String[] args) {
        // Creo el objeto compartido. Solo hay una cuenta para todos.
        Cuenta cuenta = new Cuenta(100);

        // -- CREACIÓN DE HILOS --
        // Creo el Hilo 1 (Cliente-1) usando una lambda. Lo configuro para que retire 70.
        Thread t1 = new Thread(() -> {
            cuenta.retirar(70);
        }, "Cliente-1");
        
        // Creo el Hilo 2 (Cliente-2) para que retire 50.
        Thread t2 = new Thread(() -> {
            cuenta.retirar(50);
        }, "Cliente-2");
        
        // Creo el Hilo 3 (Depósito-1) para que ingrese 40.
        Thread t3 = new Thread(() -> {
            cuenta.depositar(40);
        }, "Depósito-1");

        // -- ARRANQUE --
        // Llamo a start() para que los hilos empiecen a ejecutarse de verdad.
        // Aquí pasan a estado "Ejecutable" (o Runnable).
        t1.start();
        t2.start();
        t3.start();

        // -- ESPERA (JOIN) --
        // El hilo principal (main) se espera aquí.
        // No va a seguir hasta que t1, t2 y t3 hayan terminado su trabajo.
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido.");
        }
        
        // Imprimo el saldo final. Gracias a los join(), sé que todas las operaciones ya acabaron.
        System.out.println("Saldo final: " + cuenta.getSaldo());
    }
}