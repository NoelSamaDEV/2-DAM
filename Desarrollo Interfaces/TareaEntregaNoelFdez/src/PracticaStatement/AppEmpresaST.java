package PracticaStatement;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import EmpresaDB.AccesoDatos;
import EmpresaDB.Empleado;

public class AppEmpresaST {
	private static Connection cn=null;
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int opcion=0;
		
		try {
			AccesoDatos ac = new AccesoDatos("EmpresaDB");
			cn = ac.getConnection();
			do {
				System.out.println("Introduzca la opcion a realizar: "
						+ "\n1.- Buscar empleados por Departamento"
						+ "\n2.- Insertar empleado"
						+ "\n3.- Borrar empleados departamento por nombre"
						+ "\n4.- Actualizar salario empleado por codigo"
						+ "\n5.- Copia de Seguridad en fichero texto"
						+ "\n6.- Actualizacion desde ResulSet"
						+ "\n7.- Insertar desde ResulSet"
						+ "\n8.- Eliminar desde ResulSet"
						+ "\n0.- Salir");
				opcion = sc.nextInt();
				switch (opcion) {
				case 1:
					System.out.println("Introduce el numero de departamento a buscar:");
					int numDep=sc.nextInt();
					String case1="SELECT * FROM empleado WHERE departamento="+numDep;
					int nEmpleados=buscarEmpleado(case1);
					System.out.println("Numero de empleados en el departamento "+numDep+": "+nEmpleados);
					break;
				case 2:
					System.out.println("Insertar nuevo empleado:");
					// Pedir datos del empleado
					System.out.println("Codigo:");
					int codigo=sc.nextInt();
					System.out.println("Nombre:");
					String nombre=sc.next();
					System.out.println("Apellidos:");
					String apellidos=sc.next();
					System.out.println("Salario:");
					double salario=sc.nextDouble();
					System.out.println("Hijos:");
					int hijos=sc.nextInt();
					System.out.println("Departamento:");
					int departamento=sc.nextInt();
					String case2 = "INSERT INTO empleado (codigo, nombre, apellidos, salario, hijos, departamento) "
							+ "VALUES (" + codigo + ", '" + nombre + "', '" + apellidos + "', " + salario + ", " + hijos
							+ ", " + departamento + ")";
					boolean insertado = insertarEmpleado(case2);
					if (insertado) {
						System.out.println("Empleado insertado correctamente.");
					} else {
						System.out.println("Error al insertar el empleado.");
					}
					break;
				case 3:
					System.out.println("Introduce el nombre del departamento a borrar:");
					String nombreDep=sc.next();
					String case3="DELETE FROM empleado WHERE departamento=(SELECT codigo FROM departamento WHERE nombre='"+nombreDep+"')";
					int empleadosBorrados=borrarDepartamento(case3);
					System.out.println("Numero de empleados borrados en el departamento "+nombreDep+": "+empleadosBorrados);
					break;
				case 4:
					System.out.println("Introduce el codigo del empleado para actualizar su salario:");
					int codEmp=sc.nextInt();
					System.out.println("Introduce el nuevo salario:");
					double nuevoSalario=sc.nextDouble();
					String case4="UPDATE empleado SET salario="+nuevoSalario+" WHERE codigo="+codEmp;
					boolean actualizado=actualizarSalario(case4);
					if (actualizado) {
						System.out.println("Salario actualizado correctamente.");
					} else {
						System.out.println("Error al actualizar el salario.");
					}
					break;
				case 5:
					System.out.println("Introduce el nombre del archivo para la copia de seguridad:");
					String nombreArchivo=sc.next();
					if (realizarCopiaSeguridad(nombreArchivo)) {
						System.out.println("Copia de seguridad realizada correctamente.");
					} else {
						System.out.println("Error al realizar la copia de seguridad.");
					}
					break;
				case 6:
					System.out.println("Actualizando salarios desde ResultSet...");
					actualizarResulSet();
					System.out.println("Salarios actualizados correctamente.");
					break;
				case 7:
					System.out.println("Insertando nuevo empleado desde ResultSet...");
					insertarResulSet();
					System.out.println("Empleado insertado correctamente desde ResultSet.");
					break;
				case 8:
					System.out.println("Eliminando empleados sin hijos desde ResultSet...");
					eliminarResulSet();
					System.out.println("Empleados eliminados correctamente desde ResultSet.");
					break;
				case 0:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opcion no valida");
				}
			}while(opcion!=0);
			
			cn.close();
		} catch (Exception e) {
		    System.out.println("Error: " + e.getMessage());
		}finally {

		}
		
	}
	
	public static int buscarEmpleado(String sql) {
		ResultSet rs = null;
		Statement st = null;
		int contador = 0;
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Empleado emp = new Empleado(rs.getInt("codigo"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getDouble("salario"), rs.getInt("hijos"), rs.getInt("departamento"));
				System.out.println(emp.toString());
				contador++;
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return contador;
	}
	
	public static boolean insertarEmpleado(String sql) {
		Statement st = null;
		boolean insertado = false;
		try {
			st = cn.createStatement();
			int nResgistros = st.executeUpdate(sql);
			if (nResgistros > 0) {
				insertado = true;
			}
			st.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return insertado;
	}
	
	public static int borrarDepartamento(String sql) {
		Statement st = null;
		int nResgistros = 0;
		try {
			st = cn.createStatement();
			nResgistros = st.executeUpdate(sql);
			st.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nResgistros;
	}
	
	public static boolean actualizarSalario(String sql) {
		Statement st = null;
		boolean actualizado = false;
		try {
			st = cn.createStatement();
			int nResgistros = st.executeUpdate(sql);
			if (nResgistros > 0) {
				actualizado = true;
			}
			st.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return actualizado;
	}
	
	private static boolean realizarCopiaSeguridad(String nombreArchivo) {
	    String sql = "SELECT * FROM empleado";
	    AccesoDatos ac = new AccesoDatos("EmpresaDB");
	    Statement st = null;
	    ResultSet rs = null;
	    FileWriter fw = null;
	    boolean realizado = false;

	    try {
	        Connection cn = ac.getConnection();
	        fw = new FileWriter("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM\\2-DAM\\Desarrollo Interfaces\\TareaEntregaNoelFdez\\src\\PracticaStatement\\" + nombreArchivo);
	        fw.write("Codigo;Nombre;Apellidos;Salario;Hijos;Departamento\n");
	        st = cn.createStatement();
	        rs = st.executeQuery(sql);
	        while (rs.next()) {
	            Empleado emp = new Empleado(
	                rs.getInt("codigo"),
	                rs.getString("nombre"),
	                rs.getString("apellidos"),
	                rs.getDouble("salario"),
	                rs.getInt("hijos"),
	                rs.getInt("departamento")
	            );
	            fw.write(emp.toString() + "\n");
	            realizado = true;
	        }
	        fw.write("Fin de la copia de seguridad.\n");
	        fw.close();
	        rs.close();
	        st.close();
	        cn.close();
	    } catch (Exception e) {
	        System.out.println("Error al realizar la copia de seguridad: " + e.getMessage());
	    }
	    return realizado;
	}
	
	public static void actualizarResulSet() {
        Statement st = null;
        ResultSet rs = null;
		try {
			st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery("SELECT * FROM empleado");

			while (rs.next()) {
				double salarioActual = rs.getDouble("salario");
				rs.updateDouble("salario", salarioActual * 1.10);
				rs.updateRow();
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Error al actualizar desde ResulSet: " + e.getMessage());
		}
	}
	
	public static void insertarResulSet() {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery("SELECT * FROM empleado");

			rs.moveToInsertRow();
			rs.updateInt("codigo", 101);
			rs.updateString("nombre", "Juan");
			rs.updateString("apellidos", "Pérez");
			rs.updateDouble("salario", 3000.0);
			rs.updateInt("hijos", 2);
			rs.updateInt("departamento", 1);
			rs.insertRow();

			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Error al insertar desde ResulSet: " + e.getMessage());
		}
	}
	
	public static void eliminarResulSet() {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = st.executeQuery("SELECT * FROM empleado");

			while (rs.next()) {
				if (rs.getInt("hijos") == 0) {
					rs.deleteRow();
				}
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Error al eliminar desde ResulSet: " + e.getMessage());
		}
	}
}
