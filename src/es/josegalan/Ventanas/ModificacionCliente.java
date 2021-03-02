package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
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

public class ModificacionCliente implements ActionListener, WindowListener, ItemListener
{

	// ========================= MODIFICAR CLIENTE PRINCIPAL ===============================================
	Frame frmModificarClienteUno = new Frame("Modificar Cliente");
	Label lblSeleccionarModificarCliente = new Label("Selecciona Cliente a Modificar:");
	Choice choListaCliente = new Choice();
	Button btnModificarUno = new Button("Modificar");
	Button btnCancelarModificarUno = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ========================= CONFIRMACION MODIFICAR CLIENTE ====================================================
	Frame frmConfirmacionModificarCliente = new Frame("Confirmacion");
	Label lblConfirmacionModificarCliente = new Label("¿Estás seguro que deseas Modificarlo?");
	Button btnSiConfirmacionModificarCliente = new Button("Si");
	Button btnNoConfirmacionModificarCliente = new Button("No");

	// ================== VENTANA MODIFICACION CLIENTES =================================================
	Frame frmModificacionClientesDos = new Frame("Formulario de Modificacion de Clientes");
	Label lblIdModificacionClienteDos = new Label("Id:");
	Label lblNombreModificacionClienteDos = new Label("Nombre:");
	Label lblApellidosModificacionClientesDos = new Label("Apellidos:");
	Label lblDniModificacionClientesDos = new Label("DNI:");
	Label lblDireccionModificacionClientesDos = new Label("Dirección:");
	Label lblSexoModificacionClientesDos = new Label("Sexo:");
	TextField txtIdModificacionClientesDos = new TextField(20);
	TextField txtNombreModificacionClientesDos = new TextField(20);
	TextField txtApellidosModificacionClientesDos = new TextField(20);
	TextField txtDniModificacionClientesDos = new TextField(20);
	TextField txtDireccionModificacionClientesDos = new TextField(20);
	CheckboxGroup chkGeneroDos = new CheckboxGroup();
	Checkbox chkHombreDos = new Checkbox("Hombre",false, chkGeneroDos);
	Checkbox chkMujerDos = new Checkbox("Mujer", false, chkGeneroDos);
	String eleccionDos = "";
	Button btnModificacionClientesDos = new Button("Modificar");
	Button btnCancelarModificacionClientesDos = new Button("Cancelar");
	Panel pnlUno = new Panel();
	Panel pnlDos = new Panel();
	Panel pnlTres = new Panel();
	Panel pnlCuatro = new Panel();
	Panel pnlCinco = new Panel();
	Panel pnlSeis = new Panel();
	Panel pnlSiete = new Panel();
	Panel pnlOcho = new Panel();
	Panel pnlNueve = new Panel();
	Panel pnlDiez = new Panel();
	Panel pnlOnce = new Panel();
	Panel pnlDoce = new Panel();
	Panel pnlTrece = new Panel();
	Panel pnlCatorce = new Panel();

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgClientesModificado = new Dialog(frmConfirmacionModificarCliente, "Operacion Correcta", true);
	Label lblModificadoCorrectamente = new Label("Cliente Modificado Correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorModificarCliente = new Dialog(frmConfirmacionModificarCliente, "Error", true);
	Label lblErrorModificarCliente = new Label("Error al Modificar Cliente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorModificarSeleccionarCliente = new Dialog(frmModificarClienteUno, "Error", true);
	Label lblErrorModificarSeleccionarCliente = new Label("Selecciona un cliente válido");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificacionCliente() 
	{
		frmModificarClienteUno.setLayout(new FlowLayout());
		frmModificarClienteUno.setSize(270, 130);
		frmModificarClienteUno.setBackground(clFondo);
		frmModificarClienteUno.add(lblSeleccionarModificarCliente);
		cargarListadoClientes(choListaCliente);
		choListaCliente.setBackground(Color.WHITE);
		frmModificarClienteUno.add(choListaCliente);
		btnModificarUno.setBackground(Color.WHITE);
		btnModificarUno.addActionListener(this);
		btnCancelarModificarUno.setBackground(Color.WHITE);
		btnCancelarModificarUno.addActionListener(this);
		frmModificarClienteUno.add(btnModificarUno);
		frmModificarClienteUno.add(btnCancelarModificarUno);
		frmModificarClienteUno.addWindowListener(this);
		frmModificarClienteUno.setResizable(false);
		frmModificarClienteUno.setLocationRelativeTo(null);
		frmModificarClienteUno.setVisible(true);
	}

	// ===================================== ITEM LISTENER ===============================================================
	@Override
	public void itemStateChanged(ItemEvent e) {
		if ("Hombre".equals(e.getItem()))
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				eleccionDos="Hombre";
			}
		}
		else if ("Mujer".equals(e.getItem())) 
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				eleccionDos="Mujer";
			}
		}
	}

	// =================================== WINDOWS LISTENER ================================================================
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (frmModificarClienteUno.isActive()) {
			frmModificarClienteUno.setVisible(false);
			frmModificarClienteUno.removeWindowListener(this);
			btnModificarUno.removeActionListener(this);
			btnCancelarModificarUno.removeActionListener(this);
		}else if (frmModificacionClientesDos.isActive()) {
			frmModificacionClientesDos.setVisible(false);
			frmModificacionClientesDos.removeWindowListener(this);
			chkHombreDos.removeItemListener(this);
			chkMujerDos.removeItemListener(this);
			btnModificacionClientesDos.removeActionListener(this);
			btnCancelarModificacionClientesDos.removeActionListener(this);
		}else if (frmConfirmacionModificarCliente.isActive()) {
			frmConfirmacionModificarCliente.setVisible(false);
			frmConfirmacionModificarCliente.removeWindowListener(this);
			btnSiConfirmacionModificarCliente.removeActionListener(this);
			btnNoConfirmacionModificarCliente.removeActionListener(this);
		}else if (dlgClientesModificado.isActive()) {
			frmConfirmacionModificarCliente.setVisible(false);
			frmConfirmacionModificarCliente.removeWindowListener(this);
			btnSiConfirmacionModificarCliente.removeActionListener(this);
			btnNoConfirmacionModificarCliente.removeActionListener(this);
			frmModificacionClientesDos.setVisible(false);
			frmModificacionClientesDos.removeWindowListener(this);
			chkHombreDos.removeItemListener(this);
			chkMujerDos.removeItemListener(this);
			btnModificacionClientesDos.removeActionListener(this);
			frmModificarClienteUno.setVisible(false);
			frmModificarClienteUno.removeWindowListener(this);
			btnModificarUno.removeActionListener(this);
			btnCancelarModificarUno.removeActionListener(this);
		}else if (dlgErrorModificarCliente.isActive()) {
			dlgErrorModificarCliente.setVisible(false);
			frmConfirmacionModificarCliente.setVisible(false);
			frmConfirmacionModificarCliente.removeWindowListener(this);
			btnSiConfirmacionModificarCliente.removeActionListener(this);
			btnNoConfirmacionModificarCliente.removeActionListener(this);
		}else if (dlgErrorModificarSeleccionarCliente.isActive()) {
			dlgErrorModificarSeleccionarCliente.setVisible(false);
			dlgErrorModificarSeleccionarCliente.removeWindowListener(this);
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

	// =================================================== ACTION LISTENER ======================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarModificarUno)) {
			frmModificarClienteUno.setVisible(false);
			frmModificarClienteUno.removeWindowListener(this);
			btnModificarUno.removeActionListener(this);
			btnCancelarModificarUno.removeActionListener(this);
		}else if (e.getSource().equals(btnModificarUno)) 
		{
			if (choListaCliente.getSelectedItem().equals("Selecciona un Cliente..")) {
				creacionDialogoNotificacion(dlgErrorModificarSeleccionarCliente, lblErrorModificarSeleccionarCliente);
				dlgErrorModificarSeleccionarCliente.setVisible(true);
			}else {
				String[] datos = (cargarDatosCliente(choListaCliente.getSelectedItem().split("-")[0])).split("-");
				cargarVentanaEdicionClientes(datos);
			}
		}
		else if (e.getSource().equals(btnCancelarModificacionClientesDos)) 
		{
			frmModificacionClientesDos.setVisible(false);
			frmModificacionClientesDos.removeWindowListener(this);
			chkHombreDos.removeItemListener(this);
			chkMujerDos.removeItemListener(this);
			btnModificacionClientesDos.removeActionListener(this);
			btnCancelarModificacionClientesDos.removeActionListener(this);
		}
		else if (e.getSource().equals(btnModificacionClientesDos)) {
			creacionVentanaConfirmacion(frmConfirmacionModificarCliente, lblConfirmacionModificarCliente, btnSiConfirmacionModificarCliente, btnNoConfirmacionModificarCliente);
		}
		else if (e.getSource().equals(btnNoConfirmacionModificarCliente)) 
		{
			frmConfirmacionModificarCliente.setVisible(false);
			frmConfirmacionModificarCliente.removeWindowListener(this);
			btnSiConfirmacionModificarCliente.removeActionListener(this);
			btnNoConfirmacionModificarCliente.removeActionListener(this);
		}
		else if (e.getSource().equals(btnSiConfirmacionModificarCliente)) {
			creacionDialogoNotificacion(dlgErrorModificarCliente, lblErrorModificarCliente);
			if (comprobacionDatosIntroducidosModificacionCliente(txtNombreModificacionClientesDos, txtApellidosModificacionClientesDos, txtDniModificacionClientesDos, txtDireccionModificacionClientesDos, eleccionDos)) 
			{
				bd = new BaseDatos();
				connection = bd.conectar();
				try
				{
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sentencia = "UPDATE clientes SET nombreCliente = '"+txtNombreModificacionClientesDos.getText()+"',"
							+ "apellidosCliente = '"+txtApellidosModificacionClientesDos.getText()+"',"
							+ " dniCliente = '"+txtDniModificacionClientesDos.getText()+"', "
							+ "direccionCliente = '"+txtDireccionModificacionClientesDos.getText()+"', "
							+ "sexoCliente = '"+eleccionDos+"' "
							+ "WHERE idCliente = "+txtIdModificacionClientesDos.getText()+"";
					statement.executeUpdate(sentencia);
					creacionDialogoNotificacion(dlgClientesModificado, lblModificadoCorrectamente);
					dlgClientesModificado.setVisible(true);
				} catch (SQLException e1)
				{
					lblErrorModificarCliente.setText("Error al insertar");
					dlgErrorModificarCliente.setVisible(true);
				}finally {
					bd.desconectar(connection);
				}
			}
			else 
			{
				lblErrorModificarCliente.setText("Datos incorrectos");
				dlgErrorModificarCliente.setVisible(true);
			}
		}
	}

	// =================================== CARGAR VENTANA EDICICION DATOS CLIENTE====================================
	public void cargarVentanaEdicionClientes(String[] datos) {
		frmModificacionClientesDos.setSize(400, 300);
		frmModificacionClientesDos.setLayout(new GridLayout(7,2));
		pnlTrece.add(lblIdModificacionClienteDos);
		txtIdModificacionClientesDos.setEditable(false);
		txtIdModificacionClientesDos.setText(datos[0]);
		pnlCatorce.add(txtIdModificacionClientesDos);
		pnlUno.add(lblNombreModificacionClienteDos);
		txtNombreModificacionClientesDos.setText(datos[1]);
		pnlDos.add(txtNombreModificacionClientesDos);
		pnlTres.add(lblApellidosModificacionClientesDos);
		txtApellidosModificacionClientesDos.setText(datos[2]);
		pnlCuatro.add(txtApellidosModificacionClientesDos);
		pnlCinco.add(lblDniModificacionClientesDos);
		txtDniModificacionClientesDos.setText(datos[3]);
		pnlSeis.add(txtDniModificacionClientesDos);
		pnlSiete.add(lblDireccionModificacionClientesDos);
		pnlOcho.add(txtDireccionModificacionClientesDos);
		txtDireccionModificacionClientesDos.setText(datos[4]);
		pnlNueve.add(lblSexoModificacionClientesDos);
		if (datos[5].equals("Hombre")) {
			chkHombreDos.setState(true);
			eleccionDos= "Hombre";
		}else {
			chkMujerDos.setState(true);
			eleccionDos= "Mujer";
		}
		chkHombreDos.addItemListener(this);
		chkMujerDos.addItemListener(this);
		pnlDiez.add(chkHombreDos);
		pnlDiez.add(chkMujerDos);
		btnModificacionClientesDos.setBackground(Color.WHITE);
		btnModificacionClientesDos.addActionListener(this);
		pnlOnce.add(btnModificacionClientesDos);
		btnCancelarModificacionClientesDos.setBackground(Color.WHITE);
		btnCancelarModificacionClientesDos.addActionListener(this);
		pnlDoce.add(btnCancelarModificacionClientesDos);
		frmModificacionClientesDos.add(pnlTrece);
		frmModificacionClientesDos.add(pnlCatorce);
		frmModificacionClientesDos.add(pnlUno);
		frmModificacionClientesDos.add(pnlDos);
		frmModificacionClientesDos.add(pnlTres);
		frmModificacionClientesDos.add(pnlCuatro);
		frmModificacionClientesDos.add(pnlCinco);
		frmModificacionClientesDos.add(pnlSeis);
		frmModificacionClientesDos.add(pnlSiete);
		frmModificacionClientesDos.add(pnlOcho);
		frmModificacionClientesDos.add(pnlNueve);
		frmModificacionClientesDos.add(pnlDiez);
		frmModificacionClientesDos.add(pnlOnce);
		frmModificacionClientesDos.add(pnlDoce);
		frmModificacionClientesDos.addWindowListener(this);
		frmModificacionClientesDos.setBackground(clFondo);
		frmModificacionClientesDos.setResizable(false);
		frmModificacionClientesDos.setLocationRelativeTo(null);
		frmModificacionClientesDos.setVisible(true);
	}

	// =================================== DATOS DEL CLIENTE SELECCIONADO ===========================================
	public String cargarDatosCliente(String id) {
		bd= new BaseDatos();
		connection = bd.conectar();
		String valores = "";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM clientes WHERE idCliente = "+id+";";
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				valores = rs.getInt("idCliente")+"-"+rs.getString("nombreCliente")+"-"+rs.getString("apellidosCliente")+"-"+
						rs.getString("dniCliente")+"-"+rs.getString("direccionCliente")+"-"+rs.getString("sexoCliente");
			}
		} catch (SQLException e) {
			valores = "";	
		}finally {
			bd.desconectar(connection);
		}
		return valores;
	}

	// ==================================================== CARGAR LISTADO CLIENTES ====================================
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

	// ======================================= COMPROBACION DATOS INTRODUCIDOS ============================================================
	public boolean comprobacionDatosIntroducidosModificacionCliente(TextField nombre, TextField apellidos, TextField dni, TextField direccion, String eleccion) 
	{
		boolean booleano = false;
		if( (nombre.getText().length()!=0) && (apellidos.getText().length() != 0) && (dni.getText().length()!=0) && (direccion.getText().length()!=0) && (eleccion.length()!=0) ) {
			if (dni.getText().length() == 9)
			{
				booleano = true;
			}
		}
		return booleano;
	}

}
