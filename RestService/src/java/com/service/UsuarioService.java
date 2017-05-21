
package com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author jrgodoy
 */
public class UsuarioService {
    
    Conexion con;
    Connection conex;
    public UsuarioService() {
        con = new Conexion();
        conex = null;
    }

    void addUsuario(Usuario u) throws SQLException, ClassNotFoundException {
        String sql="insert into \"Usuarios\" (nombre,correo) values(?,?)";
        conex = con.conectarBD();
            
        PreparedStatement pst=conex.prepareStatement(sql);
        pst.setString(1,u.getNombre());
        pst.setString(2,u.getCorreo());   
        pst.execute();
        pst.close();
        conex.close();
        con.cerrarBD();    
    }
    //==========================================================================
    
    public void deleteUser(int id) throws ClassNotFoundException, SQLException {
        String sql="delete from \"Usuarios\" where id = ?";
        conex = con.conectarBD();
        
        PreparedStatement pst = conex.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        pst.close();
        conex.close();
        con.cerrarBD();
    }
    //==========================================================================
    
    public void editUser(int id, Usuario user) throws SQLException, ClassNotFoundException {
        String sql = "update \"Usuarios\" set nombre = ?, correo = ? where id = ?";
        conex = con.conectarBD();
        
        PreparedStatement pst = conex.prepareStatement(sql);
        pst.setString(1, user.getNombre());
        pst.setString(2, user.getCorreo());
        pst.setInt(3, id);
        pst.executeUpdate();
        pst.close();
        conex.close();
        con.cerrarBD();
    }
    //==========================================================================
    
    public ArrayList<Usuario> getUsers() throws SQLException, ClassNotFoundException {
        ArrayList<Usuario> lista = new ArrayList();
        conex = con.conectarBD();
        Statement st = conex.createStatement();
        ResultSet rs = st.executeQuery("select nombre,correo from \"Usuarios\"");
        while (rs.next()) {
            Usuario tm = new Usuario ();
            tm.setNombre(rs.getString("nombre"));
            tm.setCorreo(rs.getString("correo"));
            lista.add(tm);
        }
        conex.close();
        con.cerrarBD();
        return lista;
    }
    //==========================================================================
    
    public ArrayList<Hijo> getHijos(int userId) throws SQLException, ClassNotFoundException {
        ArrayList<Hijo> lista = new ArrayList();
        conex = con.conectarBD();
        Statement st = conex.createStatement();
        ResultSet rs = st.executeQuery("select id_hijo, nombre,apellido,sexo,fecha_nacimiento,"
                + "lugar_nacimiento,nacionalidad,direccion,telefono_contacto,"
                + "alergia from \"Hijos\" where id_usuario = "+userId+"");
        while (rs.next()) {
            Hijo tm = new Hijo();
            tm.setId_hijo(rs.getInt("id_hijo"));
            tm.setNombre(rs.getString("nombre"));
            tm.setApellido(rs.getString("apellido"));
            tm.setSexo(rs.getString("sexo"));
            tm.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
            tm.setLugar_nacimiento(rs.getString("lugar_nacimiento"));
            tm.setNacionalidad(rs.getString("nacionalidad"));
            tm.setDireccion(rs.getString("direccion"));
            tm.setTelefono_contacto(rs.getString("telefono_contacto"));
            tm.setAlergia(rs.getString("alergia"));
            
            
            lista.add(tm);
        }
        conex.close();
        con.cerrarBD();
        return lista;
    }
     //==========================================================================
    
     public ArrayList<Registro> getRegistros(int usuarioId) throws SQLException, ClassNotFoundException {
        ArrayList<Registro> lista = new ArrayList();
        conex = con.conectarBD();
        Statement st = conex.createStatement();
        String sql = "select rv.id_vacuna,rv.id_hijo, rv.fecha, v.nombre, rv.id_registro, "
                   + "rv.estado, rv.responsable, rv.dosis " 
                   + "from \"Hijos\" h "
                   + "join \"RegistroVacuna\" rv on rv.id_hijo = h.id_hijo " 
                   + "join \"Vacunas\" v on v.id_vacuna = rv.id_vacuna " 
                   + "where h.id_usuario = "+usuarioId;
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Registro tm = new Registro();
            tm.setId_vacuna(rs.getInt("id_vacuna"));
            tm.setNombreVacuna(rs.getString("nombre"));
            tm.setEstado(rs.getInt("estado"));
            tm.setFecha(rs.getDate("fecha"));
            tm.setDosis(rs.getInt("dosis"));
            tm.setHijoId(rs.getInt("id_hijo"));
            tm.setResponsable(rs.getString("responsable"));
            lista.add(tm);
        }
        conex.close();
        con.cerrarBD();
        return lista;
    }
    //==========================================================================
    
    public Usuario getUserById(int id) throws ClassNotFoundException, SQLException {
        conex = con.conectarBD();
        Statement st = conex.createStatement();
        ResultSet rs = st.executeQuery("select nombre,correo from \"Usuarios\" where id_usuario = "+id);
        Usuario user = new Usuario();
        while (rs.next()) {
            user.setNombre(rs.getString("nombre"));
            user.setCorreo(rs.getString("correo"));
        }
        conex.close();
        con.cerrarBD();
        return user;
    }
    //==========================================================================
    
    public String isUser(String correo) throws ClassNotFoundException, SQLException {
        conex = con.conectarBD();
        Statement st = conex.createStatement();
        ResultSet rs = st.executeQuery("select id_usuario from \"Usuarios\" where correo = '"+correo+"'");
        if (rs.next()) {
            return rs.getString(1);
        }
        else {
            return "0";
        }      
    }
}
