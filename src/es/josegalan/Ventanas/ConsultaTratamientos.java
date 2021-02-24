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

public class ConsultaTratamientos implements WindowListener, ActionListener
{

	// ==================================== VENTANA PRINCIPAL ===================================
	Frame frmConsulaTratamientos = new Frame("Consulta Tratamientos");
	Label lblInformacionTratamientos = new Label("Información Tratamientos:");
	TextArea txaConsultaTratamientos = new TextArea(15,50);
	Button btnExportarPdfTratamientos = new Button("Exportar a PDF");
	Button btnCancelarConsultaTratamientos = new Button("Cancelar");
	Color clFondo = new Color(204,229,255);

	// ============================ COMPONENTES BASE DE DATOS =============================================
	BaseDatos bd = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String sentencia = "";
	
	public ConsultaTratamientos() 
	{
		frmConsulaTratamientos.setLayout(new FlowLayout());
		frmConsulaTratamientos.setSize(500, 350);
		frmConsulaTratamientos.setBackground(clFondo);
		frmConsulaTratamientos.add(lblInformacionTratamientos);
		txaConsultaTratamientos.setEditable(false);
		txaConsultaTratamientos.setBackground(Color.WHITE);
		bd= new BaseDatos();
		connection = bd.conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM tratamientos;";
			rs = statement.executeQuery(sentencia);
			txaConsultaTratamientos.selectAll();
			txaConsultaTratamientos.setText("");
			txaConsultaTratamientos.append("IdTratamiento\tNombre\tDescripcion\tPrecio\n");
			while (rs.next()) {
				txaConsultaTratamientos.append(rs.getInt("idTratamiento")+"\t"+rs.getString("nombreTratamiento")+"\t"+rs.getString("descripcionTratamiento")+"\t"+
						rs.getInt("precioTratamiento")+"\n");
			}
		} catch (SQLException e) {
			txaConsultaTratamientos.selectAll();
			txaConsultaTratamientos.setText("");
			txaConsultaTratamientos.append("Error al cargar los datos");
			bd.desconectar(connection);
		}
		frmConsulaTratamientos.add(txaConsultaTratamientos);
		btnExportarPdfTratamientos.addActionListener(this);
		btnExportarPdfTratamientos.setBackground(Color.WHITE);
		frmConsulaTratamientos.add(btnExportarPdfTratamientos);
		btnCancelarConsultaTratamientos.addActionListener(this);
		btnCancelarConsultaTratamientos.setBackground(Color.WHITE);
		frmConsulaTratamientos.add(btnCancelarConsultaTratamientos);
		frmConsulaTratamientos.addWindowListener(this);
		frmConsulaTratamientos.setResizable(false);
		frmConsulaTratamientos.setLocationRelativeTo(null);
		frmConsulaTratamientos.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancelarConsultaTratamientos)) {
			frmConsulaTratamientos.setVisible(false);
			frmConsulaTratamientos.removeWindowListener(this);
			btnCancelarConsultaTratamientos.removeActionListener(this);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		frmConsulaTratamientos.setVisible(false);
		frmConsulaTratamientos.removeWindowListener(this);
		btnCancelarConsultaTratamientos.removeActionListener(this);
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
	
	public static void main(String[] args) {
		new ConsultaTratamientos();
	}

}
