package entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="empleado")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true)
	private String nif;
	@Column
	private String nombre;
	@Column
	private String apellido1;
	@Column
	private String apellido2;
	
	
	
	public Empleado(String nif, String nombre, String apellido1, String apellido2, Departamento departamento) {
		/*super();
		this.id = id;
		this.nif = nif;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.departamento = departamento;*/
		setNif(nif);
		setNombre(nombre);
		setApellido1(apellido1);
		setApellido2(apellido2);
		setDepartamento(departamento);
	}
	public Empleado() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name = "id_departamento")
	private Departamento departamento;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		System.out.println(id);
		this.id = id;
	}
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public Departamento getDepartamento() {
		return this.departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		String departamento;
		if (getDepartamento()==null) departamento="null";
		else departamento = getDepartamento().getNombre();
		return String.format(
				"Empleado [Id=%s, Nif=%s, Nombre=%s, Apellido1=%s, Apellido2=%s, Departamento=%s]",
				getId(), getNif(), getNombre(), getApellido1(), getApellido2(), departamento);
	}
	
	
	
	
}
