package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
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

	public Clientes() {
		
	}


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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowClosing(WindowEvent e) {
		if (frmAltaClientes.isActive()) {
			frmAltaClientes.setVisible(false);
		}
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

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
}
