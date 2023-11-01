package com.luan.getlib.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @since v0.1.1
 * @author luanpozzobon
 */
public class Database {
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure("persistence.xml").buildSessionFactory();
        }
        
        return sessionFactory;
    }
    
    public static void closeSectionFactory(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }
}
