package Ejercicios;

//Archivo: ProcesarArchivosConRunnable.java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ProcesarArchivosConRunnable {
// Implementamos Runnable para separar la tarea de la ejecución
	static class ProcesadorArchivo implements Runnable {
		private final File archivo;

		public ProcesadorArchivo(File archivo) {
			this.archivo = archivo;
		}

		@Override
		public void run() {
			System.out.printf("Hilo %s: empezando a procesar %s%n", Thread.currentThread().getName(),
					archivo.getName());
			int lineas = 0;
// Simulación: contamos líneas (si existiera archivo real)
			if (!archivo.exists()) {
				System.out.printf("Hilo %s: archivo %s no encontrado, simulando 100 líneas.%n",
						Thread.currentThread().getName(), archivo.getName());
				lineas = 100; // simulación
			} else {
				try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
					while (br.readLine() != null)
						lineas++;
				} catch (IOException e) {
					System.out.printf("Hilo %s: error leyendo %s: %s%n", Thread.currentThread().getName(),
							archivo.getName(), e.getMessage());
				}
			}
			System.out.printf("Hilo %s: %s -> %d líneas (procesado)%n", Thread.currentThread().getName(),
					archivo.getName(), lineas);
		}
	}

	public static void main(String[] args) {
// Simulamos una lista de archivos (no es necesario que existan)
		File[] archivos = { new File("logs/app1.log"), new File("logs/app2.log"), new File("logs/app3.log") };
// Para cada archivo creamos un Runnable y lo ejecutamos en un Thread
		for (int i = 0; i < archivos.length; i++) {
			Thread t = new Thread(new ProcesadorArchivo(archivos[i]), "Hilo-Proc-" + (i + 1));
			t.start();
		}
		System.out.println("Main: lanzadas tareas de procesamiento de archivos.");
// No esperamos a que terminen: en la práctica podríamos usar join() o un ExecutorService
	}
}
