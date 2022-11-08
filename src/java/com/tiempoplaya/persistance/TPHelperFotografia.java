/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TFotografias;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.utils.ReadProperties;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author jpenaab
 */
public class TPHelperFotografia implements TPInterfDAOFotografia {

    @Override
    public Boolean createPlaya(String nombre, String coordenadas, String municipio, Integer codPostal, String pais) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TPlayas getPlayaByID(Integer playaId) {
        TPlayas p = new TPlayas();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where playas.id='" + playaId + "'");
            p = (TPlayas) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return p;
    }

    @Override
    public Boolean updatePlaya(TUsuarios usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean deletePlaya(TUsuarios usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TPlayas> getAllPlayas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TPlayas getPlayaByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TPlayas getClosestPlaya(String coordenadas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer addNewPhoto(Integer idU, Integer idP, byte[] bPhoto) {
        Integer id = -1;

        TPlayas playas = new TPlayas();
        playas = this.getPlayaByID(idP);

        TUsuarios usuarios = new TUsuarios();
        usuarios = this.getUserByID(idU);

        //file
        BufferedImage image = null;

        byte[] imageByte = bPhoto;

        try {
            
            //BASE64Decoder decoder = new BASE64Decoder();
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = "_ph_" + playas.getNombre().trim().replace(" ", "_").concat("_").concat(String.valueOf(new Date().getTime())).concat(".jpg");
        String rootF = ReadProperties.getPathImagenes();
        File folder = new File(rootF + File.separator+String.format("%04d", playas.getId())+File.separator);
        
        if (!folder.exists());
            folder.mkdirs();
        
        File outputfile = new File(folder,fileName);
        
        System.err.println("DEBUG: DATA: " + this.getClass() + " " + outputfile.getAbsolutePath());
        
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(TPHelperFotografia.class.getName()).log(Level.SEVERE, null, ex);
        }

        TFotografias fotografias = new TFotografias(playas, usuarios, null, new Timestamp(System.currentTimeMillis()), fileName, 0);

        System.out.println("DEBUG: DATA: " + this.getClass() + " " + fotografias);

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            id = (Integer) session.save(fotografias);
            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return id;
    }

    @Override
    public TUsuarios getUserByID(Integer usuarioId) {

        TUsuarios u = new TUsuarios();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("from TUsuarios as users where users.id='" + usuarioId + "'");
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

        return u;
    }

    @Override
    public TFotografias getFotografiasById(Integer idPhoto) {
        TFotografias photo = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("from TFotografias as photo where photo.id=" + idPhoto + "");
            photo = (TFotografias) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return photo;
    }

    @Override
    public List<String> getFotografiasByPlaya(Integer idPlaya) {

        List<String> photosList = new ArrayList<String>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            String sqlQuery = "SELECT filename FROM tiempoplaya.t_fotografias WHERE id_playa= "+idPlaya+" AND enabled = 1 ORDER BY id DESC LIMIT 6";
            Query q = session.createSQLQuery(sqlQuery);
            photosList = (List<String>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return photosList;

    }
    
    @Override
    public List<TFotografias> getAllFotografias() {

        List<TFotografias> photosList = new ArrayList<TFotografias>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("FROM TFotografias AS p ORDER BY p.timestamp DESC");
            photosList = (List<TFotografias>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return photosList;

    }
    
    @Override
    public List<TFotografias> getFotografiasByUser(Integer userid) {

        List<TFotografias> photosList = new ArrayList<TFotografias>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {

            transaction = session.beginTransaction();
            Query q = session.createQuery("FROM TFotografias AS p WHERE id_usuario = "+ userid +" ORDER BY p.timestamp DESC");
            photosList = (List<TFotografias>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return photosList;

    }

    @Override
    public boolean deleteFotografia(Integer idPhoto) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        Boolean resultado = false;
        TFotografias p = new TFotografias();
        p.setId(idPhoto);

        try {

            transaction = session.beginTransaction();
            session.delete(p);
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
    public boolean statusFotografia(Integer idPhoto, Integer state) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        Boolean resultado = false;
        TFotografias p = new TFotografias();
        p = this.getFotografiasById(idPhoto);
        p.setEnabled(state);

        try {

            transaction = session.beginTransaction();
            session.update(p);
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
    
}
