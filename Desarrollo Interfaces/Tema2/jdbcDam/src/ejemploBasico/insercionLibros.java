package ejemploBasico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insercionLibros {

	public static void main(String[] args) {
		// declaracion de las variable y objetos
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/biblioteca";
		String user="root";
		String password="Samadegrao1";
		String sql="insert into libros (nRegistro, titulo,autor,signatura)"+"values(116,'Programacion jdbc', 'Angel Gonzalez','67.3-PET-lin')";
		Connection cn= null;
		Statement st =null;
		int numerosregistros;
		try {
			Class.forName(driver);
			cn=DriverManager.getConnection(url, user, password);
			st=cn.createStatement();
			numerosregistros = st.executeUpdate(sql);
			if(numerosregistros>0) {
				System.out.println("Se han insertado "+numerosregistros+ " registros");
			}else {
				System.out.println("No se ha insertado");
			}
			//System.out.println("fin");
			
			cn.close();
			st.close();
			
			
		} catch (Exception e) {
			System.out.println("Error "+e.getMessage());
			
			
		}

	}

}
