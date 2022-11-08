/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.utils;

/**
 *
 * @author u$3R
 */
public class TDatosAverage {
    
    private int viento;
    private int oleaje;
    private int nubosidad;
    private int ocupacion;
    private int limpiezaAgua;
    private int limpiezaArena;
    private int banderaMar;
    private int medusas;

    public TDatosAverage(){}
    
    public TDatosAverage(int viento, int oleaje, int nubosidad, int ocupacion, int limpiezaAgua, int limpiezaArena, int medusas, int banderaMar) {
        this.viento = viento;
        this.oleaje = oleaje;
        this.nubosidad = nubosidad;
        this.ocupacion = ocupacion;
        this.limpiezaAgua = limpiezaAgua;
        this.limpiezaArena = limpiezaArena;
        this.banderaMar = banderaMar;
        this.medusas = medusas;
    }
    
    public int getViento() {
        return viento;
    }

    public void setViento(int viento) {
        this.viento = viento;
    }

    public int getOleaje() {
        return oleaje;
    }

    public void setOleaje(int oleaje) {
        this.oleaje = oleaje;
    }

    public int getNubosidad() {
        return nubosidad;
    }

    public void setNubosidad(int nubosidad) {
        this.nubosidad = nubosidad;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getLimpiezaAgua() {
        return limpiezaAgua;
    }

    public void setLimpiezaAgua(int limpiezaAgua) {
        this.limpiezaAgua = limpiezaAgua;
    }

    public int getLimpiezaArena() {
        return limpiezaArena;
    }

    public void setLimpiezaArena(int limpiezaArena) {
        this.limpiezaArena = limpiezaArena;
    }

    public int getBanderaMar() {
        return banderaMar;
    }

    public void setBanderaMar(int banderaMar) {
        this.banderaMar = banderaMar;
    }

    public int getMedusas() {
        return medusas;
    }

    public void setMedusas(int medusas) {
        this.medusas = medusas;
    }
    
    public void setEmptyData(){
        this.viento = -1;
        this.oleaje = -1;
        this.nubosidad = -1;
        this.ocupacion = -1;
        this.limpiezaAgua = -1;
        this.limpiezaArena = -1;
        this.banderaMar = -1;
        this.medusas = -1;
    }
    
    @Override
    public String toString() {
        return "TDatosAverage{" + "viento=" + viento + ", oleaje=" + oleaje + ", nubosidad=" + nubosidad + ", ocupacion=" + ocupacion + ", limpiezaAgua=" + limpiezaAgua + ", limpiezaArena=" + limpiezaArena + ", medusas=" + medusas + ", banderaMar=" + banderaMar + '}';
    }
       
}
