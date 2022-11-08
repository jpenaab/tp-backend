/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TDatos;
import com.tiempoplaya.model.TFotografias;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasTopFive;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.utils.TDatosAverage;
import com.tiempoplaya.utils.TDatosReport;
import java.util.List;

/**
 *
 * @author jpenaab
 */
public interface TPInterfDAODato {
    
    public Boolean addNewData(int idUser, int idPlaya, int idFoto, int viento, int mar, int tiempo, int ocupacion, int agua, int arena, int medusas, int bandera, String utmx, String utmy, String utmz); 
    public TDatosAverage getPlayaStatusAverage(Integer idP);
    public List<TPlayasTopFive> getTopFivePlayas();
    public List<TPlayas> getFavouritesPlayas(Integer userId);
    public List<TDatosReport> getInfoSended(Integer userId);
    
}
