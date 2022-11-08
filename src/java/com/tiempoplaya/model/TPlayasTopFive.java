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
public class TPlayasTopFive extends TPlayas {
    
    private Integer numberData;
    
    public TPlayasTopFive(TPlayas p, Integer numberData) {
        super(p.getId(), p.getNombre(), p.getCoordUTMx(), p.getCoordUTMy(), p.getCoordUTMz(), p.getUtmzone(), p.getMunicipio(), p.getCp(), p.getPais());
        this.numberData = numberData;
    }
    
    public TPlayasTopFive(String nombre, String coordUTMx, String coordUTMy, String coordUTMz, Character utmZone, String municipio, int cp, String pais) {
        super(coordUTMx, coordUTMy, coordUTMz, utmZone, nombre, municipio, cp, pais);
        this.numberData = -1;
    }

    public Integer getNumberData() {
        return numberData;
    }

    public void setNumberData(Integer numberData) {
        this.numberData = numberData;
    }

    @Override
    public String toString() {
        return super.toString() +"TPlayasTopFive{" + "numberData=" + numberData + '}';
    }
    
}
