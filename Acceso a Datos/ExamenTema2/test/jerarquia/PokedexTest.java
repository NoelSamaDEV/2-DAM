package jerarquia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PokedexTest {

	/**Comprueba que el método añadir funciona bien: al meter
	 * uno nuevo devuelve true y al meter uno repetido
	 * devuelve false*/
	@Test
	public void metePokemonTest() {
		Pokedex p = new Pokedex();
		assertTrue(p.metePokemon(1));
		assertFalse(p.metePokemon(1));
	}
	
	/**Comprueba que el set de Pokemons es un set de copias*/
	@Test
	public void listPokemonsEsCopiaTest(){
		Pokedex pokedex = new Pokedex();
		int nuevaSalud = 0;
		pokedex.listaPokemons().forEach(p->p.setSalud(nuevaSalud));
		for(Pokemon p: pokedex.listaPokemons()){
			assertNotEquals(nuevaSalud, p.getSalud());
		}
	}
	
	/**Comprueba que al meter un identificador de un Pokemon
	 * que no existe, devuelve null*/
	@Test
	public void getPokemonNullTest() {
		assertEquals(null, new Pokedex().getPokemon(0));
	}
	
	/**Comprueba que la pokedex master tiene todos los pokemons*/
	@Test
	public void pokedexMasterCompleta() {
		int i = 0;
		assertNull(PokedexMaster
				.getInstancia()
				.getPokemon(i));
		for(i=1; i<=151;i++) {
			assertNotNull(PokedexMaster
					.getInstancia()
					.getPokemon(i));
		}
		assertNull(PokedexMaster
				.getInstancia()
				.getPokemon(i));
	}

}
