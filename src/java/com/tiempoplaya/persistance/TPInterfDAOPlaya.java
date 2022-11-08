/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasDistance;
import java.util.List;

/**
 *
 * @author jpenaab
 */
public interface TPInterfDAOPlaya {
    
    //CRUD Definitions
    public Integer createPlaya(String coordUTMx, String coordUTMy, String coordUTMz, Character utmZone, String nombre, String municipio, Integer codPostal, String pais);
    public TPlayas getPlayaById(Integer playaId);
    public TPlayas getPlayaByIdNoExceptions(Integer playaId);
    public Boolean updatePlaya(TPlayas playa);
    public Boolean deletePlaya(Integer playaId);
    public List<TPlayas> getAllPlayas();
    public List<TPlayas> getAllPlayasNoExceptions();
    public TPlayas getPlayaByName(String name);
    public List<TPlayasDistance> searchPlayas(String coordUTMx, String coordUTMy, String coordUTMz, Character utmzone, String searchInput);
    public TPlayas getClosestPlaya(String coordUTMx, String coordUTMy, String coordUTMz, Character utmzone);
    public List<TPlayasDistance> getNearestPlayas(String coordUTMx, String coordUTMy, String coordUTMz, Character utmzone);
    public boolean statusPlayas(Integer idBeach, Integer state);
    
}
