/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.persistance.TPHelperDatos;
import com.tiempoplaya.persistance.TPHelperUsuarios;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author u$3R
 */
public class GoogleSignInUtils {

    private String idTokenString;
    private final String CLIENT_ID = "57276246856-202ndccm52f8nd1mu7onapi2p2rur3e9.apps.googleusercontent.com";
    private TUsuarios googleUser;
    
    private Boolean verifiedEmail = false;
    private String email;
    private String apellidos; 
    private String nombre;
    private String username;
    private String userId;

    public GoogleSignInUtils(String id_token) {

        boolean flag = false;
        this.idTokenString = id_token;

        HttpTransport transport = new NetHttpTransport();
        JacksonFactory jacksonFactory = new JacksonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = null;
        try {

            idToken = verifier.verify(this.idTokenString);

        } catch (GeneralSecurityException ex) {
            Logger.getLogger(GoogleSignInUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleSignInUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            userId = payload.getSubject();
            //System.out.println("User ID: " + userId);

            // Get profile information from payload
            email = payload.getEmail();
            apellidos = (String) payload.get("family_name");
            nombre = (String) payload.get("given_name");   
            verifiedEmail = (Boolean) payload.getEmailVerified();

            flag = true;
        } else {
            System.out.println("Invalid ID token.");
        }
        
        if (flag){
            
            //System.out.println(nombre +" "+ apellidos + " "+ email);
           googleUser = findUserInDBbyEmail(email);
           
           //System.out.println("findUserInDBbyEmail " + googleUser);
            
           if (googleUser == null && verifiedEmail){
               googleUser = createGoogleUserInDB(email, nombre, apellidos);
               //System.out.println("createGoogleUserInDB " + googleUser);
           }
           
           if (googleUser != null && verifiedEmail){
               updateGoogleUserInDB(googleUser, idTokenString);
           }
        }
    }

    private TUsuarios findUserInDBbyEmail(String email) {
        
        TUsuarios u = new TPHelperUsuarios().getUsuarioByEmail(email);
        
        return u;
    }
    
    private TUsuarios createGoogleUserInDB(String email, String nombre, String apellidos) {
        
        TPHelperUsuarios tPHelperUsuarios = new TPHelperUsuarios();
        tPHelperUsuarios.createGoogleUsuario(nombre, apellidos, email,idTokenString, email, idTokenString);
        
        return new TPHelperUsuarios().getUsuarioByEmail(email);
        
    }
    
    private TUsuarios updateGoogleUserInDB(TUsuarios user, String newTk) {
        
        TPHelperUsuarios tPHelperUsuarios = new TPHelperUsuarios();
        tPHelperUsuarios.updateGoogleUsuario(user, newTk);
        
        return new TPHelperUsuarios().getUsuarioByEmail(email);
    }

    public TUsuarios getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(TUsuarios googleUser) {
        this.googleUser = googleUser;
    }
    
}
