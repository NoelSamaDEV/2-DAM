package parte2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Ejercicio2_bis {

	public static void main(String[] args) {

		BufferedReader buffer = null;
		List<Restaurante> l = null;

		try {
			buffer = new BufferedReader(new FileReader(Ejercicio1.RESTAURANTES));
			try {
				
				l = leerFichero(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (l != null) {
			imprimirCodigoPostal6(l);
			
			Predicate<Restaurante> predicate =
				(r) ->{ 
				return r.getZipCode()/10000 == 6;
						};
				Consumer<Restaurante> consumer =
					(r) -> {System.out.println(r.getNombre());
								};
				gestorRestaurantes(l, predicate, consumer);
				// imprimo todos los restaurantes
				gestorRestaurantes(l, 
				(r) -> true,
				(r) -> System.out.println(r.getNombre()));
		}
	}
	private static List<Restaurante> leerFichero(BufferedReader buffer) throws IOException {
		String linea = buffer.readLine();
		linea = buffer.readLine();
		List<Restaurante> l = new LinkedList<>();
		while (linea != null) {
			Restaurante r = Restaurante.parse(linea);
			l.add(r);
			linea = buffer.readLine();

		}
		return l;
	}
		private static void imprimirCodigoPostal6(List<Restaurante> l) {
			for (Restaurante r : l) {
				if (r.getZipCode()/10000 == 6) {
					System.out.println(r.getNombre());
				}
			}
		}
	public static void gestorRestaurantes(List<Restaurante> lista,
			Predicate<Restaurante> predicate,
			Consumer<Restaurante> consumer) {
		for (Restaurante r : lista) {
			if (predicate.test(r)) {
				consumer.accept(r);
			}
		}
	}
}
