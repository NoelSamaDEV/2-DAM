package jerarquia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PokemonTest {

	/**Con datos correctos, se crea un Pokemon de forma
	 * adecuada con todos sus campos*/
	@Test
	public void pokemonConstructorPositivoTest() {
		int id = 1;
		String nombre = "Bulbasaru";
		String tipo = "planta".toUpperCase();
		int salud = 45;
		int ataque = 49;
		int defensa = 49;
		String habilidad = "Espesura";
		String linea =
				String.format("%d;%s;%s;%d;%d;%d;%s;",
						id, nombre, tipo,
						salud, ataque, defensa, habilidad);
		Pokemon pokemon = null;
		try {
			pokemon = new Pokemon(linea);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("Error en pokemonConstructorPositivoTest");
		}
		
		assertEquals(id, pokemon.getId());
		assertEquals(nombre, pokemon.getNombre());
		assertEquals(Tipo.valueOf(tipo), pokemon.getTipo());
		assertEquals(salud, pokemon.getSalud());
		assertEquals(ataque, pokemon.getAtaque());
		assertEquals(defensa, pokemon.getDefensa());
		assertEquals(habilidad, pokemon.getHabilidad());
		
	}
	
	/**Con datos incorrectos, se genera una excepción
	 * con el mensaje dado*/
	@Test
	public void pokemonConstructorNegativoTest() {
		int id = 1;
		String nombre = "Bulbasaru";
		String tipo = "planta".toUpperCase();
		int salud = 45;
		String ataque = "ataque";
		int defensa = 49;
		String habilidad = "Espesura";
		String linea =
				String.format("%d;%s;%s;%d;%s;%d;%s;",
						id, nombre, tipo,
						salud, ataque, defensa, habilidad);
		@SuppressWarnings("unused")
		Pokemon pokemon = null;
		try {
			pokemon = new Pokemon(linea);
			fail("Error en pokemonConstructorNegativo");
		}
		catch (Exception e) {
			assertEquals(Pokemon.MENSAJE_ERROR, e.getMessage());
		}
		
	}
	
	/**Comprueba que el marshalling del Pokemon es correcto*/
	@Test
	public void pokemonMarshallingTest() {
		
		String linea = "2;Ivysaur;Planta;60;62;63;Espesura";
		Pokemon pokemon = null;
		try {
			pokemon = new Pokemon(linea);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail("Error en pokemonMarshallingTest");
		}
		assertEquals(linea, pokemon.marshalling());
	}

}
