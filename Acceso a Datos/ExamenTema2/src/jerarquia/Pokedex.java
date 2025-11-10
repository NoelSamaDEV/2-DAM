package jerarquia;

/**La pokedex de cada uno*/
public class Pokedex extends AbstractPokedex {
	
	/**Obtiene el Pokemon de la PokedexMaster y, si no es nulo,
	 * lo añade a esta Pokedex
	 * @param id el identificador del Pokemon
	 * @return si añadió el Pokemon (porque existe) o no. 
	 * En caso de que ya existiese en la Pokedex, devuelve false*/
	public boolean metePokemon(int id) {
		if (this.pokemons.containsKey(id)) {
			return false;
		}

		Pokemon p = PokedexMaster.getInstancia().getPokemon(id);

		if (p == null) {
			return false;
		}

		this.pokemons.put(id, p);
		return true;
	}
	
	/**Constructor por defecto*/
	public Pokedex() {
		super();
	}
	
	/**Constructor copia de Pokedex
	* @param pokedex otra Pokedex a copiar*/
	public Pokedex(Pokedex pokedex) {
		super(); 
		if (pokedex != null && pokedex.pokemons != null) {
			for (Pokemon p : pokedex.pokemons.values()) {
				this.pokemons.put(p.getId(), new Pokemon(p));
			}
		}
	}
}
