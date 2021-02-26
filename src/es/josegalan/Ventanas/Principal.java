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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.josegalan.BaseDatos.BaseDatos;

public class Principal implements WindowListener, ActionListener
{

	//===================================== VENTANA PRINCIPAL ==================================
	Frame ventanaPrincipal = new Frame("Peluqueria Forever Young");
	Label lblProximasCitas = new Label("Proximas Citas:");
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

	// =========================================== BASES DE DATOS ==========================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

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
		ventanaPrincipal.add(lblProximasCitas);
		txaCitasHoy.setEditable(false);
		cargarCitas(txaCitasHoy);
		txaCitasHoy.setBackground(Color.WHITE);
		ventanaPrincipal.add(txaCitasHoy);
		btnActualizar.setBackground(Color.WHITE);
		btnActualizar.addActionListener(this);
		ventanaPrincipal.add(btnActualizar);
		ventanaPrincipal.setBackground(clFondo);
		ventanaPrincipal.addWindowListener(this);
		ventanaPrincipal.setLocationRelativeTo(null);
		ventanaPrincipal.setResizable(false);
		ventanaPrincipal.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(mniAltaCliente)) 
		{
			new AltaClientes();
		}
		else if (e.getSource().equals(mniConsultaCliente)) 
		{
			new ConsultarClientes();
		}
		else if (e.getSource().equals(mniAltaTratamiento)) 
		{
			new AltaTratamiento();
		}
		else if (e.getSource().equals(mniConsultaTratamientos)) 
		{
			new ConsultaTratamientos();
		}
		else if (e.getSource().equals(mniAltaCitas)) 
		{
			new AltaCita();
		}else if (e.getSource().equals(mniConsultaCitas)) {
			new ConsultarCitas();
		}
		else if (e.getSource().equals(btnActualizar)) 
		{
			cargarCitas(txaCitasHoy);
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

	// ====================================================== CARGAR CITAS A TEXTAREA ===============================================
	public void cargarCitas(TextArea txaListadoCitas) {
		txaListadoCitas.selectAll();
		txaListadoCitas.setText("");
		bd = new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT citas.fechaCita, citas.horaCita, clientes.nombreCliente, clientes.apellidosCliente FROM citas, clientes "
					+ "WHERE citas.idClienteFK = clientes.idCliente\n"
					+ "AND citas.fechaCita > date('"+obtenerFechaHoy()+"')\n"
					+ "Order by citas.fechaCita, citas.horaCita;";
			rs = statement.executeQuery(sentencia);
			txaListadoCitas.selectAll();
			txaListadoCitas.setText("");
			txaListadoCitas.append("Fecha\tHora\tNombre\tApellidos\n");
			txaListadoCitas.append("=========================================\n");
			while (rs.next()) {
				txaListadoCitas.append(rs.getDate("fechaCita")+"\t"+rs.getTime("horaCita")+"\t"+rs.getString("nombreCliente")+"\t"+rs.getString("apellidosCliente")+"\n");
			}
		} catch (SQLException e) {
			txaListadoCitas.selectAll();
			txaListadoCitas.setText("");
			txaListadoCitas.append("Error al cargar los datos");	
		}finally {
			bd.desconectar(connection);
		}
		
	}
	
	// ============================================ OBTENER FECHA =======================================================
	public String obtenerFechaHoy() {
		Calendar fecha = new GregorianCalendar();
		  
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
  
        return (año + "-" + (mes+1) + "-" + dia);
	}




}
