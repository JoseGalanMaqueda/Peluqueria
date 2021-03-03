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

public class AltaAsignaciones implements ActionListener, WindowListener
{

	// ========================================= VENTANA PRINCIPAL ========================================
	Frame frmAltaAsignaciones = new Frame("Alta Asignaciones Tratamientos a Citas");
	Label lblSeleccionaCitaAltaAsignaciones = new Label("Selecciona una Cita:");
	Label lblSeleccionaTratamientoAltaAsignaciones = new Label("Selecciona un Tratamiento:");
	Choice choListaCitasAltaAsginaciones = new Choice();
	Choice choListaTratamientosAltaAsignaciones = new Choice();
	Button btnAltaAsignaciones = new Button("Alta");
	Button btnCancelarAsignaciones = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgAsignacionesInsertado = new Dialog(frmAltaAsignaciones, "Operacion Correcta", true);
	Label lblAnadidaCorrectamente = new Label("Asignacion añadida Correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarAsignacion = new Dialog(frmAltaAsignaciones, "Error", true);
	Label lblErrorAnadidoAsignacion = new Label("Error al Añadir Asignacion");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	// ==================================== CONSTRUCTOR ============================================================================
	public AltaAsignaciones() 
	{
		frmAltaAsignaciones.setLayout(new FlowLayout());
		frmAltaAsignaciones.setBackground(clFondo);
		frmAltaAsignaciones.setSize(320, 190);	
		frmAltaAsignaciones.add(lblSeleccionaCitaAltaAsignaciones);
		cargarListadoCitas(choListaCitasAltaAsginaciones);
		choListaCitasAltaAsginaciones.setBackground(Color.WHITE);
		frmAltaAsignaciones.add(choListaCitasAltaAsginaciones);
		frmAltaAsignaciones.add(lblSeleccionaTratamientoAltaAsignaciones);
		cargarListadoTratamientos(choListaTratamientosAltaAsignaciones);
		choListaTratamientosAltaAsignaciones.setBackground(Color.WHITE);
		frmAltaAsignaciones.add(choListaTratamientosAltaAsignaciones);
		btnAltaAsignaciones.addActionListener(this);
		btnCancelarAsignaciones.addActionListener(this);
		btnAltaAsignaciones.setBackground(Color.WHITE);
		btnCancelarAsignaciones.setBackground(Color.WHITE);
		frmAltaAsignaciones.add(btnAltaAsignaciones);
		frmAltaAsignaciones.add(btnCancelarAsignaciones);
		frmAltaAsignaciones.addWindowListener(this);
		frmAltaAsignaciones.setResizable(false);
		frmAltaAsignaciones.setLocationRelativeTo(null);
		frmAltaAsignaciones.setVisible(true);
	}

	// ===================================================== WINDOW LISTENER ===================================================
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgErrorInsertarAsignacion.isActive()) 
		{
			dlgErrorInsertarAsignacion.setVisible(false);
			dlgErrorInsertarAsignacion.removeWindowListener(this);
		}
		else 
		{
			frmAltaAsignaciones.setVisible(false);
			frmAltaAsignaciones.removeWindowListener(this);
			dlgAsignacionesInsertado.setVisible(false);
			dlgErrorInsertarAsignacion.removeWindowListener(this);
			btnAltaAsignaciones.removeActionListener(this);
			btnCancelarAsignaciones.addActionListener(this);
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

	// ==================================================== ACTION LISTENER ===========================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarAsignaciones)) {
			frmAltaAsignaciones.setVisible(false);
			frmAltaAsignaciones.removeWindowListener(this);
			btnAltaAsignaciones.removeActionListener(this);
			btnCancelarAsignaciones.removeActionListener(this);
		}
		else if (e.getSource().equals(btnAltaAsignaciones)) 
		{
			creacionDialogoNotificacion(dlgErrorInsertarAsignacion, lblErrorAnadidoAsignacion);
			if (choListaCitasAltaAsginaciones.getSelectedItem().equals("Selecciona una Cita..") || choListaTratamientosAltaAsignaciones.getSelectedItem().equals("Selecciona un Tratamiento para la cita..")) 
			{
				lblErrorAnadidoAsignacion.setText("Datos incorrectos");
				dlgErrorInsertarAsignacion.setVisible(true);
			}
			else 
			{
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "INSERT INTO tratamiento_citas VALUES(null, "+choListaCitasAltaAsginaciones.getSelectedItem().split("-")[0]+","
							+choListaTratamientosAltaAsignaciones.getSelectedItem().split("-")[0]+");";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgAsignacionesInsertado, lblAnadidaCorrectamente);
					dlgAsignacionesInsertado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorAnadidoAsignacion.setText("Error al insertar");
					dlgErrorInsertarAsignacion.setVisible(true);
				}finally {
					bd.desconectar(connection);
				}
			}
		}
	}

	// ==================================== CARGAR LISTADO TRATAMIENTOS ============================================================================
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
			choListaTratamientos.add("Selecciona un Tratamiento para la cita..");
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

	// ==================================== CARGAR LISTADO CITAS ============================================================================
	public void cargarListadoCitas(Choice choListaCitas) 
	{
		bd = new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT citas.idCita, citas.fechaCita, citas.horaCita, clientes.nombreCliente, clientes.apellidosCliente\n"
					+ "FROM citas, clientes\n"
					+ "where citas.idClienteFK = clientes.idCliente;";
			rs = statement.executeQuery(sentencia);
			choListaCitas.removeAll();
			choListaCitas.add("Selecciona una Cita..");
			while (rs.next()) {
				String[] fechaEuropea = rs.getString("fechaCita").split("-");
				String[] quitarSegundos = rs.getString("horaCita").split(":");
				choListaCitas.add(rs.getInt("citas.idCita")+"-["+fechaEuropea[2]+"/"+fechaEuropea[1]+"/"+fechaEuropea[0]+"]-"+quitarSegundos[0]+":"+quitarSegundos[1]+"-"+rs.getString("clientes.nombreCliente")
				+"-"+rs.getString("clientes.apellidosCliente"));
			}
		} catch (SQLException e) {
			choListaCitas.removeAll();
			choListaCitas.add("Problema al cargar los datos");
		}finally {
			bd.desconectar(connection);
		}
	}


	// ============================================= DIALOGO NOTIFICACION ==========================================================
	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(230, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
	}
}
