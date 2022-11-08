/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author u$3R
 */
//grupo al que pertene un usuario por id
//SELECT nombre FROM tiempoplaya.t_grupos WHERE id = (SELECT id_grupo FROM tiempoplaya.t_usuarios WHERE id=6);

@Entity(name="TGrupos")
@Table(name="t_grupos")
public class TGrupos implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    private String nombre;

    public TGrupos() {
    }

    public TGrupos(Integer id) {
        this.id = id;
    }

    public TGrupos(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TGrupos{" + "id=" + id + ", nombre=" + nombre + '}';
    }
        
}
