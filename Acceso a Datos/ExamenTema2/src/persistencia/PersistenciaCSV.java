package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import excepciones.LineaMalFormadaException;

public class PersistenciaCSV implements Persistencia {
		
	private static PersistenciaCSV instancia;
	
	public static PersistenciaCSV getInstancia() {
		if (instancia==null) {
			instancia = new PersistenciaCSV();
		}
		return instancia;
	}
	
	private PersistenciaCSV() {}

	@Override
	public <T extends Documentable> List<T> leerDocumentables(
			String nombreFichero,
			Constructor<String, T, LineaMalFormadaException> constructor) {

		List<T> lista = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
			String linea;

			while ((linea = br.readLine()) != null) {
				if (linea.length() == 0) continue;

				try {
					T objeto = constructor.apply(linea);
					if (objeto != null) {
						lista.add(objeto);
					}
				} catch (LineaMalFormadaException e) {
				}
			}

		} catch (Exception e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}

		return lista;
	}


	@Override
	public <T extends Documentable> void escribirDocumentables(
			String nombreFichero,
			List<T> lista,
			String cabecera) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {

			if (cabecera != null && cabecera.length() > 0) {
				bw.write(cabecera);
				bw.newLine();
			}

			if (lista != null) {
				for (T elemento : lista) {
					if (elemento != null) {
						bw.write(elemento.marshalling());
						bw.newLine();
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Error al escribir el fichero: " + e.getMessage());
		}
	}

}
