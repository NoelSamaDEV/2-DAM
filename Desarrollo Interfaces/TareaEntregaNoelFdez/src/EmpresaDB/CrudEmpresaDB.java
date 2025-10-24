package EmpresaDB;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CrudEmpresaDB {

	private static Connection cn = null;
	private static PreparedStatement ps = null;

	public static void main(String[] args) {
		int opcion = 0;
		Scanner sc = new Scanner(System.in);
		Empleado emp = null;
		try {
			AccesoDatos ac = new AccesoDatos("EmpresaDB");
			cn = ac.getConnection();
			do {
				System.out.println("Introduzca la opcion a realizar: "
						+ "\n1.- Insertar Empleado"
						+ "\n2.- Buscar Empleado por salario" 
						+ "\n3.- Borrar Empleados Departamento"
						+ "\n4.- Actualizar Salarios" 
						+ "\n5.- Copia de Seguridad en fichero texto" 
						+ "\n0.- Salir");
				opcion = sc.nextInt();
				switch (opcion) {
				// Insertar Empleado
				case 1:
					int codigo = obtenerSiguienteCodigo(cn);
                    sc.nextLine();
					System.out.println("Nombre: ");
					String nombre = sc.nextLine();
					System.out.println("Apellidos: ");
					String apellidos = sc.nextLine();
					System.out.println("Salario: ");
					double salario = sc.nextDouble();
					System.out.println("Numero de Hijos: ");
					int hijos = sc.nextInt();
					System.out.println("Departamento: ");
					int departamento = sc.nextInt();
					

                    emp = new Empleado(codigo, nombre, apellidos, salario, hijos, departamento);
                    boolean insertado = insertarEmpleado(emp);
					if (insertado) {
						System.out.println("Empleado insertado correctamente.");
					} else {
						System.out.println("Error al insertar el empleado.");
					}
					break;
				// Buscar Empleado por salario	
				case 2:
                    System.out.println("Salario Minimo: ");
                    double salMin = sc.nextDouble();
                    System.out.println("Salario Maximo: ");
                    double salMax = sc.nextDouble();
                    int encontrados = buscarEmpleadoSalario(salMin, salMax);
                    System.out.println("Se han encontrado " + encontrados + " empleados.");
					break;
			    // Borrar Empleados Departamento
				case 3:
                    System.out.println("Introduzca el departamento a borrar:");
                    int depto = sc.nextInt();
                    String sql="DELETE FROM empleado WHERE departamento=" + depto;
                    int borrados = borrarEmpleadosDepartamento(depto, sql);
                    System.out.println("Se han borrado " + borrados + " registros.");
					break;
				// Actualizar Salarios
				case 4:
					int actualizados = actualizarSalarios();
					System.out.println("Se han actualizado " + actualizados + " registros.");
					break;
				// Copia de Seguridad en fichero texto
				case 5:
					if (realizarCopiaSeguridad("CopiaSeguridadEmpleados.txt")) {
						System.out.println("Copia de seguridad realizada correctamente.");
					} else {
						System.out.println("Error al realizar la copia de seguridad.");
					}
					break;
				// Salir
				case 0:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opcion no valida");
				}
			} while (opcion != 0);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static boolean realizarCopiaSeguridad(String nombreArchivo) {
	    String sql = "SELECT * FROM empleado";
	    AccesoDatos ac = new AccesoDatos("EmpresaDB");
	    Statement st = null;
	    ResultSet rs = null;
	    FileWriter fw = null;
	    boolean realizado = false;

	    try {
	        // Obtener conexión
	        Connection cn = ac.getConnection();

	        // Crear el escritor de archivo
	        fw = new FileWriter("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM\\2-DAM\\Desarrollo Interfaces\\TareaEntregaNoelFdez\\src\\EmpresaDB\\" + nombreArchivo);

	        // Encabezado coherente con los campos de Empleado
	        fw.write("Codigo;Nombre;Apellidos;Salario;Hijos;Departamento\n");

	        // Ejecutar consulta
	        st = cn.createStatement();
	        rs = st.executeQuery(sql);

	        // Escribir cada empleado
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

	/**
	 * Metodo para obtener el siguiente código de empleado
	 * @param cn
	 * @return siguiente código
	 */
	private static int obtenerSiguienteCodigo(Connection cn) {
		int siguiente = 1;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(codigo) FROM empleado");
			if (rs.next()) {
				siguiente = rs.getInt(1) + 1;
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Error al obtener siguiente código: " + e.getMessage());
		}
		return siguiente;
	}
	
	/**
	 * Metodo para insertar un empleado en la base de datos
	 * @param emp
	 * @return boolean
	 */
	private static boolean insertarEmpleado(Empleado emp) {
		String sql = "INSERT INTO empleado VALUES (?, ?, ?, ?, ?, ?)";
		int resultado = 0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, emp.getCodigo());
			ps.setString(2, emp.getNombre());
			ps.setString(3, emp.getApellidos());
			ps.setDouble(4, emp.getSalario());
			ps.setInt(5, emp.getHijos());
			ps.setInt(6, emp.getDepartamento());
			resultado= ps.executeUpdate();
			
			ps.close();
			
			if (resultado == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Error al insertar empleado: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Metodo para buscar empleados por salario
	 * @param salMin
	 * @param salMax
	 * @return contador
	 */
	private static int buscarEmpleadoSalario(double salMin, double salMax) {
		String sql = "SELECT * FROM empleado WHERE salario BETWEEN ? AND ?";
		ResultSet rs = null;
		int contador=0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setDouble(1, salMin);
			ps.setDouble(2, salMax);
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
	
	/**
	 * Metodo para borrar empleados de un departamento
	 * 
	 * @param departamento
	 * @param sql
	 * @return nRegistros
	 */
	private static int borrarEmpleadosDepartamento(int departamento, String sql) {
		Statement st = null;
		int nRegistros=0;
		try {
			st = cn.createStatement();
			nRegistros = st.executeUpdate(sql);
			st.close();	
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nRegistros;
	}
	
	/**
	 * Metodo para actualizar los salarios de los empleados
	 * 
	 * @param porcentaje
	 * @return nRegistros
	 */
	private static int actualizarSalarios() {
		String Sql1 = "SELECT codigo, salario, hijos FROM empleado";
	    String Sql2 = "UPDATE empleado SET salario = ? WHERE codigo = ?";
	    int nRegistros = 0;

	    try {
	        Statement st = cn.createStatement();
	        ResultSet rs = st.executeQuery(Sql1);

	        while (rs.next()) {
	            int codigo = rs.getInt("codigo");
	            double salario = rs.getDouble("salario");
	            int hijos = rs.getInt("hijos");

	            // Calculamos el nuevo salario según tenga hijos o no
	            if (hijos >= 1) {
	                salario *= 1.05; 
	            } else {
	                salario *= 1.03;  
	            }
	            // Actualizamos el salario del empleado
	            PreparedStatement ps = cn.prepareStatement(Sql2);
	            ps.setDouble(1, salario);
	            ps.setInt(2, codigo);
	            nRegistros += ps.executeUpdate();
	            ps.close();
	        }

	        rs.close();
	        st.close();

	    } catch (Exception e) {
	        System.out.println("Error al actualizar salarios: " + e.getMessage());
	    }

	    return nRegistros;
}
}
