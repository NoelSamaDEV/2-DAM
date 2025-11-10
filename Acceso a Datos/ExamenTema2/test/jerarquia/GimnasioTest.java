package jerarquia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class GimnasioTest {

	/**Comprueba que los nombres de los entrenadores del
	 * gimnasio son los que deberían*/
	@Test
	public void listaEntrenadoresTest() {
		Set<String> nombres = new HashSet<>();
		for(Entrenador e: new Gimnasio().listaEntrenadores()) {
			nombres.add(e.getNombre());
		}
		String salida = "Brock, Gary, Goh, Misty, Norman, Cynthia, Dawn, May, Ash, Serena";
		Set<String> salidaSet = new HashSet<>();
		for(String s: salida.split(", ")) salidaSet.add(s);
		assertEquals(salidaSet, nombres);
	}
	
	/**Comprueba que el set de entrenadores es un set de copias*/
	@Test
	public void listaEntrenadoresCopiaTest() {
		Set<Entrenador> lista = new Gimnasio().listaEntrenadores();
		int nuevaEdad = 0;
		lista.forEach(e->e.setEdad(nuevaEdad));
		for(Entrenador e: new Gimnasio().listaEntrenadores()) {
			assertNotEquals(nuevaEdad, e.getEdad());
		}
	}
	
	/**Comprueba que los nombres de los Pokemons del
	 * gimnasio son los que deberían*/
	@Test
	public void listaPokemonsTest() {
		Set<String> nombres = new HashSet<>();
		for(Pokemon p: new Gimnasio().listaPokemons()) {
			nombres.add(p.getNombre());
		}
		
		String salida = "Onix, Tangela, Bulbasaur, Starmie, Staryu, Vileplume, Geodude, Bulbasaur, Pikachu";
		Set<String> salidaSet = new HashSet<>();
		for(String s: salida.split(", ")) salidaSet.add(s);
		assertEquals(salidaSet, nombres);
	}
	
	/**Comprueba que el set de Pokemons es un set de copias*/
	@Test
	public void listaPokemonsCopiaTest() {
		Set<Pokemon> lista = new Gimnasio().listaPokemons();
		int nuevaSalud = 0;
		lista.forEach(p->p.setSalud(nuevaSalud));
		for(Pokemon p: new Gimnasio().listaPokemons()) {
			assertNotEquals(nuevaSalud, p.getSalud());
		}
	}
	
	/**Comprueba que el buscador de entrenador en el gimnasio
	 * funciona bien y al darle un nombre existente, devuelve
	 * un entrenador con ese nombre*/
	@Test
	public void gimnasioEntrenadorPositivoTest() {
		String nombre = "Ash";
		assertEquals(nombre, new Gimnasio()
				.getEntrenador(nombre)
				.getNombre());
	}
	
	/**Comprueba que el buscador de entrenador en el gimnasio
	 * funciona bien y al darle un nombre no existente, 
	 * devuelve null*/
	@Test
	public void gimnasioEntrenadorNegativoTest() {
		String nombre = "misco";
		assertEquals(null, new Gimnasio().getEntrenador(nombre));
	}
	
	/**Comprueba que el buscador de entrenador en el gimnasio
	 * funciona bien y al darle un nombre existente, devuelve
	 * un entrenador con ese nombre pero es una copia*/
	@Test
	public void gimnasioEntrenadorEsCopiaTest() {
		Gimnasio g = new Gimnasio();
		String nombre = "Ash";
		int nuevaEdad = 1;
		g.getEntrenador(nombre).setEdad(nuevaEdad);
		assertNotEquals(nuevaEdad, g.getEntrenador(nombre));
	}

}
