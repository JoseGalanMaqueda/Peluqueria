package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Principal implements WindowListener, ActionListener
{

	//===================================== VENTANA PRINCIPAL ==================================
	Frame ventanaPrincipal = new Frame("Peluqueria Forever Young");
	Label lblCitasHoy = new Label("Citas Hoy:");
	TextArea txaCitasHoy = new TextArea(16,54);
	Button btnActualizar = new Button("Actualizar");
	Color clFondo = new Color(204,229,255);

	//===================================== MENU PRINCIPAL =====================================
	MenuBar menuPrincipal = new MenuBar();

	// ==================================== MENU CLIENTES ======================================
	Menu mnuCliente = new Menu("Clientes");
	MenuItem mniAltaCliente = new MenuItem("Nuevo Cliente");
	MenuItem mniBajaCliente = new MenuItem("Eliminar Cliente");
	MenuItem mniModificacionCliente = new MenuItem("Editar Cliente");
	MenuItem mniConsultaCliente = new MenuItem("Consultar Clientes");

	// =================================== MENU TRATAMIENTOS ===================================
	Menu mnuTratamientos = new Menu("Tratamientos");
	MenuItem mniAltaTratamiento = new MenuItem("Nuevo Tratamientos");
	MenuItem mniBajaTratamiento = new MenuItem("Eliminar Tratamiento");
	MenuItem mniModificacionTratamiento = new MenuItem("Editar Tratamiento");
	MenuItem mniConsultaTratamientos = new MenuItem("Consultar Tratamiento");

	// =================================== MENU CITAS ==========================================
	Menu mnuCitas = new Menu("Citas");
	MenuItem mniAltaCitas = new MenuItem("Nueva Cita");
	MenuItem mniConsultaCitas = new MenuItem("Consultar Citas");

	public Principal(int tipo) 
	{
		ventanaPrincipal.setSize(450, 400);
		ventanaPrincipal.setLayout(new FlowLayout());

		mniAltaCliente.addActionListener(this);
		mnuCliente.add(mniAltaCliente);
		if (tipo == 0) {
			mniBajaCliente.addActionListener(this);
			mnuCliente.add(mniBajaCliente);
			mniModificacionCliente.addActionListener(this);
			mnuCliente.add(mniModificacionCliente);
			mniConsultaCliente.addActionListener(this);
			mnuCliente.add(mniConsultaCliente);
		}
		menuPrincipal.add(mnuCliente);

		mniAltaTratamiento.addActionListener(this);
		mnuTratamientos.add(mniAltaTratamiento);
		if (tipo == 0) {
			mniBajaTratamiento.addActionListener(this);
			mnuTratamientos.add(mniBajaTratamiento);
			mniModificacionTratamiento.addActionListener(this);
			mnuTratamientos.add(mniModificacionTratamiento);
			mniConsultaTratamientos.addActionListener(this);
			mnuTratamientos.add(mniConsultaTratamientos);
		}
		menuPrincipal.add(mnuTratamientos);

		mniAltaCitas.addActionListener(this);
		mnuCitas.add(mniAltaCitas);
		if (tipo == 0) {
			mniConsultaCitas.addActionListener(this);
			mnuCitas.add(mniConsultaCitas);
		}
		menuPrincipal.add(mnuCitas);

		ventanaPrincipal.setMenuBar(menuPrincipal);
		ventanaPrincipal.add(lblCitasHoy);
		txaCitasHoy.setEditable(false);
		txaCitasHoy.setBackground(Color.WHITE);
		ventanaPrincipal.add(txaCitasHoy);
		btnActualizar.setBackground(Color.WHITE);
		ventanaPrincipal.add(btnActualizar);
		ventanaPrincipal.setBackground(clFondo);
		ventanaPrincipal.addWindowListener(this);
		ventanaPrincipal.setLocationRelativeTo(null);
		ventanaPrincipal.setResizable(false);
		ventanaPrincipal.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mniAltaCliente)) {
			new AltaClientes();
		}else if (e.getSource().equals(mniConsultaCliente)) {
			new ConsultarClientes();
		}else if (e.getSource().equals(mniAltaTratamiento)) {
			new AltaTratamiento();
		}else if (e.getSource().equals(mniConsultaTratamientos)) {
			new ConsultaTratamientos();
		}

	}

	// ========================================== WINDOW LISTENER ===========================================
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
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
