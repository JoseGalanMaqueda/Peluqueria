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

public class ConsultaAsignacionesTratamientos implements ActionListener, WindowListener
{

	// ==================================== VENTANA PRINCIPAL ===================================
	Frame frmConsultaAsignaciones = new Frame("Consulta Asignaciones Tratamientos a Citas");
	Label lblInformacionAsignaciones = new Label("Información Asignaciones Tratamientos a Citas:");
	TextArea txaConsultaAsignaciones = new TextArea(15,50);
	Button btnExportarPdfAsignaciones = new Button("Exportar a PDF");
	Button btnCancelarConsultaAsignaciones = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ============================ COMPONENTES BASE DE DATOS =============================================
	BaseDatos bd = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String sentencia = "";

	public ConsultaAsignacionesTratamientos() 
	{
		frmConsultaAsignaciones.setLayout(new FlowLayout());
		frmConsultaAsignaciones.setSize(500, 350);
		frmConsultaAsignaciones.setBackground(clFondo);
		frmConsultaAsignaciones.add(lblInformacionAsignaciones);
		txaConsultaAsignaciones.setEditable(false);
		txaConsultaAsignaciones.setBackground(Color.WHITE);
		bd= new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM tratamiento_citas;";
			rs = statement.executeQuery(sentencia);
			txaConsultaAsignaciones.selectAll();
			txaConsultaAsignaciones.setText("");
			txaConsultaAsignaciones.append("idTratamiento_Cita\tidCita\tidTratamiento\n");
			txaConsultaAsignaciones.append("======================================\n");
			while (rs.next()) {
				txaConsultaAsignaciones.append(rs.getInt("idTratamiento_Cita")+"\t\t"+rs.getInt("idCitaFK")+"\t\t"+rs.getInt("idTratamientoFK")+"\n");
			}
		} catch (SQLException e) {
			txaConsultaAsignaciones.selectAll();
			txaConsultaAsignaciones.setText("");
			txaConsultaAsignaciones.append("Error al cargar los datos");	
		}finally {
			bd.desconectar(connection);
		}
		frmConsultaAsignaciones.add(txaConsultaAsignaciones);
		btnExportarPdfAsignaciones.addActionListener(this);
		btnExportarPdfAsignaciones.setBackground(Color.WHITE);
		frmConsultaAsignaciones.add(btnExportarPdfAsignaciones);
		btnCancelarConsultaAsignaciones.addActionListener(this);
		btnCancelarConsultaAsignaciones.setBackground(Color.WHITE);
		frmConsultaAsignaciones.add(btnCancelarConsultaAsignaciones);
		frmConsultaAsignaciones.addWindowListener(this);
		frmConsultaAsignaciones.setResizable(false);
		frmConsultaAsignaciones.setLocationRelativeTo(null);
		frmConsultaAsignaciones.setVisible(true);
	}

	public static void main(String[] args) {
		new ConsultaAsignacionesTratamientos();
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		frmConsultaAsignaciones.setVisible(false);
		frmConsultaAsignaciones.removeWindowListener(this);
		btnCancelarConsultaAsignaciones.removeActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarConsultaAsignaciones)) {
			frmConsultaAsignaciones.setVisible(false);
			frmConsultaAsignaciones.removeWindowListener(this);
			btnCancelarConsultaAsignaciones.removeActionListener(this);
		}
	}

}
