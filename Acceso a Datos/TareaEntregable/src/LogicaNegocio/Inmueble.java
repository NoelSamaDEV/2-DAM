package LogicaNegocio;

import excepciones.LineaMalFormadaException;
import persistencia.AbstractDocumentable;

public class Inmueble extends AbstractDocumentable {
	
	public static final String CABECERA = "codInmueble;direccion;tipo;codEmpleado;precio";
	private static final String INMUEBLE_ERROR = "Error en inmueble";
	
	private String codInmueble;
	private String direccion;
	private String tipo;
	private String codEmpleado;
	private double precio;
	
	public String getCodInmueble() {
		return codInmueble;
	}

	public void setCodInmueble(String codInmueble) {
		this.codInmueble = codInmueble;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodEmpleado() {
		return codEmpleado;
	}

	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Inmueble(String linea) throws LineaMalFormadaException {
		super(linea);
	}
	
	/*public Inmueble(String codInmueble, String direccion,
			String tipo, double precio) {
		super(codInmueble+";"+direccion+";"+tipo+";"+";"+precio);
	}*/

	@Override
	public String marshalling() {
		// "codInmueble;direccion;tipo;codEmpleado;precio"
		String cadena = getCodInmueble()+";";
		cadena += getDireccion()+";";
		cadena += getTipo()+";";
		cadena += getCodEmpleado()+";";
		cadena += getPrecio();
		return cadena;
	}
	
	
	@Override
	public String toString() {
		return "Inmueble [codInmueble=" + codInmueble + ", direccion=" + direccion + ", tipo=" + tipo + ", codEmpleado="
				+ codEmpleado + ", precio=" + precio + "]";
	}

	@Override
	public String cabecera() {
		return CABECERA;
	}

	@Override
	public void parseLínea(String linea) throws LineaMalFormadaException {
		// "codInmueble;direccion;tipo;codEmpleado;precio"
		// posibles pegas: no hay 5 campos o el precio no es double
		String[] trozos = linea.split(";");
		if (trozos.length!=5)
			throw new LineaMalFormadaException(INMUEBLE_ERROR);
		String codInmueble = trozos[0];
		String direccion = trozos[1];
		String tipo = trozos[2];
		String codEmpleado = trozos[3];
		String precioS = trozos[4];
		double precio = 0;
		// hay que intentar parsear el precio
		try {
			precio = Double.parseDouble(precioS);
		}
		catch (NumberFormatException e) {
			throw new LineaMalFormadaException(INMUEBLE_ERROR);
		}
		// todo bien? set
		setCodInmueble(codInmueble);
		setDireccion(direccion);
		setTipo(tipo);
		setCodEmpleado(codEmpleado);
		setPrecio(precio);
		
	}

}
