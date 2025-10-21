package TiendaInformatica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AppTiendaInformatica {
	
	private static Connection cn=null;
	private static PreparedStatement ps = null;
	
	private static int buscarPorFabricante(int fabricante) {
		String sql="Select * from articulo where fabricante=?";
		ResultSet rs = null;
		
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, fabricante);
			rs = ps.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
				double precio = rs.getDouble("precio");
				int fab = rs.getInt("fabricante");
				Articulo art = new Articulo(codigo, nombre, precio, fab);
				System.out.println(art.toString());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} 
		return 0;
	}
	public static void main(String[] args) {
		int opcion=0;
		Scanner sc = new Scanner(System.in);
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
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
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
