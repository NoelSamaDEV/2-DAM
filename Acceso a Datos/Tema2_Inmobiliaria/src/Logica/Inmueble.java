package Logica;

public class Inmueble {
	private String codInmueble;
	private String direccion;
	private String tipo;
	private String codEmpleado;
	private double precio;
	
	public Inmueble(String codInmueble, String direccion, String tipo, double precio) {
		this.codInmueble = codInmueble;
		this.direccion = direccion;
		this.tipo = tipo;
		this.precio = precio;
	}
	
	public Inmueble(String linea) {
		String[] partes = linea.split(";");
		this.codInmueble = partes[0];
		this.direccion = partes[1];
		this.tipo = partes[2];
		this.codEmpleado = partes[3];
		this.precio = Double.parseDouble(partes[4]);
	}

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

	@Override
	public String toString() {
		return "Inmueble [codInmueble=" + codInmueble + ", direccion=" + direccion + ", tipo=" + tipo + ", codEmpleado="
				+ codEmpleado + ", precio=" + precio + "]";
	}
	
	public String serializar() {
		return codInmueble + ";" + direccion + ";" + tipo + ";" + codEmpleado + ";" + precio;
	}
}
