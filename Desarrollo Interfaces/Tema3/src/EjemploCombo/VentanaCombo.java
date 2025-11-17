package EjemploCombo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.*;
import javax.swing.*;

public class VentanaCombo extends JFrame implements ActionListener, ItemListener {
	private JLabel lbNombre, lbApellidos, lbCiudad;
	private JTextField tfNombre, tfApellidos, tfProvincia;
	private JComboBox cbCiudad;
	private JButton bEnviar;
	String[] ciudades = { "Gijon", "Mieres", "Oviedo", "Madrid" };

	public VentanaCombo(String nVentana) {
		super("nVentana");
		// Crear componentes
		lbNombre = new JLabel("Nombre:");
		lbNombre.setForeground(Color.PINK);
		tfNombre = new JTextField(20);
		lbApellidos = new JLabel("Apellidos:");
		lbApellidos.setForeground(Color.PINK);
		tfApellidos = new JTextField(20);
		lbCiudad = new JLabel("Ciudad:");
		tfProvincia = new JTextField(20);
		tfNombre.setBackground(Color.BLACK);
		tfApellidos.setBackground(Color.BLACK);
		tfProvincia.setBackground(Color.BLACK);
		tfNombre.setForeground(Color.WHITE);
		tfApellidos.setForeground(Color.WHITE);
		tfProvincia.setForeground(Color.WHITE);
		bEnviar = new JButton("Enviar");

		cbCiudad = new JComboBox(ciudades);

		// Creamos el contenedor
		this.setLayout(new FlowLayout());
		// Añadimos los componentes al contenedor
		this.add(lbNombre);
		this.add(tfNombre);
		this.add(lbApellidos);
		this.add(tfApellidos);
		this.add(cbCiudad);
		this.add(tfProvincia);
		this.add(bEnviar);

		this.setSize(300, 200);
		this.setVisible(true);

		// poner a la escucha los botones
		bEnviar.addActionListener(this);
		cbCiudad.addItemListener(this);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String driver = "com.mysql.cj.jdbc.Driver";	
		Connection cn = null;
		Statement st = null;
		
		String url = "jdbc:mysql://localhost:3306/";
		String user	= "root";
		String pass = "Samadegrao1";
		String sql="Insert into empleados values (?,?,?,?)";
		PreparedStatement ps=null;
		
		JButton btPulsado = (JButton) e.getSource();
		
		
		if (btPulsado == bEnviar) {
			try {
				Class.forName(driver);
				cn = DriverManager.getConnection(url, user, pass);
				ps=cn.prepareStatement(sql);
				ps.setString(1, tfNombre.getText());
				ps.setString(2, tfApellidos.getText());
				ps.setString(3, cbCiudad.getSelectedItem().toString());
				ps.setString(4, tfProvincia.getText());
				ps.executeUpdate();
				
			} catch (Exception e1) {
				//System.out.println(e1.getMessage());
				JOptionPane.showMessageDialog(this, "Error al insertar los datos en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		int cbEleccion = cbCiudad.getSelectedIndex();
		if (cbEleccion < 3) {
			tfProvincia.setText("Asturias");
		} else {
			tfProvincia.setText("Madrid");
		}
	}

}
