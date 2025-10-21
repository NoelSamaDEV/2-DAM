package EjemploSelect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class consultaLibros {

	public static void main(String[] args) {
		String driver= "com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/biblioteca";
		String user="root";
		String password="Samadegrao1";
		String consultaSQL= "select * from libros";
		
		ResultSet rs = null;
		Connection cn = null;
		Statement st = null;
		try {
			Class.forName(driver);
			cn=DriverManager.getConnection(url, user, password);
			st=cn.createStatement();
			rs=st.executeQuery(consultaSQL);
			System.out.println("Lista de Libros");
			while(rs.next()) {
				System.out.println("nRegistro "+rs.getInt(1));
				System.out.println("titulo "+ rs.getString(2));
				System.out.println("autor " + rs.getString(3));
				System.out.println("signatura " + rs.getString(4));
				System.out.println("*****************************");
				
			}
			rs.close();
			st.close();
			cn.close();
		} catch (Exception e) {
			//System.out.println("Estado "+e.getSQLState());
			//System.out.println("Codigo error "+ e.getErrorCode());
			System.out.println("Mensaje"+ e.getMessage());
		}
	}

}
