package com.luan.getlib.repository;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Operation;
import com.luan.getlib.utils.Database;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class OperationRepository {

    private static final DatabaseAccess<Operation> operationDatabase = new DatabaseAccess<>();
    public static boolean saveOperation(Operation operation){
        return operationDatabase.save(operation);
    }
    
    public static Operation findByBookAndCustomer(Book book, Customer customer){
        List<Operation> operations = operationDatabase.find(Operation.class, new HashMap<>(){{
            put("book", book);
            put("customer", customer);
        }});
        if(operations.isEmpty()) return null;

        return operations.get(0);
    }
    
    public static List<Operation> findRentByCustomer(Customer customer){
        return operationDatabase.find(Operation.class, new HashMap<>(){{
            put("customer", customer);
        }});
    }
    
    public static Map<String, Operation> findByCustomerId(Customer customer){
        String hql = "SELECT operation, book.title FROM Operation operation LEFT JOIN operation.book book WHERE operation.customer = :p";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        TypedQuery<Object[]> query = session.createQuery(hql, Object[].class);
        
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
        return operationDatabase.update(operation);
    }
    
    public static boolean deleteOperation(Operation operation){
        return operationDatabase.delete(operation);
    }
}