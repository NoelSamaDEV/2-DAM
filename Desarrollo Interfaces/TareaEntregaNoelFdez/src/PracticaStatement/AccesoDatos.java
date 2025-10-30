package PracticaStatement;

import java.sql.Connection;
import java.sql.DriverManager;

public class AccesoDatos {
	private String nameBbdd;
	String driver = "com.mysql.cj.jdbc.Driver";	
	String url = "jdbc:mysql://localhost:3306/";
	String user	= "root";
	String pass = "Samadegrao1";
	
	public AccesoDatos(String nBbdd) {
        this.nameBbdd = nBbdd;
    }
	
	
	public Connection getConnection() {
		Connection cn = null;
		
		try {
			Class.forName(driver);
			cn = DriverManager.getConnection(url+nameBbdd, user, pass);
		
		} catch (Exception e) {
			System.out.println("No se ha establecido la conexion " +"\n"+ e.getMessage());
		} finally {
			return cn;
		}
	}
}

