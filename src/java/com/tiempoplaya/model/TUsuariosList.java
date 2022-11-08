/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author jpenaab
 */

@XmlRootElement (name="usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class TUsuariosList implements java.io.Serializable{
    
    @XmlElement(name = "usuario")
    private ArrayList<TUsuarios> usuarios = null;

    public ArrayList<TUsuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<TUsuarios> usuarios) {
        this.usuarios = usuarios;
    }
 
}
