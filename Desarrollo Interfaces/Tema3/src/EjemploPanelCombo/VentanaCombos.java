package EjemploPanelCombo;

import javax.swing.*;

public class VentanaCombos extends JFrame {
	//Componentes
	JLabel lbTitulo, lbPais, lbCiudad, lbTitulo2;
	JComboBox cbPais, cbCiudad;
	JPanel pnContenedor, pnContenedorAbajo;
	String[] paises = {"Seleccione Pais", "España", "Francia", "Alemania"};
	//nuevos componentes
	JCheckBox chAceptar;
	JButton btnContinuar;
	
	//Constructor
	public VentanaCombos(String tituloVentana) {
		super(tituloVentana);
		this.setLayout(null);
		this.setBounds(100, 150, 410, 420);
		lbTitulo = new JLabel("SELECCIONE PAIS Y CIUDAD");
		this.add(lbTitulo);
		lbTitulo.setBounds(100, 20, 200, 20);
		//Crear panel
		crearPanel();
		crearPanelAbajo();
		//añadir el panel
		this.add(pnContenedor);
		this.add(pnContenedorAbajo);
		//colocar el panel
		pnContenedor.setBounds(10, 50, 375, 120);
		pnContenedorAbajo.setBounds(10, 200, 375, 130);
		cbPais.addItemListener(new GestionEventos(this));
		//cbCiudad.addItemListener(new GestionEventos(this));
		chAceptar.addChangeListener(new GestionEventos(this));
		btnContinuar.addActionListener(new GestionEventos(this));
		
		
		
		
	}
	private void crearPanel() {
		pnContenedor = new JPanel();
		pnContenedor.setLayout(null);
		pnContenedor.setBorder(BorderFactory.createTitledBorder("Europa"));
		lbPais = new JLabel("País");
		lbPais.setBounds(30, 30, 50, 20);
		pnContenedor.add(lbPais);
		lbCiudad = new JLabel("Ciudad");
		lbCiudad.setBounds(30, 70, 50, 20);
		pnContenedor.add(lbCiudad);
		cbPais = new JComboBox(paises);
		cbPais.setBounds(100, 30, 150, 20);
		pnContenedor.add(cbPais);
		cbCiudad = new JComboBox();
		cbCiudad.setEnabled(false);
		cbCiudad.setBounds(100, 70, 150, 20);
		pnContenedor.add(cbCiudad);
	}
	
	private void crearPanelAbajo() {
		pnContenedorAbajo = new JPanel();
		pnContenedorAbajo.setLayout(null);
		pnContenedorAbajo.setBorder(BorderFactory.createTitledBorder("NORMATIVA"));
		lbTitulo2 = new JLabel("Pulsar si esta de acuerdo con las normas del servicio");
		lbTitulo2.setBounds(10, 20, 400, 20);
		pnContenedorAbajo.add(lbTitulo2);
		chAceptar = new JCheckBox("Acepto");
		chAceptar.setBounds(10, 50, 100, 20);
		pnContenedorAbajo.add(chAceptar);
		btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(10, 90, 100, 30);
		pnContenedorAbajo.add(btnContinuar);
		btnContinuar.setEnabled(false);
		
	}
	
}
