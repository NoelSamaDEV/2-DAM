package jerarquia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import excepciones.LineaMalFormadaException;

/**Clase de prueba de Entrenador*/
public class EntrenadorTest {

	/**Con datos correctos, se crea un entrenador de forma
	 * adecuada con todos sus campos*/
	@Test
	public void entrenadorConstructorPositivoTest() {
		String nombre = "Ash";
		int edad = 10;
		String pueblo = "Pueblo Paleta";
		double dinero = 5000.23;
		String linea =
				String.format("%s;%d;%s;",
						nombre, edad, pueblo)+dinero;
		Entrenador entrenador = null;
		try {
			entrenador = new Entrenador(linea);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("Error en entrenadorConstructorPositivoTest");
		}
		assertEquals(nombre, entrenador.getNombre());
		assertEquals(edad, entrenador.getEdad());
		assertEquals(pueblo, entrenador.getPueblo());
		assertEquals(dinero, entrenador.getDinero());
		assertEquals(linea, entrenador.marshalling());
	}
	
	/**Con datos incorrectos, se genera una excepción
	 * con el mensaje dado*/
	@Test
	public void entrenadorConstructorNegativoTest() {
		String nombre = "Ash";
		int edad = 10;
		String pueblo = "Pueblo Paleta";
		double dinero = 5000.23;
		String linea =
				String.format("%s;%d;%s;%f",
						nombre, edad, pueblo, dinero);
		@SuppressWarnings("unused")
		Entrenador entrenador = null;
		try {
			entrenador = new Entrenador(linea);
			fail("Error en entrenadorConstructorNegativo");
		}
		catch (Exception e) {
			assertEquals(Entrenador.MENSAJE_ERROR, e.getMessage());
		}
		
	}
	
	/**Comprueba que la Pokedex del entrenador es correcta*/
	@Test
	public void entrenadorPokedexTest()  {
	
		String linea = "Brock;19;Pueblo Plateado;4100.27";
		Entrenador entrenador = null;
		
		try {
			entrenador = new Entrenador(linea);
		} 
		catch (LineaMalFormadaException e) {
			fail("Error en entrenadorPokedexTest");
		}
		Pokedex pokedex = new Pokedex();
		assertTrue(pokedex.metePokemon(74));
		assertTrue(pokedex.metePokemon(95));
		assertEquals(pokedex, entrenador.getPokedex());
	}
	
	/**Comprueba que el marshalling del entrenador es correcto*/
	@Test
	public void entrenadorMarshallingTest() {
		
		String linea = "Misty;12;Ciudad Celeste;3200.5";
		Entrenador entrenador = null;
		try {
			entrenador = new Entrenador(linea);
		} 
		catch (LineaMalFormadaException e) {
			fail("Error en entrenadorMarshallingTest");
		}
		assertEquals(linea, entrenador.marshalling());
	}

}
