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

public class ConsultarCitas implements WindowListener, ActionListener 
{

	// ==================================== VENTANA PRINCIPAL ===================================
	Frame frmConsulaCitas = new Frame("Consulta Citas");
	Label lblInformacionCitas = new Label("Información Citas:");
	TextArea txaConsultaCitas = new TextArea(15,50);
	Button btnExportarPdfCitas = new Button("Exportar a PDF");
	Button btnCancelarConsultaCitas = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ============================ COMPONENTES BASE DE DATOS =============================================
	BaseDatos bd = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String sentencia = "";


	// ============================== CONSTRUCTOR =====================================================
	public ConsultarCitas() 
	{
		frmConsulaCitas.setLayout(new FlowLayout());
		frmConsulaCitas.setSize(500, 350);
		frmConsulaCitas.setBackground(clFondo);
		frmConsulaCitas.add(lblInformacionCitas);
		txaConsultaCitas.setEditable(false);
		txaConsultaCitas.setBackground(Color.WHITE);
		bd= new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM citas;";
			rs = statement.executeQuery(sentencia);
			txaConsultaCitas.selectAll();
			txaConsultaCitas.setText("");
			txaConsultaCitas.append("IdCita\tFecha\tHora\tidCliente\n");
			txaConsultaCitas.append("====================================================\n");
			while (rs.next()) {
				txaConsultaCitas.append(rs.getInt("idCita")+"\t"+rs.getDate("fechaCita")+"\t"+rs.getTime("horaCita")+"\t"+
						rs.getInt("idClienteFK")+"\n");
			}
		} catch (SQLException e) {
			txaConsultaCitas.selectAll();
			txaConsultaCitas.setText("");
			txaConsultaCitas.append("Error al cargar los datos");	
		}finally {
			bd.desconectar(connection);
		}
		frmConsulaCitas.add(txaConsultaCitas);
		btnExportarPdfCitas.addActionListener(this);
		btnExportarPdfCitas.setBackground(Color.WHITE);
		frmConsulaCitas.add(btnExportarPdfCitas);
		btnCancelarConsultaCitas.addActionListener(this);
		btnCancelarConsultaCitas.setBackground(Color.WHITE);
		frmConsulaCitas.add(btnCancelarConsultaCitas);
		frmConsulaCitas.addWindowListener(this);
		frmConsulaCitas.setResizable(false);
		frmConsulaCitas.setLocationRelativeTo(null);
		frmConsulaCitas.setVisible(true);
	}

	// =================================================== ACTION LISTENER =======================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarConsultaCitas)) {
			frmConsulaCitas.setVisible(false);
			frmConsulaCitas.setVisible(false);
			frmConsulaCitas.removeWindowListener(this);
			btnCancelarConsultaCitas.removeActionListener(this);
		}
	}

	// ================================================= WINDOW LISTENER ========================================================
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		frmConsulaCitas.setVisible(false);
		frmConsulaCitas.removeWindowListener(this);
		btnCancelarConsultaCitas.removeActionListener(this);
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
