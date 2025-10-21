package Persistencia;

import java.util.List;

import Principal.Coche;

public interface persistencia {
	
	void leerCoches(String nombreFichero,List<Coche> listaCoches);
	
	void escribirCoches(String nombreFichero,List<Coche> listaCoches);
}
