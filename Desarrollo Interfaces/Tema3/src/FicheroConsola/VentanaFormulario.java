package FicheroConsola;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;


public class VentanaFormulario extends JFrame implements ActionListener {
	
	private JLabel lbNombre,lbApellido;
	private JTextField tfNombre,tfApellido;
	private JButton bConsola,bFichero;
	
	public VentanaFormulario(String nombreVentana) {
		super(nombreVentana);
		lbNombre=new JLabel("Nombre:");
		lbApellido=new JLabel("Apellido:");
		tfNombre=new JTextField(10);
		tfApellido=new JTextField(10);
		bConsola=new JButton("CONSOLA");
		bFichero=new JButton("FICHERO");
		
		//Crear contenedor
		super.setLayout(new GridLayout(3,2));
		super.add(lbNombre);
		super.add(tfNombre);
		super.add(lbApellido);
		super.add(tfApellido);
		super.add(bConsola);
		super.add(bFichero);
		
		//Definir tamaño de la ventana
		super.setSize(300,150);
		
		//Poner a la escucha los botones
		bConsola.addActionListener(this);
		bFichero.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton botonPulsado=(JButton)e.getSource();
		if(botonPulsado==bConsola) {
			//Escribir en consola
			System.out.println("Nombre: "+tfNombre.getText());
			System.out.println("Apellido: "+tfApellido.getText());
		}else if(botonPulsado==bFichero) {
			//Escribir en fichero
			try {
				FileWriter f1= new FileWriter("C:\\Users\\noels\\OneDrive\\Desktop\\2ºDAM\\2-DAM\\Desarrollo Interfaces\\Tema3\\src\\FicheroConsola\\datos.txt",true);
				f1.write("---------------------\n");
				f1.write("Nombre: "+tfNombre.getText()+"\n");
				f1.write("Apellido: "+tfApellido.getText()+"\n");
				f1.close();
			} catch (IOException ex) {
				System.out.println("Error "+ex.getMessage());
			}		
		}
	}
	
}
