package Principal;

import java.util.LinkedList;
import java.util.List;

import Persistencia.PersistenciaCSV;

public class Concesionario {
	
	private List<Coche> listaCoches;
	
	public Concesionario(String nombreFicheroOrigen) {
		// Hay que arrancar el atributo para que la persistencia lo modifique
		listaCoches = new LinkedList<>();
		
		new PersistenciaCSV().leerCoches(nombreFicheroOrigen, listaCoches);
	}

	public String listarCoches() {
		return listaCoches.toString();
	}

	public void NuevoCoche(String marca, String modelo) {
		//saco el id: tamaño de la lista +1
		int id = listaCoches.size()+1;
		//se hace un coche nuevo
		Coche c = new Coche(id,marca,modelo);
		//se añade a la lista
		listaCoches.add(c);
	}

	public String listarCochesPorMarca(String marca) {
		String salida = "";
		for (Coche c : listaCoches) {
			if (c.getMarca().compareTo(marca)==0) {
				salida += c.toString() + "";
			
			}
		}
		return salida;
	}

	public void guardarDatos(String nombreFicheroDestino) {
		new PersistenciaCSV().escribirCoches(nombreFicheroDestino, listaCoches);
		
	}
}
