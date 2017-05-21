/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import java.util.Date;

/**
 *
 * @author jrgodoy
 */
public class Registro {
    int id_vacuna;
    String nombreVacuna;
    int estado; //aplicada o no aplicada
    Date fecha;
    String responsable;
    int hijoId;
    int dosis;
    
    public Registro() {
        id_vacuna = 0;
        nombreVacuna = "";
        estado = 0;
        fecha = null;
        responsable = "";
        hijoId = 0;
        dosis = 0;
    }
    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getHijoId() {
        return hijoId;
    }

    public void setHijoId(int hijoId) {
        this.hijoId = hijoId;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public int getId_vacuna() {
        return id_vacuna;
    }

    public void setId_vacuna(int id_vacuna) {
        this.id_vacuna = id_vacuna;
    }
    
    
    
    
}
