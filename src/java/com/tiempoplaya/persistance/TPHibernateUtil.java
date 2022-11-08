/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author jpenaab
 */
public class TPHibernateUtil {

    private static SessionFactory sessionFactory = null;

    private static void createSessionFactory() {
        
        try {

            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (HibernateException e) {
            System.out.append("** Exception in SessionFactory **");
            e.printStackTrace();
        }

    }

    public static Session getSession() {

        if (sessionFactory == null) {
            createSessionFactory();
        }

        return sessionFactory.openSession();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }

}
