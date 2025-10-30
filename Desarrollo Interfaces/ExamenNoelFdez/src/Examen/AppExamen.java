package Examen;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AppExamen {

	private static Connection cn = null;
	private static PreparedStatement ps = null;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		String dni;
		String nombre;
		String apellidos;
		int departamento;

		try {
			AccesoDatos ac = new AccesoDatos("EmpresaExamen");
			cn = ac.getConnection();
			do {
				System.out.println("Introduzca la opcion a realizar: "
						+ "\n1.- Buscar empleado por DNI"
						+ "\n2.- Insertar empleado"
						+ "\n3.- Buscar departamentos"
						+ "\n4.- Modificar presupuesto"
						+ "\n5.- Borrar empleado"
						+ "\n6.- Devolver departamento con mayor presupuesto en Fichero");
				opcion = sc.nextInt();
				switch (opcion) {
				case 1:
					System.out.println("Introduzca el DNI a buscar");
					dni = sc.next();
					buscarEmpleadoDNI(dni);
					break;
				case 2:
					System.out.println("Introduzca el DNI: ");
					dni= sc.next();
					System.out.println("Introduce nombre: ");
					nombre = sc.next();
					System.out.println("Introduce apellidos: ");
					apellidos= sc.next();
					System.out.println("Introduce departamento:" );
					departamento=sc.nextInt();
					insertarEmpleado(dni, nombre, apellidos, departamento);
					System.out.println("Empleado insertado correctamente");
					break;
				case 3:
					System.out.println("Introduzce el presupuesto maximo a buscar: ");
					int presupuesto= sc.nextInt();
					boolean encontrado = buscarDepartamentoPresupuesto(presupuesto);
					if (!encontrado) {
						System.out.println("No se han encontrado departamentos con presupuesto menor a " + presupuesto);
					} else {
						System.out.println("Departamentos mostrados correctamente");
					}
					break;
				case 4:
					System.out.println("Introduzce codigo departamento a modificar: ");
					int codigo= sc.nextInt();
					System.out.println("Introduzce nuevo presupuesto: ");
					int nuevoPresupuesto= sc.nextInt();
					if (modificarPresupuestoDepartamento(codigo, nuevoPresupuesto) > 0) {
						System.out.println("Presupuesto modificado correctamente");
					} else {
						System.out.println("No se han modificado registros");
					}
					break;
				case 5:

					break;
				case 6:
					if (realizarCopiaPresupuestoMayor()) {
						System.out.println("Copia realizada correctamente");
					} else {
						System.out.println("No se ha podido realizar la copia");
					}
					break;
				case 0:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opcion no valida");
				}
			} while (opcion != 0);
			cn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			sc.close();
		}
	}

	/**
	 * Metodo que pedira un dni y mostrara por consola todos los datos del empleado
	 * 
	 */
	public static void buscarEmpleadoDNI(String dni) {
		String sql = "SELECT * FROM empleado WHERE dni=?";
		ResultSet rs = null;
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, dni);
			rs = ps.executeQuery();
			if (rs.next()) {
				Empleado emp = new Empleado(rs.getString("DNI"), rs.getString("nombre"), rs.getString("apellidos"),rs.getInt("departamento"));
				System.out.println("**********************");
				System.out.println(emp.toString());
				System.out.println("**********************");
			} else {
				System.out.println("El DNI introducido no esta en la base de datos");
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Metodo que insertara un empleado en la base de datos
	 * @return
	 */
	public static int insertarEmpleado(String dni, String nombre, String apellidos, int departamento) {
		String sql= "INSERT INTO empleado (DNI, nombre, apellidos, departamento) VALUES (?,?,?,?)";
		int nRegistros=0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, dni);
			ps.setString(2, nombre);
			ps.setString(3, apellidos);
			ps.setInt(4, departamento);
			nRegistros = ps.executeUpdate();
			ps.close();
			return nRegistros;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nRegistros;
	}
	
	/**
	 * Metodo que busca un departamento por su presupuesto, mostrarlo en consola y almacenarlo en un archivo menorpresupuesto.txt
	 */
	public static boolean buscarDepartamentoPresupuesto(int presupuesto) {
		String sql = "SELECT * FROM departamento WHERE presupuesto<?";
		ResultSet rs = null;
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, presupuesto);
			rs = ps.executeQuery();
			boolean encontrado = false;
			while (rs.next()) {
				Departamento dep = new Departamento(rs.getInt("codigo"), rs.getString("nombre"),
						rs.getInt("presupuesto"));
				System.out.println("**********************");
				System.out.println(dep.toString());
				System.out.println("**********************");
				encontrado = true;
			}
			ps.close();
			rs.close();
			return encontrado;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Metodo para modificar el presupuesto de un departamento
	 */
	public static int modificarPresupuestoDepartamento(int codigo, int nuevoPresupuesto) {
		String sql = "UPDATE departamento SET presupuesto=? WHERE codigo=?";
		int nRegistros = 0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, nuevoPresupuesto);
			ps.setInt(2, codigo);
			nRegistros = ps.executeUpdate();
			ps.close();
			return nRegistros;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nRegistros;
	}
	
	/**
	 * Metodo que alamcena los datos del departamento con menor presupuesto en un txt
	 */
	private static boolean realizarCopiaPresupuestoMayor() {
	    String sql = "SELECT * FROM departamento ORDER BY presupuesto DESC LIMIT 1";
	    AccesoDatos ac = new AccesoDatos("EmpresaExamen");
	    Statement st = null;
	    ResultSet rs = null;
	    FileWriter fw = null;
	    boolean realizado = false;
	    
		try {
			cn = ac.getConnection();
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				Departamento dep = new Departamento(rs.getInt("codigo"), rs.getString("nombre"),
						rs.getInt("presupuesto"));
				fw = new FileWriter("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM\\2-DAM\\Desarrollo Interfaces\\ExamenNoelFdez\\src\\Examen\\departamentomayorpresupuesto.txt");
				fw.write(dep.toString());
				realizado = true;
			}
			rs.close();
			st.close();
			fw.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return realizado;
	    
	    
	    
	    
	    
	}
}
