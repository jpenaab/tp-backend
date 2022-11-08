/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TGrupos;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.utils.SecureTokenGenerator;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author jpenaab
 */
public class TPHelperUsuarios implements TPInterfDAOUsuario {

    private TUsuarios authUser = null;

    @Override
    public TUsuarios getUsuarioByID(Integer usuarioId) {

        TUsuarios usuario = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios as usuarios where usuarios.id=" + usuarioId + "");
            usuario = (TUsuarios) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return usuario;
    }

    public TUsuarios getUsuarioByUsername(String username) {

        TUsuarios usuario = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios as usuarios where usuarios.usuario='" + username + "'");
            usuario = (TUsuarios) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return usuario;
    }

    @Override
    public TUsuarios getUsuarioByEmail(String email) {

        TUsuarios usuario = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios as usuarios where usuarios.email='" + email + "' AND enabled = 1");
            usuario = (TUsuarios) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return usuario;
    }

    @Override
    public List<TUsuarios> getAllUsuarios() {

        List<TUsuarios> usuarios = new ArrayList<TUsuarios>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("FROM TUsuarios AS usuarios ORDER BY id ASC");
            usuarios = (List<TUsuarios>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return usuarios;
    }

    @Override
    public Boolean createUsuario(String nombre, String apellidos, String usuario, String contrasenya, String email, Integer grupo) {

        Boolean resultado = false;
        TUsuarios u = new TUsuarios();
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setUsuario(usuario);
        u.setEmail(email);
        u.setId_grupo(grupo);
        
        u.setEnabled(0);
        
        try {
            
            u.setContrasenya(this.createHash(contrasenya));
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            session.save(u);
            transaction.commit();
            resultado = true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return resultado;
    }
    
    @Override
    public Boolean createGoogleUsuario(String nombre, String apellidos, String usuario, String contrasenya, String email, String tk) {

        Boolean resultado = false;
        TUsuarios u = new TUsuarios();
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setUsuario(usuario);
        u.setEmail(email);
        u.setTk(tk);
        u.setTk_timestamp(new Timestamp(System.currentTimeMillis()));
        
        u.setId_grupo(1);
        u.setEnabled(1);
        
        try {
            
            u.setContrasenya(this.createHash(contrasenya));
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            session.save(u);
            transaction.commit();
            resultado = true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return resultado;
    }
    
    @Override
    public Boolean updateGoogleUsuario(TUsuarios usuario, String newTk) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        Boolean resultado = false;
        TUsuarios o = new TUsuarios();

        if (usuario.getId() > 0) {
            o = this.getUsuarioByID(usuario.getId());
        } else if (usuario.getId() == -5) {

            usuario.setId_grupo(o.getId_grupo());
            usuario.setEnabled(o.getEnabled());
            usuario.setTk_timestamp(o.getTk_timestamp());
            usuario.setContrasenya(o.getContrasenya());
            usuario.setTk(newTk);

        }

        if (o == null ) {
            //el usuario no existe
            return false;
            
        } else if (o != null && usuario.getId() != -5)  {

            usuario.setId_grupo(o.getId_grupo());
            usuario.setEnabled(o.getEnabled());
            usuario.setTk_timestamp(o.getTk_timestamp());
            usuario.setContrasenya(o.getContrasenya());
            usuario.setTk(newTk);
        }

        try {

            transaction = session.beginTransaction();
            session.update(usuario);
            transaction.commit();
            resultado = true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return resultado;
    }

    @Override
    public Boolean updateUsuario(TUsuarios usuario) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        Boolean resultado = false;
        TUsuarios o = new TUsuarios();

        if (usuario.getId() > 0) {
            o = this.getUsuarioByID(usuario.getId());
        } else if (usuario.getId() == -5) {

            usuario.setId_grupo(o.getId_grupo());
            usuario.setEnabled(o.getEnabled());
            usuario.setTk_timestamp(o.getTk_timestamp());
            usuario.setContrasenya(o.getContrasenya());

        }

        if (o == null ) {
            //el usuario no existe
            return false;
            
        } else if (o != null && usuario.getId() != -5)  {

            usuario.setId_grupo(o.getId_grupo());
            usuario.setEnabled(o.getEnabled());
            usuario.setTk_timestamp(o.getTk_timestamp());
            usuario.setContrasenya(o.getContrasenya());
        }

        try {

            transaction = session.beginTransaction();
            session.update(usuario);
            transaction.commit();
            resultado = true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return resultado;
    }
    
    /**
     * Actualiza la informacion del usuario desde mis datos
     * El usuario puede actulizar el email el nombre y los apellidos.
     * @param usuario
     * @return 
     */
    public Boolean updateMyOwnUserInfo(TUsuarios usuario) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        Boolean resultado = false;
        TUsuarios o = new TUsuarios();

        if (usuario.getId() > 0) {
            o = this.getUsuarioByID(usuario.getId());
        } else if (usuario.getId() == -5) {

            usuario.setId_grupo(o.getId_grupo());
            usuario.setEnabled(o.getEnabled());
            usuario.setUsuario(o.getUsuario());
            usuario.setTk_timestamp(o.getTk_timestamp());
            usuario.setContrasenya(o.getContrasenya());

        }

        if (o == null ) {
            //el usuario no existe
            return false;
            
        } else if (o != null && usuario.getId() != -5)  {

            usuario.setId_grupo(o.getId_grupo());
            usuario.setEnabled(o.getEnabled());
            usuario.setUsuario(o.getUsuario());
            usuario.setTk_timestamp(o.getTk_timestamp());
            usuario.setContrasenya(o.getContrasenya());
        }

        try {

            transaction = session.beginTransaction();
            session.update(usuario);
            transaction.commit();
            resultado = true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return resultado;
    }

    @Override
    public Boolean deleteUsuario(Integer usuarioId) {
        
        Boolean result = false;
        TUsuarios u = new TUsuarios();
        
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            u = (TUsuarios) session.load(TUsuarios.class, usuarioId);
            session.delete(u);
            transaction.commit();
            result = true;
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }
        
        return result;
    }

    @Override
    public TUsuarios logon(String username, String password) {

        TUsuarios u = new TUsuarios();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios Where usuario='" + username + "' AND enabled=1");
            u = (TUsuarios) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        if (u != null) {

            if (0 == u.getUsuario().toString().trim().compareTo(username.toString().trim())) {

                try {
                    System.out.println(" DEBUG: USUARIO: " + username + " - " + password + " \n" + u);

                    if (0 == u.getContrasenya().toString().trim().compareTo(this.createHash(password.toString()).trim())) {
                        System.out.println(" DEBUG: USUARIO: PASS_OK");
                        this.setSessionToken(u, SecureTokenGenerator.nextToken());

                        authUser = new TUsuarios(u.getId(), u.getNombre(), u.getApellidos(), u.getUsuario(), u.getEmail(), u.getTk());
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        return authUser;
    }

    @Override
    public TUsuarios logonWeb(String username, String password) {

        TUsuarios u = new TUsuarios();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios Where usuario='" + username + "' AND enabled=1");
            u = (TUsuarios) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        if (u != null) {

            if (0 == u.getUsuario().toString().trim().compareTo(username.toString().trim())) {

                try {
                    System.out.println(" DEBUG: USUARIO: " + username + " - " + password + " \n" + u);

                    if (0 == u.getContrasenya().toString().trim().compareTo(this.createHash(password.toString()).trim())) {
                        System.out.println(" DEBUG: USUARIO: PASS_OK");
                        this.setSessionToken(u, SecureTokenGenerator.nextToken());

                        authUser = new TUsuarios(u.getId(), u.getNombre(), u.getApellidos(), u.getUsuario(), u.getId_grupo(), u.getEmail(), u.getTk());
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(TPHelperUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        return authUser;
    }

    private Boolean setSessionToken(TUsuarios u, String token) {

        Boolean flag = false;
        // usuario + token
        u.setTk(u.getUsuario() + "_" + token);
        // set timestamp for this token
        u.setTk_timestamp(new Timestamp(System.currentTimeMillis()));

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            session.update(u);
            transaction.commit();
            flag = true;
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public TUsuarios logonByToken(String tk) {

        TUsuarios u = new TUsuarios();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios Where tk='" + tk + "' AND enabled=1");
            u = (TUsuarios) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        if (u != null) {
            System.out.println(" DEBUG: USUARIO: TOKEN: " + u.toString());
        }

        /*
        
         En lugar de controlar usuario y pass
         Controlar la caducidad del token.
        
         */
        if (u != null) {

            DateTime now = new DateTime();
            DateTime tkTimestamp = new DateTime(u.getTk_timestamp());

            System.out.println("NOW : " + now.toString());
            System.out.println("TIMESTAMP : " + tkTimestamp.toString());

            int days = Days.daysBetween(tkTimestamp, now).getDays();

            System.out.println(" DEBUG: USUARIO: TOKEN EXPIRATION: " + days);

            if (days < 30 && days >= 0) {

                authUser = new TUsuarios(u.getId(), u.getNombre(), u.getApellidos(), u.getUsuario(), u.getEmail(), u.getTk());

            }

        }

        return authUser;
    }
    
    @Override
    public Boolean statusUsuario(Integer usuarioId, Integer State) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        Boolean resultado = false;
        TUsuarios o = new TUsuarios();

        if (usuarioId > 0) {
            o = this.getUsuarioByID(usuarioId);
        } 

        if (o == null ) {
            //el usuario no existe
            return false;
            
        } else if (o != null && usuarioId != -5)  {

            o.setEnabled(State);
        }

        try {

            transaction = session.beginTransaction();
            session.update(o);
            transaction.commit();
            resultado = true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return resultado;
    }

    public static String createHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes("UTF-8"));
        byte byteData[] = digest.digest();
        //convert bytes to hex chars
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    

}
