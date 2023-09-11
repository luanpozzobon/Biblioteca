package com.luan.getlib.repository;

import com.luan.getlib.models.Customer;
import com.luan.getlib.utils.Database;
import jakarta.persistence.NoResultException;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class CustomerRepository {
    public static boolean saveCustomer(Customer customer){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {            
            session.persist(customer);
            transaction.commit();
            
            return true;
        } catch(HibernateException e) {
            transaction.rollback();
            System.out.println("Operação cancelada: " + e);
            
            return false;
        } finally {
            session.close();
        }
    }
    
    public static Customer findByUsername(String username){
        String hql = "FROM Customer WHERE username = :p";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        
        query.setParameter("p", username);
        
        try{
            return (Customer) query.uniqueResult();
        } catch(NoResultException e) {
            System.out.println("Não há nenhum cliente com este username: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static boolean usernameExists(String username){
        return findByUsername(username) != null;
    }
    
    public static boolean updateCustomer(Customer customer){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try{
            session.update(customer);
            transaction.commit();
            
            return true;
        } catch(HibernateException e) {
            System.out.println("Ocorreu um erro ao atualizar o cadastro no banco de dados: " + e);
            transaction.rollback();
            
            return false;
        } finally {
            session.close();
        }
    }
    
    public static boolean deleteCustomer(Customer customer){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.delete(customer);
            transaction.commit();
            
            return true;
        } catch(HibernateException e) {
            System.out.println("Ocorreu um erro ao deletar o cadastro: " + e);
            transaction.rollback();
            
            return false;
        } finally {
            session.close();
        }
        
    }
}