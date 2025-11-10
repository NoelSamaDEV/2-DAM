package EjemploBase;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class VentanaBase extends JFrame implements ActionListener {

	private JLabel lbCantidad;
	private JTextField tfCantidad;
	private JButton bEuros, bPesetas;

	public VentanaBase() {
		super("EUROCONVERSOR");
		// Crear componentes
		lbCantidad = new JLabel();
		lbCantidad.setText("INTRODUZCA CANTIDAD A CONVERTIR");
		tfCantidad = new JTextField(20);
		bEuros = new JButton("A EUROS");
		bPesetas = new JButton("A PESETAS");

		// Creamos el contenedor
		this.setLayout(new FlowLayout());
		this.setSize(300, 250);

		// Añadimos los componentes al contenedor
		this.add(lbCantidad);
		this.add(tfCantidad);
		this.add(bEuros);
		this.add(bPesetas);

		// Hacemos visible la ventana
		this.setVisible(true);
		
		//poner a la escucha los botones
		bEuros.addActionListener(this);
		bPesetas.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// logica de la ventana
		JButton btPulsado = (JButton) e.getSource();
		String datos = tfCantidad.getText();
		double total= Double.parseDouble(datos);
		if (btPulsado == bEuros) {
			// convertir a euros
			total=total/166.386;
		} else if (btPulsado == bPesetas) {
			// convertir a pesetas
			total=total*166.386;
		}
		tfCantidad.setText(Double.toString(total));
		
	}

}
