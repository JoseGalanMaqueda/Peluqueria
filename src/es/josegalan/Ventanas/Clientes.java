package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
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

public class Clientes implements WindowListener, ActionListener, ItemListener
{
	// ================== VENTANA ALTA CLIENTES =================================================
	Frame frmAltaClientes = new Frame("Formulario de Alta de Clientes");
	Label lblNombreAltaCliente = new Label("Nombre:");
	Label lblApellidosAltaClientes = new Label("Apellidos:");
	Label lblDniAltaClientes = new Label("DNI:");
	Label lblDireccionAltaClientes = new Label("Dirección:");
	Label lblSexoAltaClientes = new Label("Sexo:");
	TextField txtNombreAltaClientes = new TextField(20);
	TextField txtApellidosAltaClientes = new TextField(20);
	TextField txtDniAltaClientes = new TextField(20);
	TextField txtDireccionAltaClientes = new TextField(20);
	CheckboxGroup chkGenero = new CheckboxGroup();
	Checkbox chkHombre = new Checkbox("Hombre",false, chkGenero);
	Checkbox chkMujer = new Checkbox("Mujer", false, chkGenero);
	String eleccion = "";
	Button btnAltaClientes = new Button("Alta");
	Button btnCancelarAltaClientes = new Button("Cancelar");
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
	Color clFondo = new Color(204,229,255);

	// ========================== DIALOGO CONFIRMACION CLIENTES NUEVOS =========================
	Frame frmConfirmacionAltaClientes = new Frame("Confirmación");
	Label lblSeguroAltaCliente = new Label("¿Estas seguro de crear este cliente?");
	Button btnSiConfirmacionSeguroAlta = new Button("Si");
	Button btnNoConfirmacionSeguroAlta = new Button("No");

	//============================ CREACION DIALOGO ERROR =============================================
	Dialog dlgErrorAltaCliente = new Dialog(frmConfirmacionAltaClientes, "Error", false);
	Label lblErrorAltaCliente = new Label("Datos incorrectos");

	// =========================== CREACION DIALOGO CORRECTO INSERTAR =================================
	Dialog dlgClienteInsertado = new Dialog(frmConfirmacionAltaClientes, "Operacion Correcta", false);
	Label lblAnadidoCorrectamente = new Label("Cliente añadido correctamente");

	// =========================================== BASES DE DATOS ==========================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	// ======================================= CONSTRUCTOR ==============================================
	public Clientes() {

	}

	// ====================================== MOSTRAR VENTANA ALTA CLIENTES ==============================
	public void mostrarAltaClientes() 
	{
		frmAltaClientes.setSize(400, 300);
		frmAltaClientes.setLayout(new GridLayout(6,2));
		pnlUno.add(lblNombreAltaCliente);
		pnlDos.add(txtNombreAltaClientes);
		pnlTres.add(lblApellidosAltaClientes);
		pnlCuatro.add(txtApellidosAltaClientes);
		pnlCinco.add(lblDniAltaClientes);
		pnlSeis.add(txtDniAltaClientes);
		pnlSiete.add(lblDireccionAltaClientes);
		pnlOcho.add(txtDireccionAltaClientes);
		pnlNueve.add(lblSexoAltaClientes);
		chkHombre.addItemListener(this);
		chkMujer.addItemListener(this);
		pnlDiez.add(chkHombre);
		pnlDiez.add(chkMujer);
		btnAltaClientes.setBackground(Color.WHITE);
		btnAltaClientes.addActionListener(this);
		pnlOnce.add(btnAltaClientes);
		btnCancelarAltaClientes.setBackground(Color.WHITE);
		btnCancelarAltaClientes.addActionListener(this);
		pnlDoce.add(btnCancelarAltaClientes);
		frmAltaClientes.add(pnlUno);
		frmAltaClientes.add(pnlDos);
		frmAltaClientes.add(pnlTres);
		frmAltaClientes.add(pnlCuatro);
		frmAltaClientes.add(pnlCinco);
		frmAltaClientes.add(pnlSeis);
		frmAltaClientes.add(pnlSiete);
		frmAltaClientes.add(pnlOcho);
		frmAltaClientes.add(pnlNueve);
		frmAltaClientes.add(pnlDiez);
		frmAltaClientes.add(pnlOnce);
		frmAltaClientes.add(pnlDoce);
		frmAltaClientes.addWindowListener(this);
		frmAltaClientes.setBackground(clFondo);
		frmAltaClientes.setResizable(false);
		frmAltaClientes.setLocationRelativeTo(null);
		frmAltaClientes.setVisible(true);
	}

	// ======================================== ACTION LISTENER ==============================================
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnCancelarAltaClientes))
		{
			frmAltaClientes.setVisible(false);
		}
		else if(e.getSource().equals(btnAltaClientes)) 
		{
			creacionVentanaConfirmacion(frmConfirmacionAltaClientes, lblSeguroAltaCliente, btnSiConfirmacionSeguroAlta, btnNoConfirmacionSeguroAlta);
		}
		else if(e.getSource().equals(btnSiConfirmacionSeguroAlta)) 
		{
			insertarClientes();
		}else if (e.getSource().equals(btnNoConfirmacionSeguroAlta))
		{
			frmConfirmacionAltaClientes.setVisible(false);
		}
	}

	// ========================================== WINDOW LISTENER ==============================================
	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		if (frmAltaClientes.isActive())
		{
			frmAltaClientes.setVisible(false);
		}
		else if (frmConfirmacionAltaClientes.isActive())
		{
			frmConfirmacionAltaClientes.setVisible(false);
		}
		else if (dlgErrorAltaCliente.isActive())
		{
			dlgErrorAltaCliente.setVisible(false);
			frmConfirmacionAltaClientes.setVisible(false);
		}
		else if (dlgClienteInsertado.isActive())
		{
			dlgClienteInsertado.setVisible(false);
			frmConfirmacionAltaClientes.setVisible(false);
			frmAltaClientes.setVisible(false);
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
	
	// ========================================= COMPROBACION DATOS FORMULARIOS =======================================
	public boolean comprobacionDatosIntroducidosAltaCliente(TextField nombre, TextField apellidos, TextField dni, TextField direccion, String eleccion) 
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

	// ========================================== CREACION VENTANA CONFIRMACION ========================================
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
	
	// =================================================== INSERTAR CLIENTES EN BD =======================================================================
	public void insertarClientes()
	{
		if (comprobacionDatosIntroducidosAltaCliente(txtNombreAltaClientes, txtApellidosAltaClientes, txtDniAltaClientes, txtDireccionAltaClientes, eleccion))
		{
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sentencia = "INSERT INTO clientes VALUES (null, '"+ txtNombreAltaClientes.getText()+ "', '" +txtApellidosAltaClientes.getText() +
						"', '"+ txtDniAltaClientes.getText() +"', '" + txtDireccionAltaClientes.getText() + "', '" + eleccion +"');";
				statement.executeUpdate(sentencia);

			} catch (SQLException e)
			{
				System.out.println("Error 3 - "+e.getMessage());
			}
			finally {
				creacionDialogoNotificacion(dlgClienteInsertado, lblAnadidoCorrectamente);
				txtNombreAltaClientes.setText("");
				txtApellidosAltaClientes.setText("");
				txtDniAltaClientes.setText("");
				txtDireccionAltaClientes.setText("");
				bd.desconectar(connection);
			}
		} else
		{
			creacionDialogoNotificacion(dlgErrorAltaCliente, lblErrorAltaCliente);
		}
		
	}
	
	// =========================================== CREACION DIALOGOS DE CONFIRMACION =========================================================
	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(230, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
		dialogo.setVisible(true);
	}

	// ============================================= ITEM LISTENER ==========================================================================
	@Override
	public void itemStateChanged(ItemEvent e) {
		if ("Hombre".equals(e.getItem()))
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				eleccion="Hombre";
			}
		}
		else if ("Mujer".equals(e.getItem())) 
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				eleccion="Mujer";
			}
		}
	}
}
