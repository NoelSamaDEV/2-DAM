package LogicaNegocio;

import java.util.LinkedList;
import java.util.List;

import excepciones.LineaMalFormadaException;
import persistencia.Documentable;
import persistencia.PersistenciaCSV;

public class Inmobiliaria {
	
	private List<Inmueble> listaInmuebles;
	private List<Empleado> listaEmpleados;

	public Inmobiliaria(String ficheroEmpleados, 
			String ficheroInmuebles) {
		// inicializamos atributos
		listaInmuebles = new LinkedList<>();
		listaEmpleados = new LinkedList<>();
		// cargamos los datos de los ficheros
		// primero empleados, porque los inmuebles dependen de ellos
		PersistenciaCSV
		.getInstancia()
		.leerEmpleado(ficheroEmpleados, listaEmpleados);
		PersistenciaCSV
		.getInstancia()
		.leerInmueble(ficheroInmuebles
				, listaInmuebles
				, listaEmpleados
				);
	}

	/**Retorna una cadena con el listado de todos los datos; 
	 * es decir inmuebles y empleados*/
	public String listarDatos() {
		// copio inmuebles
		List<Documentable> aux =
				new LinkedList<>(listaInmuebles);
		// también copio empleados
		aux.addAll(listaEmpleados);
		return aux.toString();
	}

	/**Retornar una cadena con el listado de los datos 
	 * asociados a un inmueble. En este listado se deberá 
	 * retornar también la información asociada al empleado 
	 * que se encarga del inmueble*/
	public String listadoInmuebles(String codInmueble) {
		// itero buscando el inmueble
		Inmueble inm = buscaInmueble(codInmueble);
		if (inm==null) return "";
		String cadena = inm.toString();
		// saco el empleado asociado
		Empleado e = buscaEmpleado(inm.getCodEmpleado());
		cadena += e.toString();
		return cadena;
	}

	/**Devuelve el inmueble por su código o null
	 * si no hay ningún mueble con ese código*/
	private Inmueble buscaInmueble(String codInmueble) {
		for(Inmueble inm: listaInmuebles) {
			if (inm.getCodInmueble().compareTo(codInmueble)==0) {
				return inm;
			}
		}
		return null;
	}

	/**Devuelve el empleado por su código o null
	 * si no hay ningún mueble con ese código*/
	private Empleado buscaEmpleado(String codEmpleado) {
		for(Empleado emp: listaEmpleados) {
			if (emp.getCodEmpleado().compareTo(codEmpleado)==0) {
				return emp;
			}
		}
		return null;
	}

	/**Se recibe desde el menú una cadena con los 
	 * datos asociados a un nuevo inmueble. Este inmueble 
	 * debe ser incluido siempre que se verifique el no 
	 * existe un código igual asociado a otro inmueble y 
	 * además el empleado existe dentro de la base de datos*/
	public void insertarInmueble(String linea) {
		/* Genero un objeto inmueble, miro que los campos
		 * son correctos y, si lo son, lo añado
		 * a la lista de inmuebles*/
		try {
			Inmueble aux = new Inmueble(linea);
			// miro si hay otro
			Inmueble otro = buscaInmueble(aux.getCodInmueble());
			// saco el empleado, que tiene que ser válido
			Empleado e = buscaEmpleado(aux.getCodEmpleado());
			// inmueblo otro nulo pero e no nulo
			if (otro==null && e!=null) {
				listaInmuebles.add(aux);
			}
		}
		catch (LineaMalFormadaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**Recibe una cadena de caracteres con los datos 
	 * asociados a un nuevo empleado, verificar que no 
	 * existe otro empleado con el mismo código*/
	public void insertarEmpleado(String linea) {
		
		try {
			Empleado e = new Empleado(linea);
			Empleado otro = buscaEmpleado(e.getCodEmpleado());
			if (otro==null) { // si no hay otro, se guarda
				listaEmpleados.add(e);
			}
		} 
		catch (LineaMalFormadaException e) {
			e.printStackTrace();
		}
		
	}

	/**Eliminar los datos almacenados en la base de datos 
	 * correspondiente a un inmueble cuyo código se recibe 
	 * como parámetro.
	Retorna true si se borra con éxito el inmueble*/
	public boolean borrarInmueble(String codInmueble) {
		
		Inmueble inm = buscaInmueble(codInmueble);
		if (inm != null) {
			listaInmuebles.remove(inm);
			return true;
		}
		return false;
	}

	public int guardarDatos(String nombreFicheroEmpelados, String nombreFicheroinmuebles) {
		
		int empleadosGuardados = PersistenciaCSV.getInstancia().guardarEmpleado(nombreFicheroEmpelados, listaEmpleados);
		int inmueblesGuardados = PersistenciaCSV.getInstancia().guardarInmueble(nombreFicheroinmuebles, listaInmuebles);
		return empleadosGuardados + inmueblesGuardados;
		
	}

	public String listadoInmuebles() {
		
		String cadena = "";
		for (Inmueble inm : listaInmuebles) {
			cadena += inm.toString() + "\n";
		}
		return cadena;
	}

}
