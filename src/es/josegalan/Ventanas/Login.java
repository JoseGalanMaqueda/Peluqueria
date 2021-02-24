package es.josegalan.Ventanas;

import java.awt.Button;
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

import es.josegalan.BaseDatos.BaseDatos;

public class Login implements WindowListener, ActionListener
{

	// ============================== VENTANA PRINCIPAL =========================================
	Frame frmVentanaLogin = new Frame("Login");
	Label lblnombreUsuario = new Label("Usuario:");
	Label lblpassword = new Label("Contraseña:");
	TextField txtNombreUsuario = new TextField(20);
	TextField txtPassword = new TextField(20);
	Button btnAcceder = new Button("Acceder");
	Button btnLimpiar = new Button("Limpiar");
	Color colorPrincipal = new Color(204,229,255);

	// =============================== DIALOGO ERROR =============================================
	Dialog dlgError = new Dialog(frmVentanaLogin, "Error", true);
	Label lblError = new Label("Datos incorrectos");

	// ================================ BASE DATOS ===============================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public Login() 
	{
		frmVentanaLogin.setLayout(new FlowLayout());
		frmVentanaLogin.addWindowListener(this);
		frmVentanaLogin.setSize(290,160);
		frmVentanaLogin.add(lblnombreUsuario);
		txtNombreUsuario.setText("admin");
		frmVentanaLogin.add(txtNombreUsuario);
		frmVentanaLogin.add(lblpassword);
		txtPassword.setEchoChar('*');
		txtPassword.setText("Studium2020;");
		frmVentanaLogin.add(txtPassword);
		btnAcceder.addActionListener(this);
		btnAcceder.setBackground(Color.WHITE);
		frmVentanaLogin.add(btnAcceder);
		btnLimpiar.setBackground(Color.WHITE);
		btnLimpiar.addActionListener(this);
		frmVentanaLogin.add(btnLimpiar);
		frmVentanaLogin.setBackground(colorPrincipal);
		frmVentanaLogin.setResizable(false);
		frmVentanaLogin.setLocationRelativeTo(null);
		frmVentanaLogin.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnLimpiar)) 
		{
			txtNombreUsuario.selectAll();
			txtNombreUsuario.setText("");
			txtPassword.selectAll();
			txtPassword.setText("");
			txtNombreUsuario.requestFocus();
		}
		else if (e.getSource().equals(btnAcceder)) 
		{
			BaseDatos bd = new BaseDatos();
			connection = bd.conectar();
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				sentencia = "SELECT * from usuarios where nombreUsuario='"+txtNombreUsuario.getText()+"' AND claveUsuario = sha2('"+txtPassword.getText()+"',256);";
				rs = statement.executeQuery(sentencia);
				if (rs.next()) 
				{
					int tipo = rs.getInt("tipoUsuario");
					new Principal(tipo);
					frmVentanaLogin.setVisible(false);
				}
				else 
				{
					dlgError.setLayout(new FlowLayout());
					dlgError.setSize(160, 120);
					dlgError.add(lblError);
					dlgError.setBackground(colorPrincipal);
					dlgError.addWindowListener(this);
					dlgError.setResizable(false);
					dlgError.setLocationRelativeTo(null);
					dlgError.setVisible(true);
				}
			} catch (SQLException e2) {
				System.out.println("Error 2"+e2.getMessage());
			}finally {
				bd.desconectar(connection);
			}

		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if (dlgError.isActive()) 
		{
			dlgError.setVisible(false);
		} else 
		{
			System.exit(0);
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
