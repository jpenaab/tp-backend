/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TFotografias;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TUsuarios;
import java.util.List;

/**
 *
 * @author jpenaab
 */
public interface TPInterfDAOFotografia {
    
    public Integer addNewPhoto(Integer idP, Integer idU, byte[] bPhoto);
    public TFotografias getFotografiasById(Integer idPhoto);
    public List<String> getFotografiasByPlaya(Integer idPlaya);
    public List<TFotografias> getFotografiasByUser(Integer idUser);
    public List<TFotografias> getAllFotografias();
    public boolean deleteFotografia(Integer idPhoto);
    public boolean statusFotografia(Integer idPhoto, Integer state);
    
    //CRUD Definitions
    public Boolean createPlaya(String nombre, String coordenadas, String municipio, Integer codPostal, String pais);
    public TPlayas getPlayaByID(Integer playaId);
    public TUsuarios getUserByID(Integer usuarioId);
    public Boolean updatePlaya(TUsuarios usuario);
    public Boolean deletePlaya(TUsuarios usuario);
    public List<TPlayas> getAllPlayas();
    public TPlayas getPlayaByName(String name);
    public TPlayas getClosestPlaya(String coordenadas);
    
}
