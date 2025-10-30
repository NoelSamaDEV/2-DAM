package Examen;

public class Empleado {

	private String Dni;
	private String Nombre;
	private String Apellido;
	private int Departamento;
	
	public Empleado(String dni, String nombre, String apellido, int departamento) {
		super();
		this.Dni = dni;
		this.Nombre = nombre;
		this.Apellido = apellido;
		this.Departamento= departamento;
	}

	public String getDni() {
		return Dni;
	}

	public void setDni(String dni) {
		Dni = dni;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public int getDepartamento() {
		return Departamento;
	}

	public void setDepartamento(int departamento) {
		Departamento = departamento;
	}

	@Override
	public String toString() {
		return "Empleado [Dni=" + Dni + ", Nombre=" + Nombre + ", Apellido=" + Apellido + ", Departamento="
				+ Departamento + "]";
	}

}
