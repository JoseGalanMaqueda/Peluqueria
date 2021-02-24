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

public class AltaClientes implements WindowListener, ActionListener, ItemListener
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

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgClienteInsertado = new Dialog(frmAltaClientes, "Operacion Correcta", true);
	Label lblAnadidoCorrectamente = new Label("Cliente añadido correctamente");

	// ============================== DIALOGO NOTIFICACION ==================================
	Dialog dlgErrorInsertarCliente = new Dialog(frmAltaClientes, "Error", true);
	Label lblErrorAnadidoCliente = new Label("Faltan Datos");

	// =========================================== BASES DE DATOS ==========================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaClientes() {
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

	public static void main(String[] args) {
		new AltaClientes();
	}

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

	// ===================================== ACTION PERFORMED ============================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarAltaClientes)) 
		{
			frmAltaClientes.setVisible(false);
		}
		else if (e.getSource().equals(btnAltaClientes)) 
		{
			creacionDialogoNotificacion(dlgErrorInsertarCliente, lblErrorAnadidoCliente);
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
					creacionDialogoNotificacion(dlgClienteInsertado, lblAnadidoCorrectamente);
					dlgClienteInsertado.setVisible(true);

				} catch (SQLException e1)
				{
					lblErrorAnadidoCliente.setText("Error al insertar");
					dlgErrorInsertarCliente.setVisible(true);
				}
			}
			else 
			{
				lblErrorAnadidoCliente.setText("Datos incorrectos");
				dlgErrorInsertarCliente.setVisible(true);
			}
		}
	}

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

	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(230, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgErrorInsertarCliente.isActive()) 
		{
			dlgErrorInsertarCliente.setVisible(false);
		}
		else 
		{
			frmAltaClientes.setVisible(false);
			frmAltaClientes.removeWindowListener(this);
			dlgClienteInsertado.setVisible(false);
			dlgClienteInsertado.removeWindowListener(this);
			btnAltaClientes.removeActionListener(this);
			btnCancelarAltaClientes.addActionListener(this);
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
