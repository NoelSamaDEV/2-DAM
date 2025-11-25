package AppOrdenata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionEventos implements ActionListener {

	public VentanaOrdenata ventana;

	public GestionEventos(VentanaOrdenata v1) {
		this.ventana = v1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double cantidad;
		if (e.getSource() == ventana.rbEscritorio) {
			ventana.tfResultado.setText("1000");
			cantidad = Double.parseDouble(ventana.tfResultado.getText());
			if (ventana.ckUsb.isSelected()) {
				cantidad += 20;
			}
			if (ventana.ckAuricular.isSelected()) {
				cantidad += 30;
			}
			ventana.tfResultado.setText(String.valueOf(cantidad));
		} else if (e.getSource() == ventana.rbPortatil) {
			ventana.tfResultado.setText("800");
			cantidad = Double.parseDouble(ventana.tfResultado.getText());
			if (ventana.ckUsb.isSelected()) {
				cantidad += 20;
			}
			if (ventana.ckAuricular.isSelected()) {
				cantidad += 30;
			}
			ventana.tfResultado.setText(String.valueOf(cantidad));
		} else if (e.getSource() == ventana.ckUsb) {
			cantidad = Double.parseDouble(ventana.tfResultado.getText());
			if (ventana.ckUsb.isSelected()) {
				cantidad += 20;
			} else {
				cantidad -= 20;
			}
			ventana.tfResultado.setText(String.valueOf(cantidad));
		}else if (e.getSource() == ventana.ckAuricular) {
			cantidad = Double.parseDouble(ventana.tfResultado.getText());
			if (ventana.ckAuricular.isSelected()) {
				cantidad += 30;
			} else {
				cantidad -= 30;
			}
			ventana.tfResultado.setText(String.valueOf(cantidad));
		}
	}
}