/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TGrupos;
import com.tiempoplaya.model.TUsuarios;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author jpenaab
 */
public interface TPInterfDAOUsuario {
    
    public TUsuarios logon(String username, String password);
    public TUsuarios logonWeb(String username, String password);
    public TUsuarios logonByToken(String tk);
    
    //CRUD Definitions
    public Boolean createUsuario(String nombre, String apellidos, String usuario, String contrasenya, String email, Integer grupo);
    public Boolean createGoogleUsuario(String nombre, String apellidos, String usuario, String contrasenya, String email, String tk);
    public Boolean updateGoogleUsuario(TUsuarios usuario, String newTk);
    public TUsuarios getUsuarioByID(Integer usuarioId);
    public Boolean updateUsuario(TUsuarios usuario);
    public Boolean deleteUsuario(Integer usuarioId);
    public Boolean statusUsuario(Integer usuarioId, Integer State);
    public List<TUsuarios> getAllUsuarios();
    public TUsuarios getUsuarioByEmail(String email);
    public TUsuarios getUsuarioByUsername(String username);
    
}
