package jerarquia;

import java.util.List;
import java.util.Objects;

import persistencia.AbstractDocumentable;
import excepciones.LineaMalFormadaException;
import persistencia.PersistenciaCSV;

/** Además de sus atributos, los entrenadores tienen una Pokedex */
public class Entrenador extends AbstractDocumentable {

	private static String CABECERA = "nombre;edad;pueblo;dinero";
	private static final String FICHERO = System.getProperty("user.dir") + "\\src\\ficheros\\EntrenadorPokemon.csv";
	public static final String MENSAJE_ERROR = "Error en entrenador";

	private String nombre;
	public int edad;
	private String pueblo;
	private double dinero;
	private Pokedex pokedex;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Entrenador))
			return false;
		Entrenador aux = (Entrenador) obj;
		return this.getNombre().equals(aux.getNombre());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getNombre() + "\n");
		sb.append(getPokedex());
		return sb.toString();
	}

	// TODO atributos, getters y setters
	// TODO el set de Edad tiene que ser public, el resto a gusto

	/**
	 * Recibe la línea del fichero, crea un objeto del tipo con ella, crea una
	 * Pokedex asociada y la carga de datos
	 * 
	 * @param línea la línea del fichero
	 * @throws LineaMalFormadaException
	 */
	public Entrenador(String linea) throws LineaMalFormadaException {
		super(linea);
	}

	/** Constructor copia */
	public Entrenador(Entrenador e) {
		super(e);

		if (e.pokedex != null) {
			this.pokedex = new Pokedex(e.pokedex);
		}
	}

	/** Carga la pokedex a partir del fichero de EntrenadorPokemon */
	public void cargaPokedex() {
		if (this.pokedex == null) this.pokedex = new Pokedex();

        List<EntrenadorPokemon> lista =
                PersistenciaCSV.getInstancia().leerDocumentables(FICHERO, EntrenadorPokemon::new);

        if (lista == null) return;

        for (EntrenadorPokemon ep : lista) {
            if (ep != null && Objects.equals(this.nombre, ep.getEntrenador())) {
                this.pokedex.metePokemon(ep.getIdPokemon());
            }
        }

	}

	@Override
	public String marshalling() {
		StringBuilder sb = new StringBuilder();
		sb.append(getNombre());
		sb.append(";");
		sb.append(getEdad());
		sb.append(";");
		sb.append(getPueblo());
		sb.append(";");
		sb.append(getDinero());
		return sb.toString();
	}

	@Override
	public void parseLínea(String linea) throws LineaMalFormadaException {
		String[] partes = linea.split(";");
		if (partes.length != 4) {
			throw new LineaMalFormadaException(MENSAJE_ERROR + ": " + linea);
		}
		setNombre(partes[0]);
		try {
			setEdad(Integer.parseInt(partes[1]));
		} catch (NumberFormatException e) {
			throw new LineaMalFormadaException(MENSAJE_ERROR + ": " + linea);
		}
		setPueblo(partes[2]);
		try {
			setDinero(Double.parseDouble(partes[3]));
		} catch (Exception e) {
			throw new LineaMalFormadaException(MENSAJE_ERROR + ": " + linea);
		}
	}

	@Override
	public String cabecera() {
		return CABECERA;
	}

	/**
	 * Dado el identificador del Pokemon, lo añade a la Pokedex
	 * 
	 * @param id el identificador del Pokemon
	 * @return si se añadió el Pokemon o no
	 */
	public boolean metePokemon(int id) {
		if (this.pokedex == null)
			this.pokedex = new Pokedex();
		return this.pokedex.metePokemon(id);
	}


	public static String getCABECERA() {
		return CABECERA;
	}

	public static void setCABECERA(String cABECERA) {
		CABECERA = cABECERA;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public double getDinero() {
		return dinero;
	}

	public void setDinero(double dinero) {
		this.dinero = dinero;
	}

	public static String getFichero() {
		return FICHERO;
	}

	public static String getMensajeError() {
		return MENSAJE_ERROR;
	}

	public Pokedex getPokedex() {
		return pokedex;
	}

	public void setPokedex(Pokedex pokedex) {
		this.pokedex = pokedex;
	}

}
