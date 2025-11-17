package entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "propietario")
public class Propietario {
	@Id
	private String dni;
	@Column
	private String nombre;
	@Column
	private String edad;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Propietario [dni=" + dni + ", nombre=" + nombre + ", edad=" + edad + "]";
	}
}
