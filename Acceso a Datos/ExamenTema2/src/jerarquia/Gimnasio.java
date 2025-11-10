package jerarquia;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

import persistencia.PersistenciaCSV;

public class Gimnasio {

	private Map<String, Entrenador> entrenadores;

	private static final String FICHERO = 
			System.getProperty("user.dir")+"\\src\\ficheros\\Entrenador.csv";

	/**Carga todos los datos del fichero de Entrenador*/
	public Gimnasio() {
		entrenadores = new LinkedHashMap<>();

		// Leemos los entrenadores desde el CSV
		List<Entrenador> lista = PersistenciaCSV.getInstancia()
				.leerDocumentables(FICHERO, Entrenador::new);

		if (lista != null) {
			for (Entrenador e : lista) {
				if (e != null) {
					// Cargar la pokedex de cada entrenador
					e.cargaPokedex();
					entrenadores.put(e.getNombre(), e);
				}
			}
		}
	}

	/**Devuelve una COPIA del entrenador a partir del nombre 
	 * del mismo o null en caso de que no exista el entrenador
	 * @param nombreEntrenador el nombre del entrenador a buscar
	 * @return una copia del objeto entrenador o null
	 * en caso de que no exista*/
	public Entrenador getEntrenador(String nombreEntrenador) {
		if (nombreEntrenador == null || !entrenadores.containsKey(nombreEntrenador)) {
			return null;
		}
		return new Entrenador(entrenadores.get(nombreEntrenador)); // copia defensiva
	}

	/**Devuelve un set con <b>copias</b> de los entrenadores del Gimnasio
	 * @return un set de entrenadores*/
	public Set<Entrenador> listaEntrenadores(){
		Set<Entrenador> copia = new LinkedHashSet<>();
		for (Entrenador e : entrenadores.values()) {
			copia.add(new Entrenador(e)); // copia defensiva
		}
		return copia;
	}

	/**Devuelve un set con <b>copias<b> de los Pokemons del Gimnasio
	 * @return un set de pokemons*/
	public Set<Pokemon> listaPokemons(){
		Set<Pokemon> pokemons = new LinkedHashSet<>();

		for (Entrenador e : entrenadores.values()) {
			Set<Pokemon> listaPoke = e.getPokedex().listaPokemons();
			for (Pokemon p : listaPoke) {
				pokemons.add(new Pokemon(p)); // copia defensiva
			}
		}
		return pokemons;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Entrenador e: entrenadores.values()) {
			sb.append(e.toString());
			sb.append("-------------\n");
		}
		return sb.toString();
	}
}

