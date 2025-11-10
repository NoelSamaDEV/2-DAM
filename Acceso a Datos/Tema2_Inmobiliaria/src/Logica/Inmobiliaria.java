package Logica;

import java.util.List;

public class Inmobiliaria {
	
	private List<Inmueble> listaInmuebles;
	private List<Empleado> listaEmpleados;
	
	public Inmobiliaria(List<Inmueble> listaInmuebles, List<Empleado> listaEmpleados) {
		this.listaInmuebles = listaInmuebles;
		this.listaEmpleados = listaEmpleados;
	}
	
	public String listarDatos() {
		
		return null;
		
	}
	
	public String listarInmuebles(String codInmueble) {
		
		return codInmueble;

		
	}
	
	public int guardaDatos(String nombreFicheroEmpleados, String nombreFicheroInmuebles) {
		
		return 0;
	}
	
	public void nuevoInmueble(String lineaDatos) {

	}
	
	public void nuevoEmpleado(String lineaDatos) {

	}
	
	public boolean borrarInmueble(String codInmueble) {

		return false;
	}
}
