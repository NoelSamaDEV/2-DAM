package jerarquia;

import persistencia.AbstractDocumentable;
import excepciones.LineaMalFormadaException;

public class Pokemon extends AbstractDocumentable {
	
	private static final String CABECERA = "Id;Nombre;Tipo;Salud;Ataque;Defensa;Habilidad";
	public static final String MENSAJE_ERROR = "Error en pokemon";
	
	private int id;
	private String nombre;
	public int salud;
	private int ataque;
	private int defensa;
	private String habilidad;
	private Tipo tipo;
	
	
	
	public Pokemon(String línea, int id, String nombre, int salud, int ataque, int defensa, String habilidad, Tipo tipo)
			throws LineaMalFormadaException {
		super(línea);
		this.id = id;
		this.nombre = nombre;
		this.salud = salud;
		this.ataque = ataque;
		this.defensa = defensa;
		this.habilidad = habilidad;
		this.tipo = tipo;
	}

	//TODO atributos, getters y setters
	//TODO el set de Salud tiene que ser public, el resto a gusto
	
	private void setTipo(String tipo) {
		this.tipo = Tipo.valueOf(tipo.toUpperCase());
	}

	public Pokemon(String linea) throws LineaMalFormadaException {
		super(linea);
	}
	
	
	public Pokemon(Pokemon p) {
		super(p);
	}

	@Override
	public String cabecera() {
		return CABECERA;
	}

	@Override
	public String marshalling() {
		StringBuilder sb = new StringBuilder();
		sb.append(getId());
		sb.append(";");
		sb.append(getNombre());
		sb.append(";");
		sb.append(getTipo());
		sb.append(";");
		sb.append(getSalud());
		sb.append(";");
		sb.append(getAtaque());
		sb.append(";");
		sb.append(getDefensa());
		sb.append(";");
		sb.append(getHabilidad());
		return sb.toString();
	}

	@Override
	public void parseLínea(String linea) throws LineaMalFormadaException {
		String[] partes = linea.split(";");
		if (partes.length!=7) {
			throw new LineaMalFormadaException(MENSAJE_ERROR);
		} else {
			try {
				setId(Integer.parseInt(partes[0]));
				setNombre(partes[1]);
				setTipo(partes[2]);
				setSalud(Integer.parseInt(partes[3]));
				setAtaque(Integer.parseInt(partes[4]));
				setDefensa(Integer.parseInt(partes[5]));
				setHabilidad(partes[6]);
			} catch (Exception e) {
				throw new LineaMalFormadaException(MENSAJE_ERROR);
			}
		}
		
		
	
	}
	
	@Override
	public String toString() {
		return getNombre();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		if (!(obj instanceof Pokemon)) return false;
		Pokemon aux = (Pokemon)obj;
		return getId()==aux.getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSalud() {
		return salud;
	}

	public void setSalud(int salud) {
		this.salud = salud;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public String getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(String habilidad) {
		this.habilidad = habilidad;
	}

	public static String getCabecera() {
		return CABECERA;
	}

	public static String getMensajeError() {
		return MENSAJE_ERROR;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	

}
