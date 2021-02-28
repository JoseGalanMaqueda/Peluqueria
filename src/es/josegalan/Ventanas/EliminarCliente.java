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

public class EliminarCliente implements WindowListener, ActionListener
{

	// ========================= ELIMINAR CLIENTE PRINCIPAL ===============================================
	Frame frmEliminarCliente = new Frame("Eliminar Cliente");
	Color clFondo = new Color(204,229,255);
	Label lblSeleccionarEliminarCliente = new Label("Selecciona Cliente a Eliminar:");
	Choice choListaCliente = new Choice();
	Button btnEliminar = new Button("Eliminar");
	Button btnCancelarEliminar = new Button("Cancelar");

	// ========================= CONFIRMACION ELIMINAR ====================================================
	Frame frmConfirmacionEliminarCliente = new Frame("Confirmacion");
	Label lblConfirmacionEliminarCliente = new Label("¿Estás seguro que deseas eliminarlo?");
	Button btnSiConfirmacionElimiarCliente = new Button("Si");
	Button btnNoConfirmacionElimiarCliente = new Button("No");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgCitasInsertado = new Dialog(frmConfirmacionEliminarCliente, "Operacion Correcta", true);
	Label lblAnadidaCorrectamente = new Label("Cliente Eliminado Correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarCita = new Dialog(frmConfirmacionEliminarCliente, "Error", true);
	Label lblErrorAnadidoCita = new Label("Error al Eliminar Cliente");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public EliminarCliente() 
	{
		frmEliminarCliente.setLayout(new FlowLayout());
		frmEliminarCliente.setSize(280, 130);
		frmEliminarCliente.setBackground(clFondo);
		frmEliminarCliente.add(lblSeleccionarEliminarCliente);
		cargarListadoClientes(choListaCliente);
		choListaCliente.setBackground(Color.WHITE);
		frmEliminarCliente.add(choListaCliente);
		btnEliminar.setBackground(Color.WHITE);
		btnEliminar.addActionListener(this);
		btnCancelarEliminar.setBackground(Color.WHITE);
		btnCancelarEliminar.addActionListener(this);
		frmEliminarCliente.add(btnEliminar);
		frmEliminarCliente.add(btnCancelarEliminar);
		frmEliminarCliente.addWindowListener(this);
		frmEliminarCliente.setResizable(false);
		frmEliminarCliente.setLocationRelativeTo(null);
		frmEliminarCliente.setVisible(true);
	}

	public void cargarListadoClientes(Choice cholistaClientes) 
	{
		bd = new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM clientes;";
			rs = statement.executeQuery(sentencia);
			cholistaClientes.removeAll();
			cholistaClientes.add("Selecciona un Cliente..");
			while (rs.next()) {
				cholistaClientes.add(rs.getInt("idCliente")+"-"+rs.getString("nombreCliente")+"-"+rs.getString("apellidosCliente")+"-"+rs.getString("dniCliente"));
			}
		} catch (SQLException e) {
			cholistaClientes.removeAll();
			cholistaClientes.add("Problema al cargar los datos");
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarEliminar)) {
			frmEliminarCliente.setVisible(false);
			btnEliminar.removeActionListener(this);
			btnCancelarEliminar.removeActionListener(this);
			frmEliminarCliente.removeWindowListener(this);
		}else if (e.getSource().equals(btnEliminar)) {
			creacionVentanaConfirmacion(frmConfirmacionEliminarCliente, lblConfirmacionEliminarCliente, btnSiConfirmacionElimiarCliente, btnNoConfirmacionElimiarCliente);
		}else if (e.getSource().equals(btnNoConfirmacionElimiarCliente)) {
			frmConfirmacionEliminarCliente.setVisible(false);
			btnSiConfirmacionElimiarCliente.removeActionListener(this);
			btnNoConfirmacionElimiarCliente.removeActionListener(this);
			frmConfirmacionEliminarCliente.removeWindowListener(this);
		}else if (e.getSource().equals(btnSiConfirmacionElimiarCliente)) {
			creacionDialogoNotificacion(dlgErrorInsertarCita, lblErrorAnadidoCita);
			if (choListaCliente.getSelectedItem().equals("Selecciona un Cliente..")) 
			{
				lblErrorAnadidoCita.setText("Datos incorrectos");
				dlgErrorInsertarCita.setVisible(true);
			}
			else 
			{
				
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "DELETE FROM clientes WHERE idCliente = "+choListaCliente.getSelectedItem().split("-")[0]+";";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgCitasInsertado, lblAnadidaCorrectamente);
					dlgCitasInsertado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorAnadidoCita.setText("Error al eliminar");
					dlgErrorInsertarCita.setVisible(true);
				}finally {
					bd.desconectar(connection);
				}
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (frmEliminarCliente.isActive()) {
			frmEliminarCliente.setVisible(false);
			btnEliminar.removeActionListener(this);
			btnCancelarEliminar.removeActionListener(this);
			frmEliminarCliente.removeWindowListener(this);
		}else if (frmConfirmacionEliminarCliente.isActive()) {
			frmConfirmacionEliminarCliente.setVisible(false);
			btnSiConfirmacionElimiarCliente.removeActionListener(this);
			btnNoConfirmacionElimiarCliente.removeActionListener(this);
			frmConfirmacionEliminarCliente.removeWindowListener(this);
		}
		else if (frmConfirmacionEliminarCliente.isActive()) {
			frmConfirmacionEliminarCliente.setVisible(false);
			btnSiConfirmacionElimiarCliente.removeActionListener(this);
			btnNoConfirmacionElimiarCliente.removeActionListener(this);
			frmConfirmacionEliminarCliente.removeWindowListener(this);
		}else if (dlgCitasInsertado.isActive()) {
			frmEliminarCliente.setVisible(false);
			frmConfirmacionEliminarCliente.setVisible(false);
			btnSiConfirmacionElimiarCliente.removeActionListener(this);
			btnNoConfirmacionElimiarCliente.removeActionListener(this);
			frmConfirmacionEliminarCliente.removeWindowListener(this);
			btnEliminar.removeActionListener(this);
			btnCancelarEliminar.removeActionListener(this);
			frmEliminarCliente.removeWindowListener(this);
		}else if (dlgErrorInsertarCita.isActive()) {
			dlgErrorInsertarCita.setVisible(false);
			dlgErrorInsertarCita.removeWindowListener(this);
			frmConfirmacionEliminarCliente.setVisible(false);
			btnSiConfirmacionElimiarCliente.removeActionListener(this);
			btnNoConfirmacionElimiarCliente.removeActionListener(this);
			frmConfirmacionEliminarCliente.removeWindowListener(this);
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

}
