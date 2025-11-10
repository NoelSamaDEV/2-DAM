package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import LogicaNegocio.Empleado;
import LogicaNegocio.Inmueble;
import excepciones.LineaMalFormadaException;

public class PersistenciaCSV implements Persistencia {
	
	private static PersistenciaCSV instancia;
	
	public static PersistenciaCSV getInstancia() {
		if (instancia==null) {
			instancia = new PersistenciaCSV();
		}
		return instancia;
	}
	
	private PersistenciaCSV() {}

	@Override
	public <T extends Documentable> List<T> 
	leerDocumentables(String nombreFichero,
			Constructor<String, T, 
			LineaMalFormadaException> constructor) {
		List<T> lista = new LinkedList<>();
		// leo el fichero y hay un método que convierte líneas
		// en objetos
		BufferedReader br = null;
		try {
			br = new BufferedReader(
					new FileReader(
							new File(nombreFichero)));
			// paso de las cabeceras
			br.readLine();
			// leo línea
			String linea = br.readLine();
			while(linea!=null) {
				// hago un objeto con la línea
				T objeto = parseaLíneaDocumentable(linea, 
						constructor);
				if (objeto!=null) {
					lista.add(objeto);
				}
				linea = br.readLine();
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br!=null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lista; 
	}

	@Override
	public <T extends Documentable> void 
	escribirDocumentables(String nombreFichero, 
			List<T> lista, String cabecera) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = 
					new BufferedWriter(
							new FileWriter(
									new File(nombreFichero)
									)
							);
			// primero cabecera + intro
			bufferedWriter.write(cabecera+"\n");
			// paso por la lista y vuelco datos
			for(T elemento: lista) {
				bufferedWriter.write(elemento.marshalling()+"\n");
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (bufferedWriter!=null) {
				try {
					bufferedWriter.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**Lee el fichero y carga los datos en la lista*/
	public void leerEmpleado(String nombreFichero,
			List<Empleado> listaEmpleados) {
		/*leerDocumentables(String nombreFichero,
			Constructor<String, T, 
			LineaMalFormadaException> constructor)*/
		List<Empleado> aux = 
				leerDocumentables(nombreFichero, 
						Empleado::new);
		// Constructor: espera una cadena y devuelve un empleado
		// s->return new Empleado(s)
		
		// cargo datos
		listaEmpleados.addAll(aux); 
	}

	public void leerInmueble(String nombreFichero,
			List<Inmueble> listaInmuebles,
			List<Empleado> listaEmpleados) {
		// cada inmueble tiene que ser con un codEmpleado existente
		List<Inmueble> aux = 
				leerDocumentables(nombreFichero, 
						s -> new Inmueble(s) // Inmueble::new
								);
		/* recorro la lista aux y los elementos
		 * cuyo codEmpleado sea válido los guardo*/
		 for(Inmueble inmueble: aux) {
			 /* tengo que mirar si el codEmpleado pertenece
			  * a alguno de los empleados de listaEmpleados*/
			 /*boolean encontrado = false;
			  * paso por todos los empleados y si el código
			  * coincide con alguno: encontrado = true
			  * if (encontrador) listaInmuebles.add(inmueble);*/
			 boolean valido = false;
			 for(Empleado e: listaEmpleados) {
				 // miro si el código es de alguno
				 if (e.getCodEmpleado()
						 .compareTo(inmueble.getCodEmpleado())==0) {
					 valido=true;
				 }
			 }
			 if (valido) {
				 listaInmuebles.add(inmueble);
			 }
		 }
	}
	
	public void escribirInmuebles(String nombreFichero,
			List<Inmueble> listaInmuebles) {
		escribirDocumentables(
				nombreFichero
				,listaInmuebles
				,Inmueble.CABECERA);
	}
	
	public void escribirEmpleados(String nombreFichero,
			List<Empleado> listaEmpleados) {
		escribirDocumentables(
				nombreFichero
				,listaEmpleados
				,Empleado.CABECERA);
	}

	public int guardarEmpleado(String nombreFicheroEmpelados, List<Empleado> listaEmpleados) {
		
		int guardados = 0;
		// escribo empleados
		escribirEmpleados(nombreFicheroEmpelados, listaEmpleados);
		guardados = listaEmpleados.size();
		return guardados;
	}

	public int guardarInmueble(String nombreFicheroinmuebles, List<Inmueble> listaInmuebles) {
		
		int guardados = 0;
		// escribo inmuebles
		escribirInmuebles(nombreFicheroinmuebles, listaInmuebles);
		guardados = listaInmuebles.size();
		return guardados;
	}
	
}
