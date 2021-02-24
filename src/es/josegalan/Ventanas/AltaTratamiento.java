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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.josegalan.BaseDatos.BaseDatos;

public class AltaTratamiento implements WindowListener, ActionListener 
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
	Button btnCancelarAltaTratamiento = new Button("Cancelar");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgTratamientoInsertado = new Dialog(frmAltaTratamientos, "Operacion Correcta", true);
	Label lblAnadidoCorrectamente = new Label("Tratamiento añadido correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarTratamiento = new Dialog(frmAltaTratamientos, "Error", true);
	Label lblErrorAnadidoTratamiento = new Label("Faltan Datos");

	// ===================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaTratamiento() 
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
		frmAltaTratamientos.add(listaPrecios);
		frmAltaTratamientos.add(lblDescipcionAltaTratamiento);
		frmAltaTratamientos.add(txaDescripcion);
		btnAltaTratamiento.setBackground(Color.WHITE);
		btnAltaTratamiento.addActionListener(this);
		frmAltaTratamientos.add(btnAltaTratamiento);
		btnCancelarAltaTratamiento.setBackground(Color.WHITE);
		btnCancelarAltaTratamiento.addActionListener(this);
		frmAltaTratamientos.add(btnCancelarAltaTratamiento);
		frmAltaTratamientos.addWindowListener(this);
		frmAltaTratamientos.setLocationRelativeTo(null);
		frmAltaTratamientos.setResizable(false);
		frmAltaTratamientos.setBackground(clFondo);
		frmAltaTratamientos.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(btnCancelarAltaTratamiento)) {
			frmAltaTratamientos.setVisible(false);
			frmAltaTratamientos.removeWindowListener(this);
			btnCancelarAltaTratamiento.removeActionListener(this);
			btnAltaTratamiento.removeActionListener(this);
		}else if (e.getSource().equals(btnAltaTratamiento)) {
			creacionDialogoNotificacion(dlgErrorInsertarTratamiento, lblErrorAnadidoTratamiento);
			if (comprobacionDatos(txtNombreAltaTratamiento)) 
			{
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "INSERT INTO tratamientos VALUES (null, '"+ txtNombreAltaTratamiento.getText()+ "', '" +txaDescripcion.getText() +
							"', "+ listaPrecios.getSelectedItem() +");";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgTratamientoInsertado, lblAnadidoCorrectamente);
					dlgTratamientoInsertado.setVisible(true);
					bd.desconectar(connection);

				} catch (SQLException e1)
				{
					lblErrorAnadidoTratamiento.setText("Error al insertar");
					dlgErrorInsertarTratamiento.setVisible(true);
				}
			}
			else 
			{
				lblErrorAnadidoTratamiento.setText("Datos incorrectos");
				dlgErrorInsertarTratamiento.setVisible(true);
			}
		}
	}

	// =================================== CREACION DIALOGO =====================================================
	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(250, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
	}

	// =================================== COMPROBACION ===============================================
	public boolean comprobacionDatos(TextField nombre) 
	{
		boolean booleano = false;
		if (nombre.getText().length()!=0) {
			booleano = true;
		}
		return booleano;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgErrorInsertarTratamiento.isActive()) 
		{
			dlgErrorInsertarTratamiento.setVisible(false);
		}
		else 
		{
			frmAltaTratamientos.setVisible(false);
			frmAltaTratamientos.removeWindowListener(this);
			dlgTratamientoInsertado.setVisible(false);
			dlgTratamientoInsertado.removeWindowListener(this);
			btnAltaTratamiento.removeActionListener(this);
			btnCancelarAltaTratamiento.addActionListener(this);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
