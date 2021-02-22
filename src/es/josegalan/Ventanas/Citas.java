package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
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

public class Citas implements WindowListener, ActionListener{

	// ========================================= VENTANA ALTA CITAS ===================================================
	Frame frmAltaCitas = new Frame("Alta Citas");
	Label lblFechaCitas = new Label("Fecha:");
	Label lblHoraCitas = new Label("Hora:");
	Label lblBuscarNombre = new Label("Buscar Nombre:");
	Label lblSeleccionaCliente = new Label("Selecciona un Cliente:           ");
	Choice listaClientes = new Choice();
	Choice listaHoras = new Choice();
	TextField txtFecha = new TextField("2020/10/21", 20);
	TextField txtBuscarNombre = new TextField(20);
	Button btnBuscar = new Button("Buscar");
	Button btnAltaCitas = new Button("Alta Cita");
	Button btnAtrasCitas = new Button("Cancelar");
	Clientes cliente = null;
	Color clFondo = new Color(204,229,255);
	String[] horas = {"9:00","9:30","10:00", "10:30","11:00","11:30","12:00","12:30","13:00","13:30","17:00","17:30","18:00",
			"18:30","19:00","19:30","20:00"};

	// ========================================= BASE DE DATOS ==============================================
	BaseDatos bd = null;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;


	public Citas() {}

	public void mostrarAltaCitas() {
		frmAltaCitas.setBackground(clFondo);
		frmAltaCitas.setSize(370, 200);
		frmAltaCitas.setLayout(new FlowLayout());
		frmAltaCitas.add(lblFechaCitas);
		frmAltaCitas.add(txtFecha);
		frmAltaCitas.add(lblHoraCitas);
		for (String s : horas) {
			listaHoras.add(s);
		}
		listaHoras.setBackground(Color.WHITE);
		frmAltaCitas.add(listaHoras);
		frmAltaCitas.add(lblBuscarNombre);
		frmAltaCitas.add(txtBuscarNombre);
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.addActionListener(this);
		frmAltaCitas.addWindowListener(this);
		frmAltaCitas.add(btnBuscar);
		frmAltaCitas.add(lblSeleccionaCliente);
		cliente = new Clientes();
		rs = cliente.consultaClientes();
		try {
			while (rs.next()) {
				listaClientes.add(rs.getString("nombreCliente")+" "+rs.getString("apellidosCliente")+" ");
			}
		} catch (SQLException e) {
			System.out.println("Error 6-" + e.getMessage());
		}
		listaClientes.setBackground(Color.WHITE);
		frmAltaCitas.add(listaClientes);
		btnAltaCitas.setBackground(Color.WHITE);
		frmAltaCitas.add(btnAltaCitas);
		btnAtrasCitas.setBackground(Color.WHITE);
		frmAltaCitas.add(btnAtrasCitas);
		frmAltaCitas.setLocationRelativeTo(null);
		frmAltaCitas.setResizable(false);
		frmAltaCitas.setVisible(true);
	}

	public static void main(String[] args) {
		Citas c =new Citas();
		c.mostrarAltaCitas();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnBuscar)) {
			// ==================== BUSCAR CLIENTE CONCRETO =================================
			if (txtBuscarNombre.getText().length()!=0) {
				bd = new BaseDatos();
				connection = bd.conectar();
				try {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					sentencia = "SELECT * FROM clientes WHERE nombreCliente LIKE '"+txtBuscarNombre.getText()+"%';";
					rs = statement.executeQuery(sentencia);
					listaClientes.removeAll();
					while (rs.next()) {
						listaClientes.add(rs.getString("nombreCliente")+" "+rs.getString("apellidosCliente"));
					}
				} catch (SQLException e1) {
					System.out.println("Error 7-"+e1.getMessage());
				}


			}else { // SI NO MUESTRA TODOS
				listaClientes.removeAll();
				rs = cliente.consultaClientes();
				try {
					while (rs.next()) {
						listaClientes.add(rs.getString("nombreCliente")+" "+rs.getString("apellidosCliente")+" ");
					}
				} catch (SQLException e2) {
					System.out.println("Error 6-" + e2.getMessage());
				}
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}


	@Override
	public void windowClosing(WindowEvent e) {
		if (frmAltaCitas.isActive()) {
			frmAltaCitas.setVisible(false);
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
