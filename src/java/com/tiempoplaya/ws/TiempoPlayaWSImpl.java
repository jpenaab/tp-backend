/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.ws;

import com.tiempoplaya.model.TDatos;
import com.tiempoplaya.model.TFotografias;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasDistance;
import com.tiempoplaya.model.TPlayasTopFive;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.persistance.TPHelperDatos;
import com.tiempoplaya.persistance.TPHelperFotografia;
import com.tiempoplaya.persistance.TPHelperPlayas;
import com.tiempoplaya.persistance.TPHelperUsuarios;
import com.tiempoplaya.utils.GoogleSignInUtils;
import com.tiempoplaya.utils.TDatosAverage;
import com.tiempoplaya.utils.TDatosReport;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.jws.WebService;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.postgresql.util.Base64;

/**
 *
 * @author jpenaab
 */
@WebService(endpointInterface = "com.hopto.tiempoplaya.ws.TiempoPlayaWS")
public class TiempoPlayaWSImpl implements TiempoPlayaWS {

    private TUsuarios authUser;

    @Resource
    WebServiceContext wsctx;

    private Boolean authWS(String username, String password) {

        authUser = new TUsuarios();
        authUser = new TPHelperUsuarios().logon(username, password);

        if (authUser.getId() == -1) {
            return false;
        } else {
            System.out.println(" DEBUG: LOGON: " + authUser.getTk());
        }
        return true;
    }

    @Override
    public TPlayas getPlayaByName(String playaName) {

        System.out.println("DEBUG: PLAYA BY NAME RETURNED: " + isTokenFromClientCorrect());

        if (isTokenFromClientCorrect()) {

            TPlayas p = new TPHelperPlayas().getPlayaByName(playaName);
            return p;

        } else {
            return null;
        }
    }

    @Override
    public List<TPlayas> getAllPlayas() {

        if (isTokenFromClientCorrect()) {

            List<TPlayas> p = new TPHelperPlayas().getAllPlayas();

            return p;
        } else {
            return null;
        }
    }

    @Override
    public TUsuarios loginAccount(String usuario, String contrasenya) {

        String tk = getTokenFromHeader();

        if (tk != "") {

            authUser = new TUsuarios();
            authUser = new TPHelperUsuarios().logonByToken(tk);

            return authUser;

        }

        if (authWS(usuario, contrasenya)) {

            System.out.println(usuario + "::" + this.getAuthUser().getTk());
            return this.getAuthUser();

        } else {
            return this.getAuthUser();
        }

    }

    @Override
    public List<TPlayasDistance> getClosePlayas(String coordutmx, String coordutmy, String coordutmz, String utmzone) {
        
        //System.out.println("DEBUG: 5 PLAYAs NEAR: " + isTokenFromClientCorrect());

       if (isTokenFromClientCorrect()) {

            List<TPlayasDistance> p = new TPHelperPlayas().getNearestPlayas(coordutmx, coordutmy, coordutmz, utmzone.charAt(0));
            return p;
            
        } else {

            return null;
        }
    }

    @Override
    public TPlayas getClosestPlaya(String coordutmx, String coordutmy, String coordutmz, String utmzone) {

        System.out.println("DEBUG: PLAYA RETURNED: " + isTokenFromClientCorrect());

        if (isTokenFromClientCorrect()) {

            TPlayas playa = new TPlayas();
            playa = new TPHelperPlayas().getClosestPlaya(coordutmx, coordutmy, coordutmz, utmzone.charAt(0));
            System.out.println(" DEBUG: PLAYA RETURNED: " + playa.toString());

            return playa;
        } else {

            return null;
        }
    }

    private TUsuarios getAuthUser() {
        return authUser;
    }

    private String getTokenFromHeader() {

        MessageContext mctx = wsctx.getMessageContext();

        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);

        System.out.println("DEBUG: TOKEN: " + http_headers.toString());

        List tokenList = (List) http_headers.get("tk");

        String token = "";

        if (tokenList != null) {
            //get username
            token = tokenList.get(0).toString();
            System.out.println("DEBUG: TOKEN: " + token);
        }

        return token;

    }

    private Boolean isTokenFromClientCorrect() {

        Boolean flag = false;
        String tk = getTokenFromHeader();

        if (tk != "") {

            authUser = null;

            authUser = new TPHelperUsuarios().logonByToken(tk);

            if (authUser == null) {

            } else {
                flag = true;
            }

        }

        return flag;
    }

    @Override
    public Boolean addNewData(Integer idU, Integer idP, Integer idF, String utmx, String utmy, String utmz, Integer viento, Integer mar, Integer tiempo, Integer ocupacion, Integer agua, Integer arena, Integer medusas, Integer bandera) {
        //System.out.println("DEBUG: ADD NEW DATA: " + isTokenFromClientCorrect());
        Boolean result = false;

        if (isTokenFromClientCorrect()) {

            result = new TPHelperDatos().addNewData(idU, idP, idF, viento, mar, tiempo, ocupacion, agua, arena, medusas, bandera, utmx, utmy, utmz);

        }

        return result;
    }

    @Override
    public Integer addNewPhoto(Integer idU, Integer idP, String bPhoto) {

        System.out.println("DEBUG: ADD NEW PHOTO: " + isTokenFromClientCorrect());

        Integer result = -1;

        byte[] dataPhoto = Base64.decode(bPhoto);

        if (isTokenFromClientCorrect()) {

            result = new TPHelperFotografia().addNewPhoto(idU, idP, dataPhoto);

        }

        return result;
    }

    @Override
    public TDatosAverage getInfoStatusPlaya(Integer idP) {

        TDatosAverage datos = null;

        if (isTokenFromClientCorrect()) {

            datos = new TPHelperDatos().getPlayaStatusAverage(idP);

            return datos;

        } else {

            return datos;
        }
    }

    @Override
    public List<TPlayasDistance> searchPlayaByWord(String coordutmx, String coordutmy, String coordutmz, String utmzone, String stringToFind) {
        
        if (isTokenFromClientCorrect()) {

            List<TPlayasDistance> p = new TPHelperPlayas().searchPlayas(coordutmx, coordutmy, coordutmz, utmzone.charAt(0), stringToFind);

            return p;
        } else {
            return null;
        }
    }

    @Override
    public Integer addNewBeach(String coordutmx, String coordutmy, String coordutmz, String utmzone, String name, String locality, Integer cp, String pais) {
        
        Integer result = -1;
        
        if (isTokenFromClientCorrect()) {

            result = new TPHelperPlayas().createPlaya(coordutmx, coordutmy, coordutmz, utmzone.charAt(0), name, locality, cp, pais);
            return result;
            
        } else {
            return result;
        }
    }

    @Override
    public List<TPlayasTopFive> getTopFiveBeaches() {
        
        if (isTokenFromClientCorrect()) {

            List<TPlayasTopFive> p = new TPHelperDatos().getTopFivePlayas();

            return p;
        } else {
            return null;
        }
        
    }

    @Override
    public List<TPlayas> getFavouriteBeaches(Integer userId) {
        
        if (isTokenFromClientCorrect()) {

            List<TPlayas> p = new TPHelperDatos().getFavouritesPlayas(userId);

            return p;
        } else {
            return null;
        }
        
    }

    @Override
    public List<String> getPhotoBeach(Integer idP) {
        
        List<String> p = new ArrayList<String>();
        
        if (isTokenFromClientCorrect()) {

            p = new TPHelperFotografia().getFotografiasByPlaya(idP);
            
        } 
        
        return p;
    }

    @Override
    public List<TDatosReport> getInfoDataSended(Integer userId) {
        
        List<TDatosReport> p = new ArrayList<TDatosReport>();
        
        if (true){//isTokenFromClientCorrect()) {

            p = new TPHelperDatos().getInfoSended(userId);
            
        } 
        
        return p;
    }

    @Override
    public TUsuarios googleSignIn(String id_token) {
        
        TUsuarios u, guser = null;
        
        GoogleSignInUtils googleSignInUtils = new GoogleSignInUtils(id_token);
        u = googleSignInUtils.getGoogleUser();
        guser = new TUsuarios(u.getId(), u.getNombre(), u.getApellidos(), u.getUsuario(), u.getEmail(), u.getTk());
        
        //System.out.println("googleSignIn " + googleUser);
        
        return guser;
    }

}
