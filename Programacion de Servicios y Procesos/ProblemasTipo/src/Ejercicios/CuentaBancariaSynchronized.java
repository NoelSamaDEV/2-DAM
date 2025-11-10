package Ejercicios;

//Archivo: CuentaBancariaSynchronized.java
public class CuentaBancariaSynchronized {
static class Cuenta {
private int saldo;
public Cuenta(int saldoInicial) {
this.saldo = saldoInicial;
}
// Método sincronizado: sólo un hilo puede ejecutarlo a la vez por instancia
public synchronized void depositar(int cantidad) {
int nuevo = saldo + cantidad;
// simulamos retraso que podría dar lugar a condiciones de carrera sin sincronización
try { Thread.sleep(50); } catch (InterruptedException ignored) {}
saldo = nuevo;
System.out.printf("Hilo %s: depositado %d, saldo = %d%n",
Thread.currentThread().getName(), cantidad, saldo);
}
// Método sincronizado: protege la operación completa de extracción
public synchronized boolean retirar(int cantidad) {
System.out.printf("Hilo %s: intentando retirar %d (saldo actual %d)%n",
Thread.currentThread().getName(), cantidad, saldo);
if (saldo >= cantidad) {
try { Thread.sleep(50); } catch (InterruptedException ignored) {}
saldo -= cantidad;
System.out.printf("Hilo %s: retirada ok %d, saldo = %d%n",
Thread.currentThread().getName(), cantidad, saldo);
return true;
} else {
System.out.printf("Hilo %s: fondos insuficientes para %d%n",
Thread.currentThread().getName(), cantidad);
return false;
}
}
public synchronized int getSaldo() {
return saldo;
}
}
public static void main(String[] args) {
Cuenta cuenta = new Cuenta(100);
// Creamos varias tareas que intentan retirar y depositar
Thread t1 = new Thread(() -> {
cuenta.retirar(70);
}, "Cliente-1");
Thread t2 = new Thread(() -> {
cuenta.retirar(50);
}, "Cliente-2");
Thread t3 = new Thread(() -> {
cuenta.depositar(40);
}, "Depósito-1");
// Arrancamos los hilos casi simultáneamente
t1.start();
t2.start();
t3.start();
try {
t1.join(); t2.join(); t3.join();
} catch (InterruptedException e) {
System.out.println("Main interrumpido.");
}
System.out.println("Saldo final: " + cuenta.getSaldo());
}
}