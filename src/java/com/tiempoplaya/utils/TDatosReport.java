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
public class TDatosReport {
    
    private int id;
    private String nombre;
    private String coordUTMx;
    private String coordUTMy;
    private String coordUTMz;
    private int viento;
    private int oleaje;
    private int nubosidad;
    private int ocupacion;
    private int limpiezaAgua;
    private int limpiezaArena;
    private int medusas;
    private int banderaMar;
    private String timestamp;

    public TDatosReport() {
    }
    
    public TDatosReport(int id, String nombre, String coordUTMx, String coordUTMy, String coordUTMz, int viento, int oleaje, int nubosidad, int ocupacion, int limpiezaAgua, int limpiezaArena, int medusas, int banderaMar, String timestamp) {
        this.id = id;
        this.nombre = nombre;
        this.coordUTMx = coordUTMx;
        this.coordUTMy = coordUTMy;
        this.coordUTMz = coordUTMz;
        this.viento = viento;
        this.oleaje = oleaje;
        this.nubosidad = nubosidad;
        this.ocupacion = ocupacion;
        this.limpiezaAgua = limpiezaAgua;
        this.limpiezaArena = limpiezaArena;
        this.medusas = medusas;
        this.banderaMar = banderaMar;
        this.timestamp = timestamp;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCoordUTMx() {
        return coordUTMx;
    }

    public void setCoordUTMx(String coordUTMx) {
        this.coordUTMx = coordUTMx;
    }

    public String getCoordUTMy() {
        return coordUTMy;
    }

    public void setCoordUTMy(String coordUTMy) {
        this.coordUTMy = coordUTMy;
    }

    public String getCoordUTMz() {
        return coordUTMz;
    }

    public void setCoordUTMz(String coordUTMz) {
        this.coordUTMz = coordUTMz;
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

    public int getMedusas() {
        return medusas;
    }

    public void setMedusas(int medusas) {
        this.medusas = medusas;
    }

    public int getBanderaMar() {
        return banderaMar;
    }

    public void setBanderaMar(int banderaMar) {
        this.banderaMar = banderaMar;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TDatosReport{" + "id=" + id + ", nombre=" + nombre + ", coordUTMx=" + coordUTMx + ", coordUTMy=" + coordUTMy + ", coordUTMz=" + coordUTMz + ", viento=" + viento + ", oleaje=" + oleaje + ", nubosidad=" + nubosidad + ", ocupacion=" + ocupacion + ", limpiezaAgua=" + limpiezaAgua + ", limpiezaArena=" + limpiezaArena + ", medusas=" + medusas + ", banderaMar=" + banderaMar + ", timestamp=" + timestamp + '}';
    }
            
}
