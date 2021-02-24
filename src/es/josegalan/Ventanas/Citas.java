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
import java.sql.Statement;

import es.josegalan.BaseDatos.BaseDatos;

public class Citas implements WindowListener, ActionListener
{

	// ========================================= VENTANA PRINCIPAL =======================================================
	Frame frmAltaCitas = new Frame("Alta Citas");
	Label lblFechaCitas = new Label("Fecha:");
	Label lblHoraCitas = new Label("Hora:");
	Label lblBuscarNombre = new Label("Buscar Nombre:");
	Label lblSeleccionaCliente = new Label("Selecciona un Cliente:           ");
	Choice cholistaClientes = new Choice();
	Choice cholistaHoras = new Choice();
	TextField txtFecha = new TextField("2020/10/21", 20);
	TextField txtBuscarNombre = new TextField(20);
	Button btnBuscar = new Button("Buscar");
	Button btnAltaCitas = new Button("Alta Cita");
	Button btnAtrasCitas = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);
	String[] horas = {"9:00","9:30","10:00", "10:30","11:00","11:30","12:00","12:30","13:00","13:30","17:00","17:30","18:00",
			"18:30","19:00","19:30","20:00"};

	// ============================================ VENTANA CONFIRMACION ================================================
	Frame frmConfirmacionCitas = new Frame("Confirmación");
	Label lblConfirmacionCitas = new Label("¿Estas seguro de añadir esta Cita?");
	Button btnSiConfirmacionCitas = new Button("Si");
	Button btnNoConfirmacionCitas = new Button("No");

	// ============================================ CREACIO DIALOGO DE CONFIRMACION =====================================
	Dialog dlgNotificacionAlta = new Dialog(frmConfirmacionCitas, "Notificacion", true);
	Label lblNotificacionAlta = new Label("Cita Añadida Correctamente");

	// ========================================= CREACION DIALOGO DE ERROR ===============================================
	Dialog dlgErrorAltaCita = new Dialog(frmConfirmacionCitas,"Error",true);
	Label lblErrorAltaCita = new Label("Error en el Alta");

	// ====================================== BASE DATOS ==================================================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	// ====================================== CONSTRUTOR ==================================================================
	public Citas() {}

	// ============================================= MOSTRAR ALTA CITAS ===================================================
	public void mostrarAltaCitas() {
		frmAltaCitas.setBackground(clFondo);
		frmAltaCitas.setSize(370, 150);
		frmAltaCitas.setLayout(new FlowLayout());
		frmAltaCitas.add(lblFechaCitas);
		frmAltaCitas.add(txtFecha);
		frmAltaCitas.add(lblHoraCitas);
		for (String s : horas) {
			cholistaHoras.add(s);
		}
		cholistaHoras.setBackground(Color.WHITE);
		frmAltaCitas.add(cholistaHoras);
		frmAltaCitas.add(lblSeleccionaCliente);
		cholistaClientes.setBackground(Color.WHITE);
		frmAltaCitas.add(cholistaClientes);
		btnAltaCitas.setBackground(Color.WHITE);
		btnAltaCitas.addActionListener(this);
		frmAltaCitas.add(btnAltaCitas);
		btnAtrasCitas.addActionListener(this);
		btnAtrasCitas.setBackground(Color.WHITE);
		frmAltaCitas.add(btnAtrasCitas);
		frmAltaCitas.addWindowListener(this);
		frmAltaCitas.setLocationRelativeTo(null);
		frmAltaCitas.setResizable(false);
		frmAltaCitas.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {


	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

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

	public void insertarCita() {

	}

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

	public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) {
		dialogo.setSize(250, 100);
		dialogo.setLayout(new FlowLayout());
		dialogo.setBackground(clFondo);
		dialogo.add(lbl);
		dialogo.setLocationRelativeTo(null);
		dialogo.addWindowListener(this);
		dialogo.setVisible(true);
	}

}