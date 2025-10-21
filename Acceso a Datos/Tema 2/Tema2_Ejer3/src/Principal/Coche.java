package Principal;

import excepciones.LineaMalFormadaException;

public class Coche {
	private int id;
	private String marca;
	private String modelo;
	
	public Coche(int id, String marca, String modelo) {
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
	}
	/**
	 * Recibe una linea con el formato id;marca;modelo
	 * Lanza excepcion si la linea esta mal formada
	 * @throws LineaMalFormadaException 
	 */
	public Coche(String linea) throws LineaMalFormadaException {
        //parto la linea por los ;
		String[] trozos = linea.split(";");
		if (trozos.length != 3) {
		    throw new LineaMalFormadaException(linea);
		}
		int id = 0;   
		try {
            id = Integer.parseInt(trozos[0]);
        } 
		catch (NumberFormatException e) {
        	  throw new LineaMalFormadaException(linea);
        }
		String marca = trozos[1];
		String modelo = trozos[2];
		if (marca.isBlank() || modelo.isBlank()) {
            throw new LineaMalFormadaException(linea);
		}
		setId(id);
		setMarca(marca);
		setModelo(modelo);
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	@Override
	public String toString() {
		return "Coche [Id=" + getId() + ", Marca=" + getMarca() + ", Modelo=" + getModelo() + "]";
	}
	
	public String serialize() {
		return String.format("%d;%s;%s", getId(), getMarca(), getModelo());
	}
}
