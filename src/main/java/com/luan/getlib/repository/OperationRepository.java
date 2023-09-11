package com.luan.getlib.repository;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Operation;
import com.luan.getlib.utils.Database;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class OperationRepository {
    public static boolean saveOperation(Operation operation){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.persist(operation);
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
    
    public static Operation findByBookAndCustomer(Book book, Customer customer){
        String hql = "FROM Operation WHERE book = :p1 AND customer = :p2";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        
        query.setParameter("p1", book);
        query.setParameter("p2", customer);
        
        try {
            return (Operation) query.uniqueResult();
        } catch(NoResultException e) {
            System.out.println("Não foi encontrada nenhuma operação do livro especificado para este usuário: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static List<Operation> findRentByCustomer(Customer customer){
        String hql = "FROM Operation WHERE type = 'r' AND customer = :p";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        
        query.setParameter("p", customer);
        
        try{
            return query.list();
        } catch(HibernateException e) {
            System.out.println("Ocorreu um erro durante a consulta: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static Map<String, Operation> findByCustomerId(Customer customer){
        String hql = "SELECT operation, book.title FROM Operation operation LEFT JOIN operation.book book WHERE operation.customer = :p";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query<Object[]> query = session.createQuery(hql);
        
        query.setParameter("p", customer);
        
        try{
            List<Object[]> rows = query.getResultList();

            Map<String, Operation> operations = new TreeMap<>();
            for(Object[] row : rows){
                operations.put((String) row[1], (Operation) row[0]);
            }

            return operations;
        } catch(HibernateException e){
            System.out.println("Ocorreu um erro durante a consulta: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static boolean updateOperation(Operation operation){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.update(operation);
            transaction.commit();
            
            return true;
        } catch (HibernateException e){
            System.out.println("Ocorreu um erro ao atualizar a operação no banco de dados: " + e);
            transaction.rollback();
            
            return false;
        } finally {
            session.close();
        }
    }
    
    public static boolean deleteOperation(int operationId){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try{
            Operation operation = session.get(Operation.class, operationId);
            session.delete(operation);
            transaction.commit();
            
            return true;
        } catch(HibernateException e){
            System.out.println("Ocorreu um erro ao deletar a operação: " + e);
            transaction.rollback();
            
            return false;
        } finally {
            session.close();
        }
    }
}