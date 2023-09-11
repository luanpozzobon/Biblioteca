package com.luan.getlib.utils;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.models.Operation;
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
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Book.class);
            configuration.addAnnotatedClass(Operation.class);
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
