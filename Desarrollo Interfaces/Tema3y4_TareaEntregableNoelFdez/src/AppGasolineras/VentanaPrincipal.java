package AppGasolineras;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    JLabel lbRuta, lbConsultas;
    JTextField tfRutaCSV;
    JButton btnInsertar, btnEjecutar;
    JTextArea taInfo;
    JScrollPane scroll;
    JComboBox cbConsultas;

    String[] consultas = {
    	    "Seleccione una consulta",
    	    "1. Localización, empresa y margen (Madrid)",
    	    "2. Empresa con más estaciones marítimas",
    	    "3. Gasolineras de la provincia de Málaga",
    	    "4. Gasolinera con el precio más caro",
    	    "5. Estaciones abiertas 24h"
    	};


    public VentanaPrincipal(String titulo) {
        super(titulo);

        this.setLayout(null);
        this.setBounds(200, 100, 800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ----- RUTA CSV -----
        lbRuta = new JLabel("Ruta CSV:");
        lbRuta.setBounds(20, 20, 100, 30);
        this.add(lbRuta);

        tfRutaCSV = new JTextField();
        tfRutaCSV.setBounds(100, 20, 450, 30);
        this.add(tfRutaCSV);

        btnInsertar = new JButton("Insertar CSV en BBDD");
        btnInsertar.setBounds(560, 20, 200, 30);
        this.add(btnInsertar);

        // ----- CONSULTAS -----
        lbConsultas = new JLabel("Consultas:");
        lbConsultas.setBounds(20, 70, 100, 30);
        this.add(lbConsultas);

        cbConsultas = new JComboBox(consultas);
        cbConsultas.setBounds(120, 70, 350, 30);
        this.add(cbConsultas);

        btnEjecutar = new JButton("Ejecutar Consulta");
        btnEjecutar.setBounds(480, 70, 180, 30);
        this.add(btnEjecutar);

        // ----- ÁREA CENTRAL -----
        taInfo = new JTextArea();
        taInfo.setEditable(false);

        scroll = new JScrollPane(taInfo);
        scroll.setBounds(20, 120, 740, 380);
        this.add(scroll);

        // ----- GESTIÓN DE EVENTOS -----
        GestionEventos ge = new GestionEventos(this);
        btnInsertar.addActionListener(ge);
        btnEjecutar.addActionListener(ge);
    }

    public static void main(String[] args) {
        VentanaPrincipal v = new VentanaPrincipal("Gasolineras - CSV");
        v.setVisible(true);
    }
}
