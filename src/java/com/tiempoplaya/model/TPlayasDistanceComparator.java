/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.model;

import java.util.Comparator;

/**
 *
 * @author u$3R
 */
public class TPlayasDistanceComparator implements Comparator<TPlayasDistance> {

    @Override
    public int compare(TPlayasDistance o1, TPlayasDistance o2) {
        
        if (o1.getDistance() < o2.getDistance()){
            
            return -1;
            
        }else if (o1.getDistance() > o2.getDistance()){
            
            return 1;
            
        }else {
           // si es igual 
            return 0;
            
        }
      
    }
    
}
