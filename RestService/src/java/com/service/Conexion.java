
package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jrgodoy
 */
public class Conexion {
    Connection con;
    String user;
    String pass;
    String servidor;
    String database;
    public Conexion () {
        con = null;
        user = "postgres";
        pass = "WALTONDESARROLLO";
        servidor = "localhost:5433";
        database = "RestService";
    }
    
    public Connection conectarBD() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url="jdbc:postgresql://"+servidor+"/"+database;
        con = DriverManager.getConnection(url, user, pass);
        return con;      
    }
    
    public void cerrarBD() throws SQLException {
        con.close();
    }
}
