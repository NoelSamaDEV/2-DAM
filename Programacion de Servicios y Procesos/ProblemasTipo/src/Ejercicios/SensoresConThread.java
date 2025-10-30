// Archivo: SensoresConThread.java
package Ejercicios;

public class SensoresConThread {
	// Clase que representa un sensor y extiende Thread para ejecutar su trabajo en
	// un hilo propio
	static class Sensor extends Thread {
		private final String nombreSensor;
		private final int lecturas; // número de lecturas a realizar

		public Sensor(String nombreSensor, int lecturas) {
			this.nombreSensor = nombreSensor;
			this.lecturas = lecturas;
		}

		@Override
		public void run() {
			// Este código se ejecuta cuando se llama start() en una instancia de Sensor
			for (int i = 1; i <= lecturas; i++) {
				// Simulamos lectura (aleatoria) y mostramos por consola el hilo actual
				double valor = 20 + Math.random() * 10; // temperatura simulada 20-30
				System.out.printf("Hilo %s - %s: lectura %d = %.2f ºC%n", Thread.currentThread().getName(),
						nombreSensor, i, valor);
				try {
					// Pausa para simular tiempo entre lecturas
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
		// Creamos tres sensores y los arrancamos
		Sensor s1 = new Sensor("Sensor-A", 5);
		Sensor s2 = new Sensor("Sensor-B", 5);
		Sensor s3 = new Sensor("Sensor-C", 5);
		// Cambiamos nombre de los hilos para identificación en consola
		s1.setName("Hilo-Sensor-A");
		s2.setName("Hilo-Sensor-B");
		s3.setName("Hilo-Sensor-C");
		System.out.println("Iniciando sensores...");
		s1.start(); // ejecuta run() en nuevo hilo
		s2.start();
		s3.start();
		// Esperamos a que terminen para finalizar el programa principal
		try {
			s1.join();
			s2.join();
			s3.join();
		} catch (InterruptedException e) {
			System.out.println("Main interrumpido mientras esperaba a sensores.");
		}
		System.out.println("Todos los sensores han terminado. Programa finalizado.");
	}
}