package entidad;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="departamento")
public class Departamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String nombre;
	@Column
	private double presupuesto;
	@Column
	private double gastos;
	
	/*
	@OneToMany(cascade = CascadeType.ALL)
	private List<Empleado> empleados;
	*/
	
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "id_departamento")
	private List<Empleado> empleados;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		System.out.println(id);
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	public double getGastos() {
		return gastos;
	}
	public void setGastos(double gastos) {
		this.gastos = gastos;
	}
	
	
}
