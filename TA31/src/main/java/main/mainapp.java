package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;

public class mainapp {

    public static void main(String[] args) {
        databaseConnection conexion = new databaseConnection();
        Connection connection = conexion.conectar();

        if (connection != null) {
            createDB("mi_basededatos", connection);
            createTable("mi_basededatos", "fabricantes", connection);
            createTable("mi_basededatos", "articulos", connection);
            
            for (int i = 1; i <= 5; i++) {
                insertData(connection, "mi_basededatos", "fabricantes", "Fabricante" + i, 500 + i * 100);
            }
            
            for (int i = 1; i <= 5; i++) {
                insertData(connection, "mi_basededatos", "articulos", "Articulo" + i, 100 + i * 10);
            }
            
            conexion.desconectar();
        }
    }

    public static void createDB(String name, Connection connection) {
        try {
            String query = "CREATE DATABASE IF NOT EXISTS " + name;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Database created successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(mainapp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createTable(String db, String name, Connection connection) {
        try {
            String queryDb = "USE " + db;
            Statement stmtDb = connection.createStatement();
            stmtDb.executeUpdate(queryDb);
            
            String query = "";
            
            if (name.equals("fabricantes")) {
                query = "CREATE TABLE fabricantes "
                        + "(codigo INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(100))";
            } else if (name.equals("articulos")) {
                query = "CREATE TABLE articulos "
                        + "(codigo INT PRIMARY KEY AUTO_INCREMENT, nombre NVARCHAR(100), precio INT, "
                        + "fabricante INT, FOREIGN KEY(fabricante) REFERENCES fabricantes(codigo) "
                        + "ON DELETE CASCADE ON UPDATE CASCADE)";
            }
            
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Tabla " + name + " creada con éxito.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error creando la tabla " + name + ".");
        }
    }
    
    public static void insertData(Connection connection, String db, String table_name, String nombre, int precio) {
        try {
            String queryDb = "USE " + db;
            Statement stmtDb = connection.createStatement();
            stmtDb.executeUpdate(queryDb);
            
            String query = "INSERT INTO " + table_name + " (nombre, precio) "
                    + "VALUES ('" + nombre + "', " + precio + ")";
            
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            
            System.out.println("Datos insertados en la tabla " + table_name + ".");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error insertando datos en la tabla " + table_name + ".");
        }
    }

    
}

