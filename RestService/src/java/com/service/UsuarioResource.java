
package com.service;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jrgodoy
 */

@Path("usuario") //path para la clase
public class UsuarioResource {
    
    UsuarioService uservice = new UsuarioService();
    
    @GET
    @Path("/getusers")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Usuario> getUsers() throws ClassNotFoundException, SQLException {
        return uservice.getUsers();
    }
    //==========================================================================
    
    @POST
    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public String addUser(Usuario u) throws SQLException, ClassNotFoundException {
        Usuario usuario = new Usuario();
        usuario.setNombre(u.getNombre());
        usuario.setCorreo(u.getCorreo());
        uservice.addUsuario(usuario);
        String result = "Usuario guardado: " + usuario.getNombre()+", "+usuario.getCorreo();
        return result;
    }
    //==========================================================================
    
    @DELETE
    @Path("/deleteuser")
    @Produces("text/plain")
    public String deleteUser(@QueryParam("id") int id) throws ClassNotFoundException, SQLException {
        uservice.deleteUser(id);
        String result = "Usuario eliminado correctamente!";
        return result;
    }
    //==========================================================================
    
    @PUT
    @Path("/edituser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public String editUser(@QueryParam("id") int id, Usuario user) throws SQLException, ClassNotFoundException {
        uservice.editUser(id, user);
        String result = "Usuario modificado correctamente!";
        return result;
    }
    //==========================================================================
    
    @GET
    @Path("/getuserbyid")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUserById(@QueryParam("id") int id) throws ClassNotFoundException, SQLException {
        Usuario user = new Usuario();
        user = uservice.getUserById(id);
        return user;
    }
    //==========================================================================
    @GET
    @Path("/isuser")
    @Produces("text/plain")
    public String isUser(@QueryParam("correo") String correo) throws ClassNotFoundException, SQLException {
        String result;
        result = uservice.isUser(correo);
        return result;
    }
    //==========================================================================
    
    @GET
    @Path("/gethijos")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Hijo> getHijos(@QueryParam("userId") int userId) throws ClassNotFoundException, SQLException {
        Usuario user = new Usuario();
        ArrayList<Hijo> hijos = new ArrayList();
        hijos = uservice.getHijos(userId);
        return hijos;
    }
     //==========================================================================
    
    @GET
    @Path("/getregistro")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Registro> getRegistro(@QueryParam("userId") int hijoId) throws ClassNotFoundException, SQLException {
        Usuario user = new Usuario();
        ArrayList<Registro> registros = new ArrayList();
        registros = uservice.getRegistros(hijoId);
        return registros;
    }
}
