package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.josegalan.BaseDatos.BaseDatos;

public class AñadirAsignaciones implements WindowListener,ActionListener
{

	Frame frmAsignaciones = new Frame("Alta Asignación Tratamiento");
	Label lblCitade = new Label();
	Label lblSeleccionaTratamiento = new Label("Selecciona Tratamientos: ");
	Button btnAlta = new Button("Alta");
	Button btnCancelar = new Button("Cancelar");
	Label lblTratamientos = new Label("Tratamientos en la cita");
	TextArea txaListadoTratamientos = new TextArea(6,33);
	Choice choListaTratamientos = new Choice();
	Color clFondo = new Color(204,229,255);
	String datoCita;

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgTratamientoInsertado = new Dialog(frmAsignaciones, "Operacion Correcta", true);
	Label lblAnadidaCorrectamente = new Label("Tratamiento Insertado Correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarTratamiento = new Dialog(frmAsignaciones, "Error", true);
	Label lblErrorAnadidoTratamiento = new Label("Error al añadir Tratamiento");


	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AñadirAsignaciones(String idCita, String[] clienteSeleccionado) {
		datoCita = idCita;
		frmAsignaciones.setLayout(new FlowLayout());
		lblCitade.setText("Cita de " + clienteSeleccionado[1]+"     ");
		frmAsignaciones.add(lblCitade);
		frmAsignaciones.add(lblSeleccionaTratamiento);
		cargarListadoTratamientos(choListaTratamientos);
		choListaTratamientos.setBackground(Color.white);
		frmAsignaciones.add(choListaTratamientos);
		btnAlta.addActionListener(this);
		btnCancelar.addActionListener(this);
		btnAlta.setBackground(Color.WHITE);
		btnCancelar.setBackground(Color.WHITE);
		frmAsignaciones.add(btnAlta);
		frmAsignaciones.add(lblTratamientos);
		txaListadoTratamientos.setEditable(false);
		txaListadoTratamientos.setBackground(Color.WHITE);
		rellenarTextArea(idCita);
		frmAsignaciones.add(txaListadoTratamientos);
		frmAsignaciones.add(btnCancelar);
		frmAsignaciones.setBackground(clFondo);
		frmAsignaciones.addWindowListener(this);
		frmAsignaciones.setSize(280, 300);
		frmAsignaciones.setResizable(false);
		frmAsignaciones.setLocationRelativeTo(null);
		frmAsignaciones.setVisible(true);
	}

	public void rellenarTextArea(String citaSeleccionada) {
		bd= new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "select tratamientos.nombreTratamiento from tratamientos, tratamiento_citas where tratamientos.idTratamiento = tratamiento_citas.idTratamientoFK AND idCitaFK = "+citaSeleccionada+" ;";
			rs = statement.executeQuery(sentencia);
			txaListadoTratamientos.selectAll();
			txaListadoTratamientos.setText("");
			txaListadoTratamientos.append("Tratamientos\n");
			txaListadoTratamientos.append("======================\n");
			while (rs.next()) {
				txaListadoTratamientos.append(rs.getString("tratamientos.nombreTratamiento")+"\n");
			}
		} catch (SQLException e) {
			txaListadoTratamientos.selectAll();
			txaListadoTratamientos.setText("");
			txaListadoTratamientos.append("Error al cargar los datos");	
		}finally {
			bd.desconectar(connection);
		}
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



	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (frmAsignaciones.isActive()) {
			frmAsignaciones.setVisible(false);
			frmAsignaciones.removeWindowListener(this);
			btnCancelar.removeActionListener(this);
			btnAlta.removeActionListener(this);
		} else if (dlgTratamientoInsertado.isActive()) {
			dlgTratamientoInsertado.setVisible(false);
			dlgTratamientoInsertado.removeWindowListener(this);
		} else if (dlgErrorInsertarTratamiento.isActive()) {
			dlgErrorInsertarTratamiento.setVisible(false);
			dlgErrorInsertarTratamiento.removeWindowListener(this);
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
		if (e.getSource().equals(btnCancelar)) {
			frmAsignaciones.setVisible(false);
			frmAsignaciones.removeWindowListener(this);
			btnCancelar.removeActionListener(this);
			btnAlta.removeActionListener(this);
		} else if (e.getSource().equals(btnAlta)) {
			creacionDialogoNotificacion(dlgErrorInsertarTratamiento, lblErrorAnadidoTratamiento);
			if (choListaTratamientos.getSelectedItem().equals("Selecciona un Tratamiento..")) 
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
					sentencia = "INSERT INTO tratamiento_citas VALUES(null, "+datoCita+", "+choListaTratamientos.getSelectedItem().split("-")[0]+");";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgTratamientoInsertado, lblAnadidaCorrectamente);
					dlgTratamientoInsertado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorAnadidoTratamiento.setText("Error al insertar");
					dlgErrorInsertarTratamiento.setVisible(true);
				}finally {
					bd.desconectar(connection);
					rellenarTextArea(datoCita);
				}
			}
		}
	}

	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(260, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
	}

}
