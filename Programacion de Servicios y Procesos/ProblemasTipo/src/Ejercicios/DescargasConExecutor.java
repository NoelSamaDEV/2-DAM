package Ejercicios;

//Archivo: DescargasConExecutor.java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DescargasConExecutor {
// Simula una tarea de descarga que devuelve el nombre del recurso descargado
	static class TareaDescarga implements Callable<String> {
		private final String recurso;

		public TareaDescarga(String recurso) {
			this.recurso = recurso;
		}

		@Override
		public String call() throws Exception {
			System.out.printf("Thread %s: empezando descarga de %s%n", Thread.currentThread().getName(), recurso);
// Simulamos tiempo de descarga variable
			long duracion = 500 + (long) (Math.random() * 1500);
			Thread.sleep(duracion);
			System.out.printf("Thread %s: terminada descarga de %s (t=%dms)%n", Thread.currentThread().getName(),
					recurso, duracion);
			return recurso + " (ok)";
		}
	}

	public static void main(String[] args) {
// Lista de recursos a descargar (simulada)
		List<String> recursos = List.of("img1.png", "img2.png", "datos.json", "video.mp4", "img3.png");
// Creamos un ExecutorService con un pool fijo (control de concurrencia)
		ExecutorService executor = Executors.newFixedThreadPool(3); // máximo 3 hilos simultáneos
		List<Future<String>> resultados = new ArrayList<>();
// Enviamos tareas al executor y guardamos futuros
		for (String r : recursos) {
			Future<String> futuro = executor.submit(new TareaDescarga(r));
			resultados.add(futuro);
		}
// Respondemos a medida que terminan (podríamos usar invokeAll para bloquear)
		for (Future<String> f : resultados) {
			try {
				String res = f.get(); // espera a que la tarea termine y obtiene el resultado
				System.out.println("Resultado recibido: " + res);
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("Error en tarea de descarga: " + e.getMessage());
			}
		}
// Siempre cerrar el executor
		executor.shutdown();
		try {
			if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}
		System.out.println("Todas las descargas procesadas. Fin.");
	}
}