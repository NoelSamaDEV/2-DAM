package Interfaz;

import Logica.Inmobiliaria;

public class Main {

	public static void main(String[] args) {
		
		Inmobiliaria im = new Inmobiliaria(null, null);
		
		im.listarDatos();
		im.listarInmuebles("");
		im.guardaDatos("empleados.txt", "inmuebles.txt");
		im.nuevoEmpleado("E001;Juan Perez;555-1234");
		im.nuevoInmueble("I001;Calle Falsa 123;Casa;E001;150000");
		im.borrarInmueble("I001");
	}

}
