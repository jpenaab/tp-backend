/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.model;

/**
 *
 * @author u$3R
 */
public class TPlayasDistance extends TPlayas{
    
    private double distance = 0;

    public TPlayasDistance(TPlayas p, double distance) {
        super(p.getId(), p.getNombre(), p.getCoordUTMx(), p.getCoordUTMy(), p.getCoordUTMz(), p.getUtmzone(), p.getMunicipio(), p.getCp(), p.getPais());
        this.distance = distance;
    }
    public TPlayasDistance(String nombre, String coordUTMx, String coordUTMy, String coordUTMz, Character utmZone, String municipio, int cp, String pais) {
        super(coordUTMx, coordUTMy, coordUTMz, utmZone, nombre, municipio, cp, pais);
        this.distance = -1;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return super.toString() + " TPlayasDistance{" + "distance=" + distance + '}'; //To change body of generated methods, choose Tools | Templates.
    }
        
}
