/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.utils;

import com.tiempoplaya.model.TPlayas;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

/**
 *
 * @author jpenaab
 */
public class CoordenadasUtils {
    
    private static final NumberFormat DF = NumberFormat.getInstance(new Locale("de", "DE"));
    
    
    //distancia euclidea
    
    //convertir UTM a latitud, longitud
    
    //calcula el módulo de dos vectores
    /**
     * La distancia entre dos puntos es igual al módulo del vector que tiene de extremos dichos puntos.
     * @param x Coordenada X o Este
     * @param y Coordenada Y o Norte
     * @param h Coordenada h
     * @return resultado en metros.
     */
    public static double getModuloVectorExtremos(double v1x, double v1y, int v1h, double v2x, double v2y, int v2h){
        
        double result = 0;
 
        result = Math.sqrt((Math.pow((v2x - v1x), 2) + Math.pow((v2y - v1y),2) + Math.pow((v2h - v1h),2)));
          
        return (result/1000);
        
    }
    
    public static LatLng getLatLngFromUTM(String utmx, String utmy, String utmz, char zone){
                
        Double easting = 0.0, northing = 0.0;
        Integer lngZone = 0;
        Character latZone;
        
        easting = getDoubleCoordUTMx(utmx);
        northing = getDoubleCoordUTMy(utmy);
        lngZone = getIntCoordUTMz(utmz);
        latZone = zone;
        
        UTMRef utm = new UTMRef(easting, northing, latZone, lngZone);
              
        return utm.toLatLng();
    }
    
    private static Double getDoubleCoordUTMy(String coordUTMy) {
        Double d = 0.0;

        try {
            d = Double.valueOf(DF.parse(coordUTMy).doubleValue());
        } catch (ParseException ex) {
            Logger.getLogger(TPlayas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return d;
    }
    
    private static Double getDoubleCoordUTMx(String coordUTMx) {

        Double d = 0.0;

        try {
            d = Double.valueOf(DF.parse(coordUTMx).doubleValue());
        } catch (ParseException ex) {
            Logger.getLogger(TPlayas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return d;
    }
    
    private static Integer getIntCoordUTMz(String coordUTMz) {
        return Integer.parseInt(coordUTMz);
    } 
    
}
