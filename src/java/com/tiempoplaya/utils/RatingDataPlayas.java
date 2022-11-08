/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.utils;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author u$3R
 */

public class RatingDataPlayas implements Serializable {
    
    private Integer numberDataRating;
    private Integer playaIdentifier;

    public RatingDataPlayas() {
    }

    public RatingDataPlayas(Integer numberDataRating, Integer playaIdentifier) {
        this.numberDataRating = numberDataRating;
        this.playaIdentifier = playaIdentifier;
    }

    public Integer getNumberdatarating() {
        return numberDataRating;
    }

    public void setNumberdatarating(Integer numberDataRating) {
        this.numberDataRating = numberDataRating;
    }

    public Integer getPlayaidentifier() {
        return playaIdentifier;
    }

    public void setPlayaidentifier(Integer playaIdentifier) {
        this.playaIdentifier = playaIdentifier;
    }

    @Override
    public String toString() {
        return super.toString() + " RatingDataPlayas{" + "numberDataRating=" + numberDataRating + ", playaIdentifier=" + playaIdentifier + '}';
    }
    
}
