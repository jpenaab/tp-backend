/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.tiempoplaya.model.TPlayas;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jpenaab
 */
public class TiempoPlayaHelper {

    private static Session session;

    public TiempoPlayaHelper() {

        this.session = TPHibernateUtil.getSessionFactory().getCurrentSession();

    }

    public Session getSession() {
        return session;
    }
    
    public void closeSession(){
        getSession().close();
    }
    
    
    
}
