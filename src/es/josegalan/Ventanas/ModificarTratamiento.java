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

public class ModificarTratamiento implements ActionListener, WindowListener
{

	// ========================= MODIFICAR TRATAMIENTO PRINCIPAL ===============================================
	Frame frmModificarTratamientoUno = new Frame("Modificar Cliente");
	Label lblSeleccionarModificarTratamiento = new Label("Selecciona Tratamiento a Modificar:");
	Choice choListaTratamientos = new Choice();
	Button btnModificarUno = new Button("Modificar");
	Button btnCancelarModificarUno = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ===================================== VENTANA ALTA CLIENTES =======================================================
	Frame frmModificarTratamientoDos = new Frame("Modificacion Tratamientos");
	Label lblIdModificacionTratamientoDos = new Label("Id:");
	Label lblNombreModificacionTratamientoDos = new Label("Nombre:");
	Label lblPrecioModificacionTratamientoDos = new Label("Precio:");
	Label lblDescipcionModificacionTratamientoDos = new Label("Descripcion:");
	TextField txtIdModificacionTratamientoDos = new TextField(5);
	TextField txtNombreModificacionTratamientoDos = new TextField(14);
	String[] precios = {"0.00","1.00","1.50","2.00","2.50","3.00","3.50","4.00","4.50","5.00","5.50","6.00","6.50","7.00","7.50","8.00","8.50","9.00","9.50"};
	Choice listaPrecios = new Choice(); 
	TextArea txaDescripcionModificacion = new TextArea(7, 45);
	Button btnModificacionTratamientoDos = new Button("Modificar");
	Button btnCancelarModificacionTratamientoDos = new Button("Cancelar");


	// ========================= CONFIRMACION MODIFICAR TRATAMIENTOS ====================================================
	Frame frmConfirmacionModificarTratamiento = new Frame("Confirmacion");
	Label lblConfirmacionModificarTratamiento = new Label("¿Estás seguro que deseas Modificarlo?");
	Button btnSiConfirmacionModificarTratamiento = new Button("Si");
	Button btnNoConfirmacionModificarTratamiento = new Button("No");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgTratamientoModificado = new Dialog(frmConfirmacionModificarTratamiento, "Operacion Correcta", true);
	Label lblModificadoCorrectamente = new Label("Tratamiento Modificado Correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorModificarTratamiento = new Dialog(frmConfirmacionModificarTratamiento, "Error", true);
	Label lblErrorModificarTratamiento = new Label("Error al Modificar Tratamiento");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorModificarSeleccionarTratamiento = new Dialog(frmModificarTratamientoUno, "Error", true);
	Label lblErrorModificarSeleccionarTratamiento = new Label("Selecciona un Tratamiento válido");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarTratamiento() 
	{
		frmModificarTratamientoUno.setLayout(new FlowLayout());
		frmModificarTratamientoUno.setSize(270, 130);
		frmModificarTratamientoUno.setBackground(clFondo);
		frmModificarTratamientoUno.add(lblSeleccionarModificarTratamiento);
		cargarListadoTratamientos(choListaTratamientos);
		choListaTratamientos.setBackground(Color.WHITE);
		frmModificarTratamientoUno.add(choListaTratamientos);
		btnModificarUno.setBackground(Color.WHITE);
		btnModificarUno.addActionListener(this);
		btnCancelarModificarUno.setBackground(Color.WHITE);
		btnCancelarModificarUno.addActionListener(this);
		frmModificarTratamientoUno.add(btnModificarUno);
		frmModificarTratamientoUno.add(btnCancelarModificarUno);
		frmModificarTratamientoUno.addWindowListener(this);
		frmModificarTratamientoUno.setResizable(false);
		frmModificarTratamientoUno.setLocationRelativeTo(null);
		frmModificarTratamientoUno.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (frmModificarTratamientoUno.isActive()) 
		{
			frmModificarTratamientoUno.setVisible(false);
			frmModificarTratamientoUno.removeWindowListener(this);
			btnModificarUno.removeActionListener(this);
			btnCancelarModificarUno.removeActionListener(this);
		}else if (dlgErrorModificarSeleccionarTratamiento.isActive()) {
			dlgErrorModificarSeleccionarTratamiento.setVisible(false);
			dlgErrorModificarSeleccionarTratamiento.removeWindowListener(this);
		}else if (frmModificarTratamientoDos.isActive()) {
			frmModificarTratamientoDos.setVisible(false);
			frmModificarTratamientoDos.removeWindowListener(this);
			btnModificacionTratamientoDos.removeActionListener(this);
			btnCancelarModificacionTratamientoDos.removeActionListener(this);
		}else if (frmConfirmacionModificarTratamiento.isActive()) {
			frmConfirmacionModificarTratamiento.setVisible(false);
			frmConfirmacionModificarTratamiento.removeWindowListener(this);
			btnSiConfirmacionModificarTratamiento.removeActionListener(this);
			btnNoConfirmacionModificarTratamiento.removeActionListener(this);
		}else if (dlgErrorModificarTratamiento.isActive()) {
			dlgErrorModificarTratamiento.setVisible(false);
			frmConfirmacionModificarTratamiento.setVisible(false);
			frmConfirmacionModificarTratamiento.removeWindowListener(this);
			btnSiConfirmacionModificarTratamiento.removeActionListener(this);
			btnNoConfirmacionModificarTratamiento.removeActionListener(this);
		}else if (dlgTratamientoModificado.isActive()) {
			frmConfirmacionModificarTratamiento.setVisible(false);
			frmConfirmacionModificarTratamiento.removeWindowListener(this);
			btnSiConfirmacionModificarTratamiento.removeActionListener(this);
			btnNoConfirmacionModificarTratamiento.removeActionListener(this);
			frmModificarTratamientoDos.setVisible(false);
			frmModificarTratamientoDos.removeWindowListener(this);
			btnModificacionTratamientoDos.removeActionListener(this);
			frmModificarTratamientoUno.setVisible(false);
			frmModificarTratamientoUno.removeWindowListener(this);
			btnModificarUno.removeActionListener(this);
			btnCancelarModificarUno.removeActionListener(this);
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
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(btnCancelarModificarUno)) 
		{
			frmModificarTratamientoUno.setVisible(false);
			frmModificarTratamientoUno.removeWindowListener(this);
			btnModificarUno.removeActionListener(this);
			btnCancelarModificarUno.removeActionListener(this);
		}
		else if (e.getSource().equals(btnModificarUno)) 
		{
			if (choListaTratamientos.getSelectedItem().equals("Selecciona un Tratamiento..")) {
				creacionDialogoNotificacion(dlgErrorModificarSeleccionarTratamiento, lblErrorModificarSeleccionarTratamiento);
				dlgErrorModificarSeleccionarTratamiento.setVisible(true);
			}else {
				String[] datos = (cargarDatosTratamientos(choListaTratamientos.getSelectedItem().split("-")[0])).split("-");
				cargarVentanaEdicionTratamiento(datos);
			}
		}else if (e.getSource().equals(btnCancelarModificacionTratamientoDos)) {
			frmModificarTratamientoDos.setVisible(false);
			frmModificarTratamientoDos.removeWindowListener(this);
			btnModificacionTratamientoDos.removeActionListener(this);
			btnCancelarModificacionTratamientoDos.removeActionListener(this);
		}else if (e.getSource().equals(btnModificacionTratamientoDos)) 
		{
			creacionVentanaConfirmacion(frmConfirmacionModificarTratamiento, lblConfirmacionModificarTratamiento, btnSiConfirmacionModificarTratamiento, btnNoConfirmacionModificarTratamiento);
		}
		else if (e.getSource().equals(btnNoConfirmacionModificarTratamiento)) 
		{
			frmConfirmacionModificarTratamiento.setVisible(false);
			frmConfirmacionModificarTratamiento.removeWindowListener(this);
			btnSiConfirmacionModificarTratamiento.removeActionListener(this);
			btnNoConfirmacionModificarTratamiento.removeActionListener(this);
		}
		else if (e.getSource().equals(btnSiConfirmacionModificarTratamiento)) 
		{
			creacionDialogoNotificacion(dlgErrorModificarTratamiento, lblErrorModificarTratamiento);
			if (comprobacionDatos(txtNombreModificacionTratamientoDos)) 
			{
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "UPDATE tratamientos SET nombreTratamiento = '"+txtNombreModificacionTratamientoDos.getText()+"',"
							+ "descripcionTratamiento = '"+txaDescripcionModificacion.getText()+"',"
							+ " precioTratamiento = "+listaPrecios.getSelectedItem()+" "
							+ "WHERE idTratamiento = "+txtIdModificacionTratamientoDos.getText()+";";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgTratamientoModificado, lblModificadoCorrectamente);
					dlgTratamientoModificado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorModificarTratamiento.setText("Error al Modificar");
					dlgErrorModificarTratamiento.setVisible(true);
				}finally {
					bd.desconectar(connection);
				}
			}
			else 
			{
				lblErrorModificarTratamiento.setText("Datos incorrectos");
				dlgErrorModificarTratamiento.setVisible(true);
			}
		}
	}


	// =================================== DATOS DEL CLIENTE SELECCIONADO ===========================================
	public String cargarDatosTratamientos(String id) {
		bd= new BaseDatos();
		connection = bd.conectar();
		String valores = "";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM tratamientos WHERE idTratamiento = "+id+";";
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				valores = rs.getInt("idTratamiento")+"-"+rs.getString("nombreTratamiento")+"-"+rs.getString("descripcionTratamiento")+"-"+
						rs.getDouble("precioTratamiento");
			}
		} catch (SQLException e) {
			valores = "";	
		}finally {
			bd.desconectar(connection);
		}
		return valores;
	}

	// ================================================ CARGAR VENTANA CON DATOS ======================================
	public void cargarVentanaEdicionTratamiento(String[] datos) 
	{
		frmModificarTratamientoDos.setSize(400, 270);
		frmModificarTratamientoDos.setLayout(new FlowLayout());
		frmModificarTratamientoDos.add(lblIdModificacionTratamientoDos);
		txtIdModificacionTratamientoDos.setEditable(false);
		txtIdModificacionTratamientoDos.setText(datos[0]);
		frmModificarTratamientoDos.add(txtIdModificacionTratamientoDos);
		frmModificarTratamientoDos.add(lblNombreModificacionTratamientoDos);
		txtNombreModificacionTratamientoDos.setText(datos[1]);
		frmModificarTratamientoDos.add(txtNombreModificacionTratamientoDos);
		frmModificarTratamientoDos.add(lblPrecioModificacionTratamientoDos);
		for (String d : precios) {
			listaPrecios.add((d));
		}
		listaPrecios.select(datos[3]+"0");
		listaPrecios.setBackground(Color.WHITE);
		frmModificarTratamientoDos.add(listaPrecios);
		frmModificarTratamientoDos.add(lblDescipcionModificacionTratamientoDos);
		txaDescripcionModificacion.setText(datos[2]);
		frmModificarTratamientoDos.add(txaDescripcionModificacion);
		btnModificacionTratamientoDos.setBackground(Color.WHITE);
		btnModificacionTratamientoDos.addActionListener(this);
		frmModificarTratamientoDos.add(btnModificacionTratamientoDos);
		btnCancelarModificacionTratamientoDos.setBackground(Color.WHITE);
		btnCancelarModificacionTratamientoDos.addActionListener(this);
		frmModificarTratamientoDos.add(btnCancelarModificacionTratamientoDos);
		frmModificarTratamientoDos.addWindowListener(this);
		frmModificarTratamientoDos.setLocationRelativeTo(null);
		frmModificarTratamientoDos.setResizable(false);
		frmModificarTratamientoDos.setBackground(clFondo);
		frmModificarTratamientoDos.setVisible(true);
	}
	
	public boolean comprobacionDatos(TextField nombre) 
	{
		boolean booleano = false;
		if (nombre.getText().length()!=0) {
			booleano = true;
		}
		return booleano;
	}

	// ==================================================== CARGAR LISTADO CLIENTES ====================================
	public void cargarListadoTratamientos(Choice cholistaTratamientos) 
	{
		bd = new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM tratamientos;";
			rs = statement.executeQuery(sentencia);
			cholistaTratamientos.removeAll();
			cholistaTratamientos.add("Selecciona un Tratamiento..");
			while (rs.next()) {
				cholistaTratamientos.add(rs.getInt("idTratamiento")+"-"+rs.getString("nombreTratamiento"));
			}
		} catch (SQLException e) {
			cholistaTratamientos.removeAll();
			cholistaTratamientos.add("Problema al cargar los datos");
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
}
