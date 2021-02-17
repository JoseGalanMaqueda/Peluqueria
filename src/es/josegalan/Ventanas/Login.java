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
	
	public Login() 
	{
		frmVentanaLogin.setLayout(new FlowLayout());
		frmVentanaLogin.addWindowListener(this);
		frmVentanaLogin.setSize(290,160);
		frmVentanaLogin.add(lblnombreUsuario);
		frmVentanaLogin.add(txtNombreUsuario);
		frmVentanaLogin.add(lblpassword);
		txtPassword.setEchoChar('*');
		frmVentanaLogin.add(txtPassword);
		btnAcceder.addActionListener(this);
		frmVentanaLogin.add(btnAcceder);
		btnLimpiar.addActionListener(this);
		frmVentanaLogin.add(btnLimpiar);
		frmVentanaLogin.setBackground(colorPrincipal);
		frmVentanaLogin.setResizable(false);
		frmVentanaLogin.setLocationRelativeTo(null);
		frmVentanaLogin.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Login();
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
			dlgError.setLayout(new FlowLayout());
			dlgError.setSize(160, 120);
			dlgError.add(lblError);
			dlgError.setBackground(colorPrincipal);
			dlgError.addWindowListener(this);
			dlgError.setResizable(false);
			dlgError.setLocationRelativeTo(null);
			dlgError.setVisible(true);
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
