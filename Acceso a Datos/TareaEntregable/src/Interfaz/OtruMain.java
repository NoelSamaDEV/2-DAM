package Interfaz;

import java.util.List;

import LogicaNegocio.Empleado;
import persistencia.PersistenciaCSV;

public class OtruMain {
	
	public static void main(String[] args) {
		
		
		List<Empleado> lista = PersistenciaCSV
				.getInstancia()
				.leerDocumentables(
						System.getProperty("user.dir")+
						"\\src"+"\\empelados.csv",
						Empleado::new);
		System.out.println(lista);
		
	}

}
