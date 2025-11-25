package EjemploPanelCombo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GestionEventos implements ItemListener, ChangeListener, ActionListener {
	private VentanaCombos ventanaPrincipal= null;
	
	public GestionEventos(VentanaCombos v) {
		this.ventanaPrincipal = v;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==ventanaPrincipal.cbPais) {
			//Selección del país
			if(ventanaPrincipal.cbPais.getSelectedIndex()==0) {
				ventanaPrincipal.cbCiudad.setEnabled(false);
				ventanaPrincipal.cbCiudad.removeAllItems();
			}else if(ventanaPrincipal.cbPais.getSelectedIndex()==1) {
				ventanaPrincipal.cbCiudad.setEnabled(true);
				ventanaPrincipal.cbCiudad.removeAllItems();
				ventanaPrincipal.cbCiudad.addItem("Seleccione Ciudad");
				ventanaPrincipal.cbCiudad.addItem("Madrid");
				ventanaPrincipal.cbCiudad.addItem("Gijon");
				ventanaPrincipal.cbCiudad.addItem("Oviedo");
			}else if(ventanaPrincipal.cbPais.getSelectedIndex()==2) {
				ventanaPrincipal.cbCiudad.setEnabled(true);
				ventanaPrincipal.cbCiudad.removeAllItems();
				ventanaPrincipal.cbCiudad.addItem("Seleccione Ciudad");
				ventanaPrincipal.cbCiudad.addItem("París");
				ventanaPrincipal.cbCiudad.addItem("Lyon");
				ventanaPrincipal.cbCiudad.addItem("Marsella");
			}else if(ventanaPrincipal.cbPais.getSelectedIndex()==3) {
				ventanaPrincipal.cbCiudad.setEnabled(true);
				ventanaPrincipal.cbCiudad.removeAllItems();
				ventanaPrincipal.cbCiudad.addItem("Seleccione Ciudad");
				ventanaPrincipal.cbCiudad.addItem("Berlín");
				ventanaPrincipal.cbCiudad.addItem("Múnich");
				ventanaPrincipal.cbCiudad.addItem("Frankfurt");
			}
			
		}else {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Ciudad seleccionada: "+
					ventanaPrincipal.cbCiudad.getSelectedItem().toString());
		}
		
		
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==ventanaPrincipal.chAceptar) {
			if(ventanaPrincipal.chAceptar.isSelected()) {
				ventanaPrincipal.btnContinuar.setEnabled(true);
			}else {
				ventanaPrincipal.btnContinuar.setEnabled(false);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ventanaPrincipal.btnContinuar) {
			JOptionPane.showMessageDialog(ventanaPrincipal, "Insercion Realizada,"+
					" Pais: "+ventanaPrincipal.cbPais.getSelectedItem().toString()+
					", Ciudad: "+ventanaPrincipal.cbCiudad.getSelectedItem().toString());
		}
	}
}
