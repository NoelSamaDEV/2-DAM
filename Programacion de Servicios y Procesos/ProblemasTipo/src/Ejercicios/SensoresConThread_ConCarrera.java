package Ejercicios;

//Archivo: SensoresConThread_ConCarrera.java
public class SensoresConThread_ConCarrera {

 // Contador global sin protección (mostrará condición de carrera)
 static int contadorTotalLecturas = 0;

 static class Sensor extends Thread {
     private final String nombreSensor;
     private final int lecturas; // número de lecturas a realizar

     public Sensor(String nombreSensor, int lecturas) {
         this.nombreSensor = nombreSensor;
         this.lecturas = lecturas;
     }

     @Override
     public void run() {
         for (int i = 1; i <= lecturas; i++) {
             double valor = 20 + Math.random() * 10; // temperatura simulada 20-30
             System.out.printf("Hilo %s - %s: lectura %d = %.2f ºC%n",
                     Thread.currentThread().getName(), nombreSensor, i, valor);

             // Incremento del contador global SIN protección -> condición de carrera posible
             contadorTotalLecturas = contadorTotalLecturas + 1;

             try {
                 Thread.sleep(500);
             } catch (InterruptedException e) {
                 System.out.printf("Hilo %s interrumpido.%n", Thread.currentThread().getName());
                 return;
             }
         }
         System.out.printf("Hilo %s - %s: terminado.%n", Thread.currentThread().getName(), nombreSensor);
     }
 }

 public static void main(String[] args) {
     Sensor s1 = new Sensor("Sensor-A", 5);
     Sensor s2 = new Sensor("Sensor-B", 5);
     Sensor s3 = new Sensor("Sensor-C", 5);

     s1.setName("Hilo-Sensor-A");
     s2.setName("Hilo-Sensor-B");
     s3.setName("Hilo-Sensor-C");

     System.out.println("Iniciando sensores (versión CON condición de carrera)...");
     s1.start();
     s2.start();
     s3.start();

     try {
         s1.join();
         s2.join();
         s3.join();
     } catch (InterruptedException e) {
         System.out.println("Main interrumpido mientras esperaba a sensores.");
     }

     // Resultado esperado = 5 + 5 + 5 = 15. Con condición de carrera suele ser < 15.
     System.out.println("Lecturas totales registradas (sin protección): " + contadorTotalLecturas);
     System.out.println("Programa finalizado.");
 }
}

