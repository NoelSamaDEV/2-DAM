package jerarquia;

import java.util.List;

import persistencia.PersistenciaCSV;

/** La pokedex maestra, la que tiene todos los pokemons */
public final class PokedexMaster extends AbstractPokedex {

	private static PokedexMaster instancia;
	private static final String FICHERO = System.getProperty("user.dir") + "\\src\\ficheros\\Pokemon.csv";

	public static PokedexMaster getInstancia() {
		if (instancia == null) {
			instancia = new PokedexMaster();
		}
		return instancia;
	}

	/**Carga todos los datos del fichero de Pokemons*/
	private PokedexMaster() {
		super();
		List<Pokemon> lista = PersistenciaCSV.getInstancia()
				.leerDocumentables(FICHERO, Pokemon::new);
		
		if (lista != null) {
			for (Pokemon p : lista) {
				if (p != null) {
					this.pokemons.put(p.getId(), p);
				}
			}
		}
	}
}