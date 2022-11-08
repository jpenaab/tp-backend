/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasDistance;
import com.tiempoplaya.model.TPlayasDistanceComparator;
import com.tiempoplaya.utils.CoordenadasUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import uk.me.jstott.jcoord.UTMRef;

/**
 *
 * @author jpenaab
 */
public class TPHelperPlayas implements TPInterfDAOPlaya {
    
    private final NumberFormat NF = NumberFormat.getNumberInstance(Locale.GERMAN);

    @Override
    public TPlayas getPlayaById(Integer playaId) {

        TPlayas playa = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where playas.id=" + playaId + " and enabled=1");
            playa = (TPlayas) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return playa;
    }
    
    /**
     * retorna la playa por ID 
     * Las habilitadas y las deshabilitadas
     * @param playaId
     * @return 
     */
    private TPlayas getAnyPlayaById(Integer playaId) {

        TPlayas playa = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where playas.id=" + playaId );
            playa = (TPlayas) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return playa;
    }
    
    @Override
    public TPlayas getPlayaByIdNoExceptions(Integer playaId) {

        TPlayas playa = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where playas.id=" + playaId);
            playa = (TPlayas) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return playa;
    }
    
    @Override
    public TPlayas getPlayaByName(String playaName) {

        TPlayas playa = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where playas.nombre='" + playaName + "' and enabled=1");
            playa = (TPlayas) q.uniqueResult();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        } 

        return playa;
    }
    
    @Override
    public List<TPlayas> getAllPlayas() {

        List<TPlayas> playas = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where enabled=1");
            playas = (List<TPlayas>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }
                                        
        return playas;
    }
    
    @Override
    public List<TPlayas> getAllPlayasNoExceptions() {

        List<TPlayas> playas = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("FROM TPlayas AS playas ORDER BY enabled ASC");
            playas = (List<TPlayas>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }
                                        
        return playas;
    }
    
    @Override
    public Integer createPlaya(String coordUTMx, String coordUTMy, String coordUTMz, Character utmZone, String nombre, String municipio, Integer codPostal, String pais) {
        
        Integer resultadoId = -1;
        
        TPlayas p = new TPlayas(coordUTMx, coordUTMy, coordUTMz, utmZone, nombre, municipio, codPostal, pais);
                
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            
            transaction = session.beginTransaction();
            resultadoId = (Integer) session.save(p);
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
                            
        return resultadoId;
    }
    
    @Override
    public TPlayas getClosestPlaya(String coordUTMx, String coordUTMy, String coordUTMz, Character utmzone) {
        
        TPlayas playa = new TPlayas();
        List<TPlayas> playaArray = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where coordutmz='"+ coordUTMz +"' and utmzone='"+utmzone+"' and enabled=1");
            playaArray = (List<TPlayas>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }
        
        Double aux = Double.POSITIVE_INFINITY;
        
        //System.out.println("DEBUG: ANDROID: "+ coordUTMx +" "+ coordUTMy +" "+ coordUTMz);
        
        for (TPlayas p : playaArray){
            
            Double distancia = CoordenadasUtils.getModuloVectorExtremos(p.getDoubleCoordUTMx(), p.getDoubleCoordUTMy(), p.getIntCoordUTMz(), Double.valueOf(coordUTMx), Double.valueOf(coordUTMy), Integer.valueOf(coordUTMz));
            
            //System.out.println(distancia +" "+ p);
            
            //la playa más cerca           
            if (distancia < aux){
                aux = distancia;
                playa = p;
            }
            
        }
        
        return playa;
    }

    @Override
    public List<TPlayasDistance> getNearestPlayas(String coordUTMx, String coordUTMy, String coordUTMz, Character utmzone) {
        
        List<TPlayasDistance> playasDistanceArray = new ArrayList<TPlayasDistance>();
        List<TPlayas> playasArray = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where coordutmz='"+ coordUTMz +"' and utmzone='"+utmzone+"' and enabled=1");
            playasArray = (List<TPlayas>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }
        
        Double aux = Double.POSITIVE_INFINITY;
        
        //System.out.println("DEBUG: NEAR FIVE: "+ coordUTMx +" "+ coordUTMy +" "+ coordUTMz + " " + utmzone);
        
        TPlayasDistance d = null;
        
        for (TPlayas p : playasArray){
            
            Double distancia = CoordenadasUtils.getModuloVectorExtremos(p.getDoubleCoordUTMx(), p.getDoubleCoordUTMy(), p.getIntCoordUTMz(), Double.valueOf(coordUTMx), Double.valueOf(coordUTMy), Integer.valueOf(coordUTMz));
            
            d = new TPlayasDistance(p, distancia);
            playasDistanceArray.add(d);
                        
        }
        
        Comparator<TPlayasDistance> comp = true ? new TPlayasDistanceComparator() : Collections.reverseOrder(new TPlayasDistanceComparator());
        Collections.sort(playasDistanceArray, comp);
        
        if(playasDistanceArray.size()>20){
            return playasDistanceArray.subList(0, 10);
        }else{
            return playasDistanceArray;
        }
    }

    @Override
    public Boolean updatePlaya(TPlayas playa) {   
         
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        Boolean resultado = false;
        TPlayas p = new TPlayas();

        if (playa.getId() > 0) {
            p = this.getAnyPlayaById(playa.getId());
        } 

        if (p == null ) {
            //la playa no existe
            return false;
            
        } else if (p != null)  {

            p.setNombre(playa.getNombre());
            p.setMunicipio(playa.getMunicipio());
            p.setPais(playa.getPais());
            p.setCoordUTMx(playa.getCoordUTMx());
            p.setCoordUTMy(playa.getCoordUTMy());
            p.setCoordUTMz(playa.getCoordUTMz());
            p.setUtmzone(playa.getUtmzone());
            
            System.err.println(p);
            
        }

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

    @Override
    public Boolean deletePlaya(Integer playaId) {
        Boolean result = false;
        TPlayas p = new TPlayas();
        
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            p = (TPlayas) session.load(TPlayas.class, playaId);
            session.delete(p);
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
    public List<TPlayasDistance> searchPlayas(String coordUTMx, String coordUTMy, String coordUTMz, Character utmzone, String searchInput) {
        
        List<TPlayasDistance> playasDistanceArray = new ArrayList<TPlayasDistance>();
        List<TPlayas> playasArray = null;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TPlayas as playas where LOWER(playas.nombre) like '%" + searchInput.toLowerCase() + "%' and enabled=1");
            playasArray = (List<TPlayas>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        } 
        
        Double aux = Double.POSITIVE_INFINITY;
        
        //System.out.println("DEBUG: NEAR FIVE: "+ coordUTMx +" "+ coordUTMy +" "+ coordUTMz + " " + utmzone);
        
        TPlayasDistance d = null;
        
        for (TPlayas p : playasArray){
            
            Double distancia = CoordenadasUtils.getModuloVectorExtremos(p.getDoubleCoordUTMx(), p.getDoubleCoordUTMy(), p.getIntCoordUTMz(), Double.valueOf(coordUTMx), Double.valueOf(coordUTMy), Integer.valueOf(coordUTMz));
            
            d = new TPlayasDistance(p, distancia);
            playasDistanceArray.add(d);
                        
        }
        
        Comparator<TPlayasDistance> comp = true ? new TPlayasDistanceComparator() : Collections.reverseOrder(new TPlayasDistanceComparator());
        Collections.sort(playasDistanceArray, comp);
        
        if(playasDistanceArray.size()>20){
            return playasDistanceArray.subList(0, 10);
        }else{
            return playasDistanceArray;
        }
        
    }

    @Override
    public boolean statusPlayas(Integer idBeach, Integer state) {
        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();
        
        Boolean resultado = false;
        TPlayas p = new TPlayas();
        p = this.getPlayaByIdNoExceptions(idBeach);
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
