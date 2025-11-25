package AppGasolineras;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;

public class GestionEventos implements ActionListener {

	private VentanaPrincipal v;

	public GestionEventos(VentanaPrincipal v) {
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object o = e.getSource();

		if (o == v.btnInsertar) {
			leerYVolcarCSV();
		}

		else if (o == v.btnEjecutar) {
			ejecutarConsulta();
		}
	}

	private void leerYVolcarCSV() {

		String ruta = v.tfRutaCSV.getText();
		if (ruta.equals("")) {
			JOptionPane.showMessageDialog(v, "Debe introducir la ruta del CSV");
			return;
		}

		v.taInfo.setText("Leyendo y procesando CSV...\n");

		AccesoDatos ac = new AccesoDatos("estaciones_servicio");
		Connection cn = ac.getConnection();

		try {
			BufferedReader br = new BufferedReader(new FileReader(ruta));

			String linea = br.readLine();
			int contador = 0;

			while ((linea = br.readLine()) != null) {

				String[] trozos = linea.split(";");

				int id_empresa = Integer.parseInt(trozos[0]);
				String rotulo = trozos[1];

				int ideess = Integer.parseInt(trozos[2]);
				String direccion = trozos[3];
				String localidad = trozos[4];
				String provincia = trozos[5];
				String cp = trozos[6];
				String margen = trozos[7];
				String horario = trozos[8];

				String tipo = trozos[9].toUpperCase();

				double latitud = Double.parseDouble(trozos[10]);
				double longitud = Double.parseDouble(trozos[11]);

				double precioGas = trozos[12].isEmpty() ? 0 : Double.parseDouble(trozos[12]);
				double precioGasoil = trozos[13].isEmpty() ? 0 : Double.parseDouble(trozos[13]);

				// FECHA (DATETIME MYSQL)
				String fecha = trozos[14].replace("/", "-");
				if (fecha.length() == 16) { 
					fecha = fecha + ":00";
				}

				insertarEmpresa(cn, id_empresa, rotulo);
				insertarEstacion(cn, ideess, direccion, localidad, provincia, cp, margen, horario, tipo, latitud,
						longitud, id_empresa);
				insertarPrecio(cn, ideess, fecha, precioGas, precioGasoil);

				contador++;
			}

			br.close();
			v.taInfo.append("\nRegistros insertados: " + contador);

		} catch (Exception e) {
			v.taInfo.append("\nERROR leyendo CSV: " + e.getMessage());
		}
	}

	private void insertarEmpresa(Connection cn, int id, String rotulo) {
		try {
			String sql = "INSERT INTO empresa (id_empresa, rotulo) VALUES (?, ?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, rotulo);
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error insertando empresa: " + e.getMessage());
		}
	}

	private void insertarEstacion(Connection cn, int ideess, String direccion, String localidad, String provincia,
			String cp, String margen, String horario, String tipo, double latitud, double longitud, int id_empresa) {

		try {
			String sql = "INSERT INTO estacion_servicio "
					+ "(ideess, direccion, localidad, provincia, cp, margen, horario, tipo_estacion, latitud, longitud, id_empresa) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = cn.prepareStatement(sql);

			ps.setInt(1, ideess);
			ps.setString(2, direccion);
			ps.setString(3, localidad);
			ps.setString(4, provincia);
			ps.setString(5, cp);
			ps.setString(6, margen);
			ps.setString(7, horario);
			ps.setString(8, tipo);
			ps.setDouble(9, latitud);
			ps.setDouble(10, longitud);
			ps.setInt(11, id_empresa);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error insertando estación: " + e.getMessage());
		}
	}

	private void insertarPrecio(Connection cn, int ideess, String fecha, double gas, double gasoil) {

		try {
			String sql = "INSERT INTO precio_carburante (ideess, fecha, precio_gasolina, precio_gasoil) "
					+ "VALUES (?, ?, ?, ?)";

			PreparedStatement ps = cn.prepareStatement(sql);

			ps.setInt(1, ideess);
			ps.setString(2, fecha);
			ps.setDouble(3, gas);
			ps.setDouble(4, gasoil);

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error insertando precios: " + e.getMessage());
		}
	}

	private void ejecutarConsulta() {

	    int opcion = v.cbConsultas.getSelectedIndex();
	    v.taInfo.setText("");  

	    AccesoDatos ac = new AccesoDatos("estaciones_servicio");
	    Connection cn = ac.getConnection();

	    try {

	        Statement st = cn.createStatement();
	        ResultSet rs;
	        String salida = "";  

	        switch(opcion) {
	            case 1://Lista de gasolineras en Madrid con precio de gasolina más bajo
	                String sql1 =
	                    "SELECT e.ideess, e.direccion, e.localidad, p.precio_gasolina " +
	                    "FROM estacion_servicio e " +
	                    "JOIN precio_carburante p ON e.ideess = p.ideess " +
	                    "WHERE e.provincia = 'Madrid' " +
	                    "ORDER BY p.precio_gasolina ASC";

	                rs = st.executeQuery(sql1);

	                salida += "ID\tDirección\tLocalidad\tPrecio (€)\n";
	                salida += "------------------------------------------------------\n";

	                while (rs.next()) {
	                    salida +=
	                        rs.getInt("ideess") + "\t" +
	                        rs.getString("direccion") + "\t" +
	                        rs.getString("localidad") + "\t" +
	                        rs.getDouble("precio_gasolina") + "\n";
	                }

	                v.taInfo.setText(salida);
	                break;
	            case 2://Empresa con más estaciones marítimas
	                String sql2 =
	                    "SELECT emp.rotulo, COUNT(*) AS total " +
	                    "FROM empresa emp " +
	                    "JOIN estacion_servicio es ON emp.id_empresa = es.id_empresa " +
	                    "WHERE es.tipo_estacion = 'MARITIMA' " +
	                    "GROUP BY emp.id_empresa " +
	                    "ORDER BY total DESC LIMIT 1";

	                rs = st.executeQuery(sql2);

	                salida += "Empresa con más estaciones marítimas:\n\n";

	                if (rs.next()) {
	                    salida += "Empresa: " + rs.getString("rotulo") + "\n";
	                    salida += "Total estaciones: " + rs.getInt("total") + "\n";
	                } else {
	                    salida += "No hay estaciones marítimas.";
	                }

	                v.taInfo.setText(salida);
	                break;
	            case 3://Lista de estaciones de la provincia de Malaga

	            	 String sql3 =
	                    "SELECT e.ideess, e.direccion, e.localidad, e.provincia, emp.rotulo " +
	                    "FROM estacion_servicio e " +
	                    "JOIN empresa emp ON e.id_empresa = emp.id_empresa " +
	                    "WHERE e.provincia = 'Málaga' " +
	                    "ORDER BY e.localidad, e.direccion";

	                rs = st.executeQuery(sql3);

	                salida += "ID\tDirección\tLocalidad\tProvincia\tEmpresa\n";
	                salida += "---------------------------------------------------------------------\n";

	                while (rs.next()) {
	                    salida +=
	                        rs.getInt("ideess") + "\t" +
	                        rs.getString("direccion") + "\t" +
	                        rs.getString("localidad") + "\t" +
	                        rs.getString("provincia") + "\t" +
	                        rs.getString("rotulo") + "\n";
	                }

	                v.taInfo.setText(salida);
	                break;

	            case 4://Gasolinera con precio de gasolina más caro
	            	String sql4 =
                    "SELECT e.ideess, e.direccion, e.localidad, e.provincia, emp.rotulo, p.precio_gasolina " +
                    "FROM estacion_servicio e " +
                    "JOIN empresa emp ON e.id_empresa = emp.id_empresa " +
                    "JOIN precio_carburante p ON e.ideess = p.ideess " +
                    "ORDER BY p.precio_gasolina DESC " +
                    "LIMIT 1";

                rs = st.executeQuery(sql4);

                salida += "Gasolinera con el precio de gasolina más caro:\n\n";

                if (rs.next()) {
                    salida += "ID: " + rs.getInt("ideess") + "\n";
                    salida += "Empresa: " + rs.getString("rotulo") + "\n";
                    salida += "Dirección: " + rs.getString("direccion") + "\n";
                    salida += "Localidad: " + rs.getString("localidad") + "\n";
                    salida += "Provincia: " + rs.getString("provincia") + "\n";
                    salida += "Precio gasolina: " + rs.getDouble("precio_gasolina") + " €\n";
                } else {
                    salida += "No hay datos de precios de gasolina.\n";
                }

                v.taInfo.setText(salida);
                break;
	            case 5://Estaciones abiertas 24h
	                String sql5 =
	                    "SELECT ideess, direccion, localidad, horario " +
	                    "FROM estacion_servicio " +
	                    "WHERE horario LIKE '%24%'";

	                rs = st.executeQuery(sql5);

	                salida += "ID\tDirección\tLocalidad\tHorario\n";
	                salida += "------------------------------------------------------\n";

	                while (rs.next()) {
	                    salida +=
	                        rs.getInt("ideess") + "\t" +
	                        rs.getString("direccion") + "\t" +
	                        rs.getString("localidad") + "\t" +
	                        rs.getString("horario") + "\n";
	                }
	                v.taInfo.setText(salida);
	                break;
	            default:
	                JOptionPane.showMessageDialog(v, "Seleccione una consulta válida.");
	                break;
	        }
	    } catch (Exception e) {
	        v.taInfo.setText("Error ejecutando consulta:\n" + e.getMessage());
	    }
	}
}
