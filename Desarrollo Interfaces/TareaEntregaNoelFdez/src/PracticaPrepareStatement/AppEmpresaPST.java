package PracticaPrepareStatement;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import EmpresaDB.AccesoDatos;
import EmpresaDB.Empleado;

public class AppEmpresaPST {

	private static Connection cn = null;
	private static PreparedStatement ps = null;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int opcion = 0;

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
					int numDep = sc.nextInt();
					int nEmpleados = buscarEmpleados(numDep);
					System.out.println("Numero de empleados en el departamento " + numDep + ": " + nEmpleados);
					break;
				case 2:
					System.out.println("Insertar nuevo empleado:");
					// Pedir datos del empleado
					System.out.println("Codigo:");
					int codigo = sc.nextInt();
					System.out.println("Nombre:");
					String nombre = sc.next();
					System.out.println("Apellidos:");
					String apellidos = sc.next();
					System.out.println("Salario:");
					double salario = sc.nextDouble();
					System.out.println("Hijos:");
					int hijos = sc.nextInt();
					System.out.println("Departamento:");
					int departamento = sc.nextInt();
					Empleado emp = new Empleado(codigo, nombre, apellidos, salario, hijos, departamento);
					boolean insertado = insertarEmpleado(emp);
					if (insertado) {
						System.out.println("Empleado insertado correctamente.");
					} else {
						System.out.println("Error al insertar el empleado.");
					}
					break;
				case 3:
					System.out.println("Introduzca el nombre del departamento para borrar empleados:");
					String nombreDep = sc.next();
					int nBorrados = borrarEmpleadoPorDepartamento(nombreDep);
					System.out.println("Se han borrado " + nBorrados + " empleados del departamento " + nombreDep + ".");
					break;
				case 4:
                    System.out.println("Introduzca el codigo del empleado para actualizar su salario:");
                    int codEmp = sc.nextInt();
                    System.out.println("Introduzca el nuevo salario:");
                    double nuevoSalario = sc.nextDouble();
                    boolean actualizado = actualizarSalarioPorCodigo(codEmp, nuevoSalario);
					if (actualizado) {
						System.out.println("Salario actualizado correctamente.");
					} else {
						System.out.println("Error al actualizar el salario.");
					}
					break;
				case 5:
					System.out.println("Introduzca el nombre del archivo para la copia de seguridad:");
					String nombreArchivo = sc.next();
					boolean realizado = realizarCopiaSeguridad(nombreArchivo);
					if (realizado) {
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
			} while (opcion != 0);

			cn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			sc.close();
		}

	}

	private static int buscarEmpleados(int numDep) {
		String sql = "SELECT * FROM empleado WHERE departamento=?";
		ResultSet rs = null;
		int contador = 0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, numDep);
			rs = ps.executeQuery();
			while (rs.next()) {
				Empleado emp = new Empleado(rs.getInt("codigo"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getDouble("salario"), rs.getInt("hijos"), rs.getInt("departamento"));
				System.out.println("**********************");
				System.out.println(emp.toString());
				System.out.println("**********************");
				contador++;
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return contador;
	}

	private static boolean insertarEmpleado(Empleado emp) {
		String sql = "INSERT INTO empleado (codigo, nombre, apellidos, salario, hijos, departamento) VALUES (?, ?, ?, ?, ?, ?)";
		int resultado = 0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, emp.getCodigo());
			ps.setString(2, emp.getNombre());
			ps.setString(3, emp.getApellidos());
			ps.setDouble(4, emp.getSalario());
			ps.setInt(5, emp.getHijos());
			ps.setInt(6, emp.getDepartamento());
			resultado = ps.executeUpdate();
			ps.close();
			if (resultado == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}

	private static int borrarEmpleadoPorDepartamento(String nombreDep) {
		PreparedStatement ps = null;
		int nRegistros = 0;
		try {
			String sql = "DELETE FROM empleado WHERE departamento = (SELECT codigo FROM departamento WHERE nombre = ?)";
			ps = cn.prepareStatement(sql);
			ps.setString(1, nombreDep);
			nRegistros = ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nRegistros;
	}
	
	private static boolean actualizarSalarioPorCodigo(int codigo, double nuevoSalario) {
		PreparedStatement ps = null;
		boolean actualizado = false;
		try {
			String sql = "UPDATE empleado SET salario = ? WHERE codigo = ?";
			ps = cn.prepareStatement(sql);
			ps.setDouble(1, nuevoSalario);
			ps.setInt(2, codigo);
			int nRegistros = ps.executeUpdate();
			if (nRegistros > 0) {
				actualizado = true;
			}
			ps.close();
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
	        fw = new FileWriter("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM\\2-DAM\\Desarrollo Interfaces\\TareaEntregaNoelFdez\\src\\PracticaPrepareStatement\\" + nombreArchivo);
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