package es.josegalan.Ventanas;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.josegalan.BaseDatos.BaseDatos;

public class ConsultarClientes implements WindowListener, ActionListener
{

	// ==================================== VENTANA PRINCIPAL ===================================
	Frame frmConsulaClientes = new Frame("Consulta Clientes");
	Label lblInformacionClientes = new Label("Información Clientes:");
	TextArea txaConsultaClientes = new TextArea(15,50);
	Button btnExportarPdfClientes = new Button("Exportar a PDF");
	Button btnCancelarConsultaClientes = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ============================ COMPONENTES BASE DE DATOS =============================================
	BaseDatos bd = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String sentencia = "";

	// ============================== CONSTRUCTOR ==========================================
	public ConsultarClientes() 
	{
		frmConsulaClientes.setLayout(new FlowLayout());
		frmConsulaClientes.setSize(500, 350);
		frmConsulaClientes.setBackground(clFondo);
		frmConsulaClientes.add(lblInformacionClientes);
		txaConsultaClientes.setEditable(false);
		txaConsultaClientes.setBackground(Color.WHITE);
		bd= new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM clientes;";
			rs = statement.executeQuery(sentencia);
			txaConsultaClientes.selectAll();
			txaConsultaClientes.setText("");
			txaConsultaClientes.append("IdCliente\tNombre\tApellidos\tDNI\tDireccion\tSexo\n");
			txaConsultaClientes.append("====================================================\n");
			while (rs.next()) {
				txaConsultaClientes.append(rs.getInt("idCliente")+"\t"+rs.getString("nombreCliente")+"\t"+rs.getString("apellidosCliente")+"\t"+
						rs.getString("dniCliente")+"\t"+rs.getString("direccionCliente")+"\t"+rs.getString("sexoCliente")+"\n");
			}
		} catch (SQLException e) {
			txaConsultaClientes.selectAll();
			txaConsultaClientes.setText("");
			txaConsultaClientes.append("Error al cargar los datos");
			bd.desconectar(connection);
		}
		frmConsulaClientes.add(txaConsultaClientes);
		btnExportarPdfClientes.addActionListener(this);
		btnExportarPdfClientes.setBackground(Color.WHITE);
		frmConsulaClientes.add(btnExportarPdfClientes);
		btnCancelarConsultaClientes.addActionListener(this);
		btnCancelarConsultaClientes.setBackground(Color.WHITE);
		frmConsulaClientes.add(btnCancelarConsultaClientes);
		frmConsulaClientes.addWindowListener(this);
		frmConsulaClientes.setResizable(false);
		frmConsulaClientes.setLocationRelativeTo(null);
		frmConsulaClientes.setVisible(true);
	}

	// ===================================== ACTION LISTENER ==========================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarConsultaClientes)) {
			frmConsulaClientes.setVisible(false);
			frmConsulaClientes.setVisible(false);
			frmConsulaClientes.removeWindowListener(this);
			btnCancelarConsultaClientes.removeActionListener(this);
		}
	}

	// ======================================= WINDOW LISTENER ==========================================
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		frmConsulaClientes.setVisible(false);
		frmConsulaClientes.removeWindowListener(this);
		btnCancelarConsultaClientes.removeActionListener(this);
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
