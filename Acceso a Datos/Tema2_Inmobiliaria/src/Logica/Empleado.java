package Logica;

public class Empleado {
	private String codEmpleado;
	private String nombre;
	private String telefono;
	
	public Empleado(String codEmpleado, String nombre, String telefono) {
		this.codEmpleado = codEmpleado;
		this.nombre = nombre;
		this.telefono = telefono;
	}
	
	public Empleado(String linea) {
		String[] partes = linea.split(";");
		this.codEmpleado = partes[0];
		this.nombre = partes[1];
		this.telefono = partes[2];
	}

	public String getCodEmpleado() {
		return codEmpleado;
	}

	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String toString() {
		return "Empleado [codEmpleado=" + codEmpleado + ", nombre=" + nombre + ", telefono=" + telefono + "]";
	}
	
	public String serializar() {
		return codEmpleado + ";" + nombre + ";" + telefono;
	}
}
