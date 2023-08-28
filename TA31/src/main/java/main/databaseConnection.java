package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class databaseConnection {

	 String database = "";
	    String url = "jdbc:mysql://localhost:33061";
	    String user = "root";
	    String password = "root";
	    String driver = "com.mysql.cj.jdbc.Driver";
	    Connection conexion;

	    public databaseConnection() {

	    }

	    public Connection conectar() {

	        try {
	            Class.forName(driver);
	            conexion = DriverManager.getConnection(url+database, user, password);
	            System.out.println("Se conectó a la base de datos "+database);
	        } catch (ClassNotFoundException | SQLException ex) { 
	            System.out.println("No se conextó a la base de datos "+database);
	            Logger.getLogger(databaseConnection.class.getName()).log(Level.SEVERE, null, ex);
	        }

	        return conexion;
	    }

	    public void desconectar(){

	        try {

	            conexion.close();
	        } catch (SQLException ex) {

	            Logger.getLogger(databaseConnection.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	}
	



