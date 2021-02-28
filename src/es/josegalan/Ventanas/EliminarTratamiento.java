package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.josegalan.BaseDatos.BaseDatos;

public class EliminarTratamiento implements ActionListener, WindowListener
{

	// ========================= ELIMINAR CLIENTE PRINCIPAL ===============================================
	Frame frmEliminarTratamiento = new Frame("Eliminar Tratamiento");
	Color clFondo = new Color(204,229,255);
	Label lblSeleccionarEliminarTratamiento = new Label("Selecciona Tratamiento a Eliminar:");
	Choice choListaTratamientos = new Choice();
	Button btnEliminar = new Button("Eliminar");
	Button btnCancelarEliminar = new Button("Cancelar");

	// ========================= CONFIRMACION ELIMINAR ====================================================
	Frame frmConfirmacionEliminarTratamiento = new Frame("Confirmacion");
	Label lblConfirmacionEliminarTratamiento = new Label("¿Estás seguro que deseas eliminarlo?");
	Button btnSiConfirmacionElimiarTratamiento = new Button("Si");
	Button btnNoConfirmacionElimiarTratamiento = new Button("No");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgTratamientoInsertado = new Dialog(frmConfirmacionEliminarTratamiento, "Operacion Correcta", true);
	Label lblAnadidaCorrectamente = new Label("Tratamiento Eliminado Correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarTratamiento = new Dialog(frmConfirmacionEliminarTratamiento, "Error", true);
	Label lblErrorAnadidoTratamiento = new Label("Error al Eliminar Tratamiento");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public EliminarTratamiento() {
		frmEliminarTratamiento.setLayout(new FlowLayout());
		frmEliminarTratamiento.setSize(270, 130);
		frmEliminarTratamiento.setBackground(clFondo);
		frmEliminarTratamiento.add(lblSeleccionarEliminarTratamiento);
		cargarListadoTratamientos(choListaTratamientos);
		choListaTratamientos.setBackground(Color.WHITE);
		frmEliminarTratamiento.add(choListaTratamientos);
		btnEliminar.setBackground(Color.WHITE);
		btnEliminar.addActionListener(this);
		btnCancelarEliminar.setBackground(Color.WHITE);
		btnCancelarEliminar.addActionListener(this);
		frmEliminarTratamiento.add(btnEliminar);
		frmEliminarTratamiento.add(btnCancelarEliminar);
		frmEliminarTratamiento.addWindowListener(this);
		frmEliminarTratamiento.setResizable(false);
		frmEliminarTratamiento.setLocationRelativeTo(null);
		frmEliminarTratamiento.setVisible(true);
	}

	public void cargarListadoTratamientos(Choice choListaTratamientos) 
	{
		bd = new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM tratamientos;";
			rs = statement.executeQuery(sentencia);
			choListaTratamientos.removeAll();
			choListaTratamientos.add("Selecciona un Tratamiento..");
			while (rs.next()) {
				choListaTratamientos.add(rs.getInt("idTratamiento")+"-"+rs.getString("nombreTratamiento"));
			}
		} catch (SQLException e) {
			choListaTratamientos.removeAll();
			choListaTratamientos.add("Problema al cargar los datos");
		}finally {
			bd.desconectar(connection);
		}
	}

	// ====================================== CREAR VENTANA CONFIRMACION =====================================================================
	public void creacionVentanaConfirmacion(Frame ventana, Label lbl, Button btnUno, Button btnDos) 
	{
		ventana.setSize(250, 100);
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

	// ======================================= CREAR DIALOGO NOTIFICACION ==================================================================
	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(250, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		if (frmEliminarTratamiento.isActive()) {
			frmEliminarTratamiento.setVisible(false);
			btnEliminar.removeActionListener(this);
			btnCancelarEliminar.removeActionListener(this);
			frmEliminarTratamiento.removeWindowListener(this);
		}else if (frmConfirmacionEliminarTratamiento.isActive()) {
			frmConfirmacionEliminarTratamiento.setVisible(false);
			btnSiConfirmacionElimiarTratamiento.removeActionListener(this);
			btnNoConfirmacionElimiarTratamiento.removeActionListener(this);
			frmConfirmacionEliminarTratamiento.removeWindowListener(this);
		}
		else if (frmConfirmacionEliminarTratamiento.isActive()) {
			frmConfirmacionEliminarTratamiento.setVisible(false);
			btnSiConfirmacionElimiarTratamiento.removeActionListener(this);
			btnNoConfirmacionElimiarTratamiento.removeActionListener(this);
			frmConfirmacionEliminarTratamiento.removeWindowListener(this);
		}else if (dlgTratamientoInsertado.isActive()) {
			frmEliminarTratamiento.setVisible(false);
			frmConfirmacionEliminarTratamiento.setVisible(false);
			btnSiConfirmacionElimiarTratamiento.removeActionListener(this);
			btnNoConfirmacionElimiarTratamiento.removeActionListener(this);
			frmConfirmacionEliminarTratamiento.removeWindowListener(this);
			btnEliminar.removeActionListener(this);
			btnCancelarEliminar.removeActionListener(this);
			frmEliminarTratamiento.removeWindowListener(this);
		}else if (dlgErrorInsertarTratamiento.isActive()) {
			dlgErrorInsertarTratamiento.setVisible(false);
			dlgErrorInsertarTratamiento.removeWindowListener(this);
			frmConfirmacionEliminarTratamiento.setVisible(false);
			btnSiConfirmacionElimiarTratamiento.removeActionListener(this);
			btnNoConfirmacionElimiarTratamiento.removeActionListener(this);
			frmConfirmacionEliminarTratamiento.removeWindowListener(this);
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarEliminar)) {
			frmEliminarTratamiento.setVisible(false);
			btnEliminar.removeActionListener(this);
			btnCancelarEliminar.removeActionListener(this);
			frmEliminarTratamiento.removeWindowListener(this);
		}else if (e.getSource().equals(btnEliminar)) {
			creacionVentanaConfirmacion(frmConfirmacionEliminarTratamiento, lblConfirmacionEliminarTratamiento, btnSiConfirmacionElimiarTratamiento, btnNoConfirmacionElimiarTratamiento);
		}else if (e.getSource().equals(btnNoConfirmacionElimiarTratamiento)) {
			frmConfirmacionEliminarTratamiento.setVisible(false);
			btnSiConfirmacionElimiarTratamiento.removeActionListener(this);
			btnNoConfirmacionElimiarTratamiento.removeActionListener(this);
			frmConfirmacionEliminarTratamiento.removeWindowListener(this);
		}else if (e.getSource().equals(btnSiConfirmacionElimiarTratamiento)) {
			creacionDialogoNotificacion(dlgErrorInsertarTratamiento, lblErrorAnadidoTratamiento);
			if (choListaTratamientos.getSelectedItem().equals("Selecciona un Cliente..")) 
			{
				lblErrorAnadidoTratamiento.setText("Datos incorrectos");
				dlgErrorInsertarTratamiento.setVisible(true);
			}
			else 
			{

				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "DELETE FROM tratamientos WHERE idTratamiento = "+choListaTratamientos.getSelectedItem().split("-")[0]+";";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgTratamientoInsertado, lblAnadidaCorrectamente);
					dlgTratamientoInsertado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorAnadidoTratamiento.setText("Error al eliminar");
					dlgErrorInsertarTratamiento.setVisible(true);
				}finally {
					bd.desconectar(connection);
				}
			}
		}
	}

}
