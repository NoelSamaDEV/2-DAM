package entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name  = "coche")
public class Coche {
	@Id
	private String matricula;
	@Column
	private String marca;
	@Column
	private int precio;
	
	@ManyToOne
	@JoinColumn(name="dni")
	private Propietario propietario;
	
	public Coche() {
		
	}
	public Coche(String matricula, String marca, int precio, Propietario propietario) {
		setMatricula(matricula);
		setMarca(marca);
		setPrecio(precio);
		setPropietario(propietario);
	}
	
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		if (matricula==null || matricula.length()!=6) {
			throw new IllegalArgumentException("La matricula debe tener 6 caracteres");
		}
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Propietario getPropietario() {
		return propietario;
	}
	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		return "Coche [matricula=" + matricula + ", marca=" + marca + ", precio=" + precio + ", propietario="
				+ propietario + "]";
	}
	
	
	
}
