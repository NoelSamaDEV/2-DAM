package TiendaInformatica;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AppTiendaInformatica {
	
	private static Connection cn=null;
	private static PreparedStatement ps = null;
	
	private static int buscarPorFabricante(int fabricante) {
		String sql="Select * from articulo where fabricante=?";
		ResultSet rs = null;
		int contador=0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, fabricante);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				//gestionamos el resltSet
				Articulo art = new Articulo(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("fabricante"));
				System.out.println("**********************");
				System.out.println(art.toString());
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
	
	private static boolean insertarArticulo(Articulo art) {
		String sql="Insert into articulo values (?,?,?,?)";
		int resultado=0;
		try {
			ps= cn.prepareStatement(sql);
			ps.setInt(1, art.getCodigo());
			ps.setString(2, art.getNombre());
			ps.setDouble(3, art.getPrecio());
			ps.setInt(4, art.getFabricante());
			resultado= ps.executeUpdate();
			
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
	
	private static int borrarArticuloPorPrecio(String sql) {
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
	
	public static int incrementarPrecioFabricante(double incremento, int fabricante) {
		String sql = "Update articulo set precio=precio+precio*? where fabricante=?";
		int nRegistros = 0;
		try {
			ps = cn.prepareStatement(sql);
			ps.setDouble(1, incremento);
			ps.setInt(2, fabricante);
			nRegistros = ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return nRegistros;
		
	}
	
	public static boolean copiaSeguridadTexto() {
		String sql = "Select * from articulo";
		Statement st = null;
		FileWriter fw = null;
		ResultSet rs = null;
		Articulo art;
		Boolean realizado = false;
		
		try {
			fw = new FileWriter("CopiaSeguridadArticulos.txt");
			fw.write("Codigo;Nombre;Precio;Fabricante\n");
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				realizado = true;
				art = new Articulo(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("fabricante"));
				fw.write(art.toString()+"\n");
			}
			fw.write("Fin de la copia de Seguridad");
			rs.close();
			st.close();
			fw.close();
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public static void main(String[] args) {
		int opcion=0;
		Scanner sc = new Scanner(System.in);
		Articulo art=null;
		try {
			AccesoDatos ac = new AccesoDatos("TiendaDam");
			cn = ac.getConnection();
			do {
				System.out.println("Introduzca la opcion a realizar: "
						+ "\n1.- Buscar Articulo por Fabricante"
						+ "\n2.- Insertar Articulo"
						+ "\n3.- Borrar Articulo por Precio"
						+ "\n4.- Actualizar Precio por Fabricante"
						+ "\n5.- Copia de Seguridad en fichero texto"
						+ "\n0.- Salir");
				opcion = sc.nextInt();
				switch (opcion) {
				case 1:
					System.out.println("Introduzca el codigo de fabricante a buscar:");
					int fabricante = sc.nextInt();
					System.out.println("Se han mostrado "+ buscarPorFabricante(fabricante) + " articulos." );
					break;
				case 2:
					//pediremos los datos
					art = new Articulo(2,"monitor", 150.0, 3);
					if(insertarArticulo(art)) {
						System.out.println("Articulo insertado correctamente");
					} else {
						System.out.println("Error al insertar el articulo");
					}
					break;
				case 3:
					System.out.println("Introduzca el precio maximo para borrar articulos:");
					double precio = sc.nextDouble();
					String sql="Delete from articulo where precio>" + precio;
					System.out.println("Se han borrado " + borrarArticuloPorPrecio(sql)+ " registros.");
					break;
				case 4:
					System.out.println("Introduzca el incremento de precio");
					double incremento = sc.nextDouble();
					System.out.println("Introduzca el codigo de fabricante");
					int fab = sc.nextInt();
					System.out.println("Se han actualizado " + incrementarPrecioFabricante(incremento, fab) + " registros.");
					break;
				case 5:
					if (copiaSeguridadTexto()) {
						System.out.println("Copia de seguridad realizada correctamente");
					} else {
						System.out.println("Error al realizar la copia de seguridad");
					}
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

}
