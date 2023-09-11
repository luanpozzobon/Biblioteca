package com.luan.getlib.repository;

import com.luan.getlib.models.Book;
import com.luan.getlib.utils.Database;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class BookRepository {
    public static boolean saveBook(Book book){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.persist(book);
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
    
    public static Book findById(int id){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        
        try {
            return session.get(Book.class, id);
        } catch(ObjectNotFoundException e) {
            System.out.println("Não há nenhum livro com este Id: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static List<Book> findByTitle(String title){
        String hql = "FROM Book WHERE title LIKE :p";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        
        query.setParameter("p", "%" + title + "%");
        
        try {
            return query.list();
        } catch(HibernateException e) {
            System.out.println("Ocorreu um erro durante a consulta: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static List<Book> getAllBooks(){
        String hql = "FROM Book ORDER BY title ASC";
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery(hql);
        
        try {
            return query.list();
        } catch (HibernateException e) {
            System.out.println("Ocorreu um erro durante a consulta: " + e);
            
            return null;
        } finally {
            session.close();
        }
    }
    
    public static boolean updateBook(Book book){
        SessionFactory sessionFactory = Database.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            session.update(book);
            transaction.commit();
            
            return true;
        } catch(HibernateException e) {
            System.out.println("Ocorreu um erro ao atualizar o livro no banco de dados: " + e);
            transaction.rollback();
            
            return false;
        } finally {
            session.close();
        }
    }
}
