package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.josegalan.BaseDatos.BaseDatos;

public class AltaCita implements ActionListener, WindowListener
{
	// ========================================= VENTANA PRINCIPAL =======================================================
	Frame frmAltaCitas = new Frame("Alta Citas");
	Label lblFechaCitas = new Label("Fecha:");
	Label lblHoraCitas = new Label("Hora:");
	Label lblSeleccionaCliente = new Label("Selecciona un Cliente:");
	Choice cholistaClientes = new Choice();
	Choice cholistaHoras = new Choice();
	TextField txtFecha = new TextField(""+obtenerFechaHoy()+"", 20);
	TextField txtBuscarNombre = new TextField(20);
	Button btnBuscar = new Button("Buscar");
	Button btnAltaCitas = new Button("Alta Cita");
	Button btnCancelarCitas = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);
	String[] horas = {"9:00","9:30","10:00", "10:30","11:00","11:30","12:00","12:30","13:00","13:30","17:00","17:30","18:00",
			"18:30","19:00","19:30","20:00"};

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgCitasInsertado = new Dialog(frmAltaCitas, "Operacion Correcta", true);
	Label lblAnadidaCorrectamente = new Label("Cita añadida correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarCita = new Dialog(frmAltaCitas, "Error", true);
	Label lblErrorAnadidoCita = new Label("Faltan Datos");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	// ===================================== CONSTRUCTOR ===================================================================
	public AltaCita() 
	{
		frmAltaCitas.setBackground(clFondo);
		frmAltaCitas.setSize(400, 170);
		frmAltaCitas.setLayout(new FlowLayout());
		frmAltaCitas.add(lblFechaCitas);
		frmAltaCitas.add(txtFecha);
		frmAltaCitas.add(lblHoraCitas);
		for (String s : horas) {
			cholistaHoras.add(s);
		}
		cargarListadoClientes(cholistaClientes);
		cholistaHoras.setBackground(Color.WHITE);
		frmAltaCitas.add(cholistaHoras);
		frmAltaCitas.add(lblSeleccionaCliente);
		cholistaClientes.setBackground(Color.WHITE);
		frmAltaCitas.add(cholistaClientes);
		btnAltaCitas.setBackground(Color.WHITE);
		btnAltaCitas.addActionListener(this);
		frmAltaCitas.add(btnAltaCitas);
		btnCancelarCitas.addActionListener(this);
		btnCancelarCitas.setBackground(Color.WHITE);
		frmAltaCitas.add(btnCancelarCitas);
		frmAltaCitas.addWindowListener(this);
		frmAltaCitas.setLocationRelativeTo(null);
		frmAltaCitas.setResizable(false);
		frmAltaCitas.setVisible(true);
	}

	// ============================================== WINDOW LISTENER ========================================================
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgErrorInsertarCita.isActive()) 
		{
			dlgErrorInsertarCita.setVisible(false);
		}
		else 
		{
			frmAltaCitas.setVisible(false);
			frmAltaCitas.removeWindowListener(this);
			dlgCitasInsertado.setVisible(false);
			dlgCitasInsertado.removeWindowListener(this);
			btnAltaCitas.removeActionListener(this);
			btnCancelarCitas.addActionListener(this);
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

	// ================================================== ACTION LISTENER ============================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarCitas)) {
			frmAltaCitas.setVisible(false);
			frmAltaCitas.removeWindowListener(this);
			btnAltaCitas.removeActionListener(this);
			btnCancelarCitas.addActionListener(this);
		}else if (e.getSource().equals(btnAltaCitas)) {
			creacionDialogoNotificacion(dlgErrorInsertarCita, lblErrorAnadidoCita);
			if (txtFecha.getText().length()!=0) 
			{
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "INSERT INTO citas VALUES (null, '"+ txtFecha.getText()+ "', '" + cholistaHoras.getSelectedItem() +
							"', "+ cholistaClientes.getSelectedItem().split("-")[0] +");";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgCitasInsertado, lblAnadidaCorrectamente);
					dlgCitasInsertado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorAnadidoCita.setText("Error al insertar");
					dlgErrorInsertarCita.setVisible(true);
				}finally {
					bd.desconectar(connection);
				}
			}
			else 
			{
				lblErrorAnadidoCita.setText("Datos incorrectos");
				dlgErrorInsertarCita.setVisible(true);
			}
		}
	}

	// =================================================== CARGAR DATOS EN CHOICE ====================================================================
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

	// ============================================= DIALOGO NOTIFICACION ==========================================================
	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(230, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
	}
	
	public String obtenerFechaHoy() {
		Calendar fecha = new GregorianCalendar();
		  
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
  
        return (año + "-" + (mes+1) + "-" + dia);
	}

}
