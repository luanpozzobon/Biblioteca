package com.luan.getlib.repository;

import com.luan.getlib.models.Employee;
import com.luan.getlib.utils.Database;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class EmployeeRepository {    
    public static boolean saveEmployee(Employee employee){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.persist(employee);
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
    
    public static Employee findByAccessCode(String accessCode){
        String hql = "FROM employees WHERE accessCode = :p";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        
        query.setParameter("p", accessCode);
        
        try {
            return (Employee) query.uniqueResult();
        } catch(NoResultException e) {
            System.out.println("Não há nenhum funcionário com este código de acesso: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static boolean accessCodeExists(String accessCode){
        return findByAccessCode(accessCode) != null;
    }
    
    public static boolean updateEmployee(Employee employee){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.update(employee);
            transaction.commit();
            
            return true;
        } catch (HibernateException e) {
            System.out.println("Ocorreu um erro ao atualizar o cadastro no banco de dados: " + e);
            transaction.rollback();
            
            return false;
        } finally {
            session.close();
        }
    }
    
    public static boolean deleteEmployee(Employee employee){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.delete(employee);
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
