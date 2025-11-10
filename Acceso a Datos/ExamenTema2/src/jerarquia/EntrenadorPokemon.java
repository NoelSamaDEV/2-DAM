package jerarquia;

import persistencia.AbstractDocumentable;
import excepciones.LineaMalFormadaException;

public class EntrenadorPokemon extends AbstractDocumentable {
	
	private static final String CABECERA = "nombre;id";
	public static final String MENSAJE_ERROR = "Error en EntrenadorPokemon";
	
	private String entrenador;
	private int idPokemon;
	
	//TODO atributos, getters y setters

	public EntrenadorPokemon(String línea) throws LineaMalFormadaException {
		super(línea);
	}

	@Override
	public String marshalling() {
		return String.format("%s;%d", 
				getEntrenador(), getIdPokemon());
	}

	@Override
	public String cabecera() {
		return CABECERA;
	}

	@Override
	public void parseLínea(String linea) 
			throws LineaMalFormadaException {
		try {
            if (linea == null) throw new LineaMalFormadaException(MENSAJE_ERROR);
            if (linea.equalsIgnoreCase(CABECERA)) {
                throw new LineaMalFormadaException(MENSAJE_ERROR);
            }

            String[] t = linea.split(";", -1);
            if (t.length != 2) throw new LineaMalFormadaException(MENSAJE_ERROR);

            this.entrenador = t[0];
            this.idPokemon = Integer.parseInt(t[1]);

        } catch (Exception e) {
            throw new LineaMalFormadaException(MENSAJE_ERROR);
        }
	}

	public String getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(String entrenador) {
		this.entrenador = entrenador;
	}

	public int getIdPokemon() {
		return idPokemon;
	}

	public void setIdPokemon(int idPokemon) {
		this.idPokemon = idPokemon;
	}

	public static String getCabecera() {
		return CABECERA;
	}

	public static String getMensajeError() {
		return MENSAJE_ERROR;
	}
	
	

}
