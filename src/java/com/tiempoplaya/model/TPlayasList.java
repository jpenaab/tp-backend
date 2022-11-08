/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.model;

//https://stackoverflow.com/questions/6609770/returning-an-arraylist-from-a-webservice-in-java
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author jpenaab
 */

@XmlRootElement (name="playas")
@XmlAccessorType(XmlAccessType.FIELD)
public class TPlayasList implements java.io.Serializable {
    
    @XmlElement (name ="playa")
    private List<TPlayas> playas = null;

    public List<TPlayas> getPlayas() {
        return playas;
    }

    public void setPlayas(List<TPlayas> playas) {
        this.playas = playas;
    }
    
    
    
}
