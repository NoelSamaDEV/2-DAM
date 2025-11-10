package LogicaNegocio;

import excepciones.LineaMalFormadaException;
import persistencia.AbstractDocumentable;

public class Empleado extends AbstractDocumentable {
	
	public static final String CABECERA = "codEmpleado;nombre;telefono";
	public static final String EMPLEADO_ERROR = "Error en empleado";
	
	private String codEmpleado;
	private String nombre;
	private String telefono;
	
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

	public Empleado(String línea) throws LineaMalFormadaException {
		super(línea);
	}

	@Override
	public String marshalling() {
		return getCodEmpleado()+";"+getNombre()+";"+getTelefono();
	}

	@Override
	public String cabecera() {
		return CABECERA;
	}
	
	
	@Override
	public String toString() {
		return "Empleado [codEmpleado=" + codEmpleado + ", nombre=" + nombre + ", telefono=" + telefono + "]";
	}

	@Override
	public void parseLínea(String linea) 
			throws LineaMalFormadaException {
		// El único error es que no haya suficientes campos
		//cod2;paco ;222222222
		String[] trozos = linea.split(";");
		if (trozos.length<3) {
			throw new LineaMalFormadaException(EMPLEADO_ERROR);
		}
		// todo bien? set
		setCodEmpleado(trozos[0]);
		setNombre(trozos[1]);
		setTelefono(trozos[2]);
	}
	
	

}
