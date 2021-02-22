package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.josegalan.BaseDatos.BaseDatos;

public class Tratamientos implements WindowListener, ActionListener, ItemListener
{

	// ===================================== VENTANA ALTA CLIENTES =======================================================
	Frame frmAltaTratamientos = new Frame("Alta Tratamientos");
	Label lblNombreAltaTratamiento = new Label("Nombre:");
	Label lblPrecioAltaTratamiento = new Label("Precio:");
	Label lblDescipcionAltaTratamiento = new Label("Descripcion:");
	TextField txtNombreAltaTratamiento = new TextField(20);
	Color clFondo = new Color(204,229,255);
	String[] precios = {"0.00","1.00","1.50","2.00","2.50","3.00","3.50","4.00","4.50","5.00","5.50","6.00","6.50","7.00","7.50","8.00","8.50","9.00","9.50"};
	Choice listaPrecios = new Choice(); 
	TextArea txaDescripcion = new TextArea(7, 45);
	Button btnAltaTratamiento = new Button("Alta");
	Button btnAtrasTratamiento = new Button("Cancelar");
	double eleccion = 0.00;

	// ===================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	// ========================== DIALOGO CONFIRMACION TRATAMIENTOS NUEVOS =========================
	Frame frmConfirmacionAltaTratamientos = new Frame("Confirmación");
	Label lblSeguroAltaTratamiento = new Label("¿Estas seguro de crear este Tratamiento?");
	Button btnSiConfirmacionSeguroAlta = new Button("Si");
	Button btnNoConfirmacionSeguroAlta = new Button("No");

	//============================ CREACION DIALOGO ERROR =============================================
	Dialog dlgErrorAltaTratamiento = new Dialog(frmConfirmacionAltaTratamientos, "Error", false);
	Label lblErrorAltaTratamiento = new Label("Datos incorrectos");

	// =========================== CREACION DIALOGO CORRECTO INSERTAR =================================
	Dialog dlgTratamientoInsertado = new Dialog(frmConfirmacionAltaTratamientos, "Operacion Correcta", false);
	Label lblAnadidoCorrectamente = new Label("Cliente añadido correctamente");



	public Tratamientos() {

	}

	// ====================================== MOSTRAR ALTA TRATAMIENTOS =========================================================
	public void mostrarAltaTratamientos() 
	{
		frmAltaTratamientos.setSize(400, 270);
		frmAltaTratamientos.setLayout(new FlowLayout());
		frmAltaTratamientos.add(lblNombreAltaTratamiento);
		frmAltaTratamientos.add(txtNombreAltaTratamiento);
		frmAltaTratamientos.add(lblPrecioAltaTratamiento);
		for (String d : precios) {
			listaPrecios.add((d));
		}
		listaPrecios.setBackground(Color.WHITE);
		listaPrecios.addItemListener(this);
		frmAltaTratamientos.add(listaPrecios);
		frmAltaTratamientos.add(lblDescipcionAltaTratamiento);
		frmAltaTratamientos.add(txaDescripcion);
		btnAltaTratamiento.setBackground(Color.WHITE);
		btnAltaTratamiento.addActionListener(this);
		frmAltaTratamientos.add(btnAltaTratamiento);
		btnAtrasTratamiento.setBackground(Color.WHITE);
		btnAtrasTratamiento.addActionListener(this);
		frmAltaTratamientos.add(btnAtrasTratamiento);
		frmAltaTratamientos.addWindowListener(this);
		frmAltaTratamientos.setLocationRelativeTo(null);
		frmAltaTratamientos.setResizable(false);
		frmAltaTratamientos.setBackground(clFondo);
		frmAltaTratamientos.setVisible(true);
	}

	// ========================================================== WINDOWS LISTENER ==============================================
	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		if (frmAltaTratamientos.isActive())
		{
			frmAltaTratamientos.setVisible(false);
		}else if (frmConfirmacionAltaTratamientos.isActive()) {
			frmConfirmacionAltaTratamientos.setVisible(false);
		}else if (dlgErrorAltaTratamiento.isActive()) {
			dlgErrorAltaTratamiento.setVisible(false);
			frmConfirmacionAltaTratamientos.setVisible(false);
		}else if (dlgTratamientoInsertado.isActive()) {
			dlgTratamientoInsertado.setVisible(false);
			frmConfirmacionAltaTratamientos.setVisible(false);
			frmAltaTratamientos.setVisible(false);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

	// ======================================================= ITEM LISTENER =======================================================
	@Override
	public void itemStateChanged(ItemEvent e) {

		for (String string : precios) {
			if (string.equals(e.getItem())) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					eleccion= Double.parseDouble(string);
				}
			}
		}
	}

	// ========================================================= ACTION LISTENER ==================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAtrasTratamiento)) {
			frmAltaTratamientos.setVisible(false);
		}else if (e.getSource().equals(btnAltaTratamiento)) 
		{
			creacionVentanaConfirmacion(frmConfirmacionAltaTratamientos, lblSeguroAltaTratamiento, btnSiConfirmacionSeguroAlta, btnNoConfirmacionSeguroAlta);
		}else if (e.getSource().equals(btnSiConfirmacionSeguroAlta)) 
		{
			insertarTratamiento();
		}else if (e.getSource().equals(btnNoConfirmacionSeguroAlta)) {
			frmConfirmacionAltaTratamientos.setVisible(false);
		}
	}

	// ========================================================== COMPROBACION DATOS ================================================

	public boolean comprobacionDatos(TextField nombre) 
	{
		boolean booleano = false;
		if (nombre.getText().length()!=0) {
			booleano = true;
		}
		return booleano;
	}

	// =============================================== INSERTAR DATOS ==============================================================
	public void insertarTratamiento() {
		if (comprobacionDatos(txtNombreAltaTratamiento)) {
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sentencia = "INSERT INTO tratamientos VALUES (null, '"+ txtNombreAltaTratamiento.getText()+ "', '"+ txaDescripcion.getText()+
						"', "+ eleccion +");";
				statement.executeUpdate(sentencia);
			} catch (SQLException e)
			{
				System.out.println("Error 3 - "+e.getMessage());
			}finally {
				creacionDialogoNotificacion(dlgTratamientoInsertado, lblAnadidoCorrectamente);
				txtNombreAltaTratamiento.setText("");
				txaDescripcion.setText("");
				bd.desconectar(connection);
			}
		}else {
			creacionDialogoNotificacion(dlgErrorAltaTratamiento, lblErrorAltaTratamiento);
		}
	}
	
	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(250, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
		dialogo.setVisible(true);
	}
	
	public void creacionVentanaConfirmacion(Frame ventana, Label lbl, Button btnUno, Button btnDos) 
	{
		ventana.setSize(270, 100);
		ventana.setLayout(new FlowLayout());
		ventana.setBackground(clFondo);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.add(lbl);
		btnUno.setBackground(Color.WHITE);
		btnDos.setBackground(Color.WHITE);
		btnUno.addActionListener(this);
		btnDos.addActionListener(this);
		ventana.add(btnUno);
		ventana.add(btnDos);
		ventana.setVisible(true);
	}



}
