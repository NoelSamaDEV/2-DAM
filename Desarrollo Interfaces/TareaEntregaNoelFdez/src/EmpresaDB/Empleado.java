package EmpresaDB;

public class Empleado {
	private int codigo;
	private String nombre;
	private String apellidos;
	private double salario;
	private int hijos;
	private int departamento;
	
	
	public Empleado(int codigo, String nombre, String apellidos, double salario, int hijos,int departamento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.salario = salario;
        this.hijos = hijos;
        this.departamento = departamento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int getHijos() {
		return hijos;
	}

	public void setHijos(int hijos) {
		this.hijos = hijos;
	}

	public int getDepartamento() {
		return departamento;
	}

	public void setDepartamento(int departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return "Empleado [codigo=" + codigo + ", nombre=" + nombre + ", apellidos=" + apellidos + ", salario=" + salario
				+ ", hijos=" + hijos + ", departamento=" + departamento + "]";
	}
	
	
}
