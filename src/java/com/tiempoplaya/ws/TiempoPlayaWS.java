/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.ws;

import com.tiempoplaya.model.TDatos;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasDistance;
import com.tiempoplaya.model.TPlayasTopFive;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.utils.TDatosAverage;
import com.tiempoplaya.utils.TDatosReport;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author jpenaab
 */

/*
Interface que define los métodos publicados del webservice
*/

@WebService
//SEI (Service Endpoint Interface)

@SOAPBinding(style=SOAPBinding.Style.DOCUMENT)
//Definir como crear el contrato WSDL
//RPC Simplica el despliegue del servicio por lo que es mas facil
//DOCUMENT importante cuando se trabaja con listas y arrays
public interface TiempoPlayaWS {
      

    /**
     * Tiempo y Playa Login
     * @param usuario
     * @param contrasenya
     * @return 
     */
    @WebMethod(operationName = "loginAccount")
    TUsuarios loginAccount(@WebParam(name = "usuario") String usuario, @WebParam(name = "contrasenya") String contrasenya);
    
    /**
     * Google Account Sign In
     * @param id_token
     * @return 
     */
    @WebMethod(operationName = "googleSignIn")
    TUsuarios googleSignIn(@WebParam(name = "id_token") String id_token);
    
    /**
     * Return playa by Name
     * @param playaName
     * @return 
     */
    @WebMethod(operationName = "getPlayaByName")
    TPlayas getPlayaByName(@WebParam(name = "playaName") String playaName);
    
    /**
     * Return Playas List
     * @param stringToFind
     * @return 
     */
    @WebMethod(operationName = "searchPlayaByWord")
    List<TPlayasDistance> searchPlayaByWord(@WebParam(name = "coordutmx") String coordutmx, @WebParam(name = "coordutmy") String coordutmy, @WebParam(name = "coordutmz") String coordutmz, @WebParam(name = "utmzone") String utmzone, @WebParam(name = "stringToFind") String stringToFind);
    
    /**
     * Todas las Playas
     * @return 
     */
    @WebMethod(operationName = "getAllPlayas")
    List<TPlayas> getAllPlayas();
    
    /**
     * Top 5 de playas cercanas
     * @param coordutmx
     * @param coordutmy
     * @param coordutmz
     * @return 
     */
    @WebMethod(operationName = "getClosePlayas")
    List<TPlayasDistance> getClosePlayas(@WebParam(name = "coordutmx") String coordutmx, @WebParam(name = "coordutmy") String coordutmy, @WebParam(name = "coordutmz") String coordutmz, @WebParam(name = "utmzone") String utmzone);
    
    /**
     * Playa mas cercana
     * @param coordutmx
     * @param coordutmy
     * @param coordutmz
     * @return 
     */
    @WebMethod(operationName = "getClosestPlaya")
    TPlayas getClosestPlaya(@WebParam(name = "coordutmx") String coordutmx, @WebParam(name = "coordutmy") String coordutmy, @WebParam(name = "coordutmz") String coordutmz, @WebParam(name = "utmzone") String utmzone);

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getInfoStatusPlaya")
    public TDatosAverage getInfoStatusPlaya(@WebParam(name = "idP") Integer idP);
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "addNewData")
    public Boolean addNewData(@WebParam(name = "idU") Integer idU, @WebParam(name = "idP") Integer idP, @WebParam(name = "idF") Integer idF, @WebParam(name = "utmx") String utmx, @WebParam(name = "utmy") String utmy, @WebParam(name = "utmz") String utmz, @WebParam(name = "viento") Integer viento, @WebParam(name = "mar") Integer mar, @WebParam(name = "tiempo") Integer tiempo, @WebParam(name = "ocupacion") Integer ocupacion, @WebParam(name = "agua") Integer agua, @WebParam(name = "arena") Integer arena, @WebParam(name = "medusas") Integer medusas, @WebParam(name = "bandera") Integer bandera);

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addNewPhoto")
    public Integer addNewPhoto(@WebParam(name = "idU") Integer idU, @WebParam(name = "idP") Integer idP, @WebParam(name = "bPhoto") String bPhoto);
    
    /**
     * 
     * @param idP
     * @return 
     */
    @WebMethod(operationName = "getPhotoBeach")
    public List<String> getPhotoBeach( @WebParam(name = "idP") Integer idP);
    
     /**
      * 
      * @param coordutmx
      * @param coordutmy
      * @param coordutmz
      * @param utmzone
      * @param name
      * @param locality
      * @param cp
      * @param pais
      * @return 
      */
    @WebMethod(operationName = "addNewBeach")
    public Integer addNewBeach(@WebParam(name = "coordutmx") String coordutmx, @WebParam(name = "coordutmy") String coordutmy, @WebParam(name = "coordutmz") String coordutmz, @WebParam(name = "utmzone") String utmzone, @WebParam(name = "name") String name, @WebParam(name = "locality") String locality, @WebParam(name = "cp") Integer cp, @WebParam(name = "pais") String pais);
    
    
    /**
     * @param none
     * @return 
     */
    @WebMethod(operationName = "getTopFiveBeaches")
    public List<TPlayasTopFive> getTopFiveBeaches();
    
    /**
     * @param userId
     * @return 
     */
    @WebMethod(operationName = "getFavouriteBeaches")
    public List<TPlayas> getFavouriteBeaches(@WebParam(name = "userId") Integer userId);
    
    /**
     * 
     * @param userId
     * @return 
     */
    @WebMethod(operationName = "getInfoDataSended")
    public List<TDatosReport> getInfoDataSended(@WebParam(name = "userId") Integer userId);
}
