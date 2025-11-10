package persistencia;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import excepciones.LineaMalFormadaException;
import jerarquia.Entrenador;

public class PersistenciaCSVTest {
	
	private static final String FICHERITO_LECTURA = "datos.pdf";
	private static final String FICHERITO_ESCRITURA = "misco.pdf";
	
	private static final String CABECERA = "Cabecera";
	private static final String CADENA = cadena().toString();
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		
		Formatter formatter = new Formatter(
				new File(FICHERITO_LECTURA)
				);
		formatter.format(CABECERA+"\n");	
		formatter.format(CADENA);
		formatter.close();
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		
		new File(FICHERITO_LECTURA).delete();
		new File(FICHERITO_ESCRITURA).delete();
	}

	/**Comprueba el método de leerDocumentables de PersistenciaCSV*/
	@Test
	public void lecturaTest(){
		
		List<Entrenador> l = PersistenciaCSV
				.getInstancia()
				.leerDocumentables(FICHERITO_LECTURA, 
						Entrenador::new);
		try {
			assertEquals(
					new Entrenador(CADENA), 
					l.get(0)
					);
		} 
		catch (LineaMalFormadaException e) {
			fail("Error en lecturaTest");
		}
	}
	
	/**Comprueba el método de escribirDocumentables de PersistenciaCSV*/
	@Test
	public void escrituraTest(){
		List<Entrenador> l = new LinkedList<>();
		try {
			l.add(new Entrenador(CADENA));
			PersistenciaCSV
			.getInstancia()
			.escribirDocumentables(FICHERITO_ESCRITURA, l, CABECERA);
			
			Scanner s = new Scanner(
					new File(FICHERITO_ESCRITURA)
					);
			assertEquals(CABECERA, s.nextLine());
			assertEquals(CADENA, s.nextLine());
			s.close();
		} 
		catch (LineaMalFormadaException e) {
			fail("Fallo en escrituraTest");
		} 
		catch (FileNotFoundException e) {
			fail("Fallo en escrituraTest");
		}
		
		
	}
	
	private static StringBuilder cadena() {
		int[] valores = 
			{65, 115, 104, 59, 49, 48, 59, 80, 117, 101, 
			98, 108, 111, 32, 80, 97, 108, 101, 116, 97, 
			59, 53, 48, 48, 48, 46, 50, 51};
		StringBuilder sb = new StringBuilder();
        for (int n: valores) sb.append((char) n);
        return sb;
	}
	
	

}
