package jerarquia;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class AbstractPokedex {
	
	protected Map<Integer, Pokemon> pokemons;
	
	@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		if (!(obj instanceof AbstractPokedex)) return false;
		AbstractPokedex aux = (AbstractPokedex) obj;
		return this.getPokemons().equals(aux.getPokemons());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Entry<Integer, Pokemon> entry: this.pokemons.entrySet()) {
			int id = entry.getKey();
			Pokemon p = entry.getValue();
			String fila = String.format("%d: %s\n", id, p);
			sb.append(fila);
		}			
		return sb.toString();
	}
	
	public AbstractPokedex() {
		//TODO
		this.pokemons = new LinkedHashMap<>();
	}
	
	/**Devuelve una <b>copia</b> del pokemon por el identificador o 
	 * null en caso de que no exista
	 * @param id el identificador del pokemon
	 * @return el objeto Pokemon o null en caso de no existir*/
	public Pokemon getPokemon(int id) {
		Pokemon p = this.pokemons.get(id);
		if (p == null) return null;
		return new Pokemon(p);
	}
	
	/**Devuelve una copia del atributo
	 * @return una copia del atributo pokemons*/
	public Map<Integer, Pokemon> getPokemons() {
		Map<Integer, Pokemon> copia = new LinkedHashMap<>();
		for (Map.Entry<Integer, Pokemon> entry : this.pokemons.entrySet()) {
			copia.put(entry.getKey(), new Pokemon(entry.getValue()));
		}
		return copia;
	}
	
	/**Devuelve un set con <b>copias</b> de los Pokemons
	 * que contiene
	 * @return un set de copias de Pokemons*/
	public Set<Pokemon> listaPokemons(){
		Set<Pokemon> copia = new java.util.LinkedHashSet<>();
		for (Pokemon p : this.pokemons.values()) {
			copia.add(new Pokemon(p));
		}
		return copia;
	}

}
























