package AppOrdenata;

import javax.swing.*;


public class VentanaOrdenata extends JFrame {
	
	public JPanel pnSuperior,pnIzquierda,pnDerecha;
	public ButtonGroup grOrdenadores;
	public JRadioButton rbPortatil,rbEscritorio;
	public JLabel lbTitulo,lbResultado;
	public JTextField tfResultado;
	public JCheckBox ckUsb,ckAuricular;
	
	public VentanaOrdenata(String titulo) {
		super(titulo);
		this.setLayout(null);
		this.setBounds(100, 100, 500, 270);
		lbTitulo = new JLabel("ORDENADORES UNENDO");
		lbTitulo.setBounds(150, 10, 150, 25);
		this.add(lbTitulo);
		//Crear el panel1
		crearPanelSuperior();
		this.add(pnSuperior);
		//posicionar panel 1
		pnSuperior.setBounds(10, 40, 465, 70);
		//Crear  el panel2
		crearPanelIzquierda();
		this.add(pnIzquierda);
		//posicionar panel 2
		pnIzquierda.setBounds(10, 120, 230, 100);
		//Crear  el panel3
		crearPanelDerecha();
		this.add(pnDerecha);
		//posicionar panel 3
		pnDerecha.setBounds(250, 120, 225, 100);
		//dar visibilidad a los paneles
		this.setVisible(true);
		
		//poner escucha componentes
		this.rbEscritorio.addActionListener(new GestionEventos(this));
		this.rbPortatil.addActionListener(new GestionEventos(this));
		this.ckUsb.addActionListener(new GestionEventos(this));
		this.ckAuricular.addActionListener(new GestionEventos(this));
		
	}
	
	public void crearPanelSuperior() {
		pnSuperior = new JPanel();
		pnSuperior.setLayout(null);
		pnSuperior.setBorder(BorderFactory.createTitledBorder("Seleccione tipo de ordenador"));
		grOrdenadores = new ButtonGroup();
		rbPortatil = new JRadioButton("Ordenador Portátil");
		rbEscritorio = new JRadioButton("Ordenador Sobremesa");
		grOrdenadores.add(rbPortatil);
		grOrdenadores.add(rbEscritorio);
		rbPortatil.setBounds(10, 23, 200, 30);
		rbEscritorio.setBounds(210, 23, 200, 30);
		pnSuperior.add(rbPortatil);
		pnSuperior.add(rbEscritorio);
	}
	
	public void crearPanelIzquierda() {
		pnIzquierda = new JPanel();
		pnIzquierda.setLayout(null);
		pnIzquierda.setBorder(BorderFactory.createTitledBorder("Seleccione accesorios"));
		ckUsb = new JCheckBox("USB 128Gb (20€)");
		ckAuricular = new JCheckBox("Auriculares (30€)");
		ckUsb.setBounds(10, 23, 200, 30);
		ckAuricular.setBounds(10, 53, 200, 30);
		pnIzquierda.add(ckUsb);
		pnIzquierda.add(ckAuricular);
	}
	
	public void crearPanelDerecha() {
		pnDerecha = new JPanel();
		pnDerecha.setLayout(null);
		pnDerecha.setBorder(BorderFactory.createTitledBorder("Resultado"));
		lbResultado = new JLabel("Precio final:");
		tfResultado = new JTextField();
		lbResultado.setBounds(10, 23, 100, 30);
		tfResultado.setBounds(120, 23, 100, 30);
		pnDerecha.add(lbResultado);
		pnDerecha.add(tfResultado);
	}
	
}
