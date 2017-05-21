
package com.service;

import java.util.Date;

/**
 *
 * @author Walton
 */
public class Hijo {
    int id_hijo;
    String nombre;
    String apellido;
    String sexo;
    String fecha_nacimiento;
    String lugar_nacimiento;
    String nacionalidad;
    String direccion;
    String telefono_contacto;
    String alergia;
    

    public Hijo(int id_hijo,String nombre, String apellido, String sexo, String fecha_nacimiento, 
            String lugar_nacimiento,String nacionalidad,String direccion,String telefono_contacto,String alergia) {
        this.id_hijo = id_hijo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lugar_nacimiento = lugar_nacimiento;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.telefono_contacto = telefono_contacto;
        this.alergia = alergia;
    }
    
    public Hijo() {
        this.id_hijo = 0;
        this.nombre = "";
        this.apellido = "";
        this.sexo = "";
        this.fecha_nacimiento = null;
        this.lugar_nacimiento = "";
        this.nacionalidad = "";
        this.direccion = "";
        this.telefono_contacto = "";
        this.alergia = "";
    }

    public int getId_hijo() {
        return id_hijo;
    }

    public void setId_hijo(int id_hijo) {
        this.id_hijo = id_hijo;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

   
    
    

}
