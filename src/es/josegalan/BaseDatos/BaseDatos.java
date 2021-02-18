package es.josegalan.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/peluqueria?serverTimezone=UTC";
	String login = "root";
	String password = "Studium2020;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public BaseDatos() {

	}

	public Connection conectar()
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-" + cnfe.getMessage());
		} catch (SQLException sqle)
		{
			System.out.println("Error 2-" + sqle.getMessage());
		}
		return connection;
	}

	public void desconectar(Connection con) 
	{
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Error 3-"+e.getMessage());
		}
	}
}

