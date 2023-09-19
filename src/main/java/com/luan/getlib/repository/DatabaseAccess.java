package com.luan.getlib.repository;

import com.luan.getlib.utils.Database;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
class DatabaseAccess<T> {
    private final SessionFactory sessionFactory = Database.getSessionFactory();
    private final String CANCELED = "Operação cancelada: ";

    protected boolean save(T entity) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);
            transaction.commit();
            return true;
        } catch(HibernateException e) {
            if(transaction != null) transaction.rollback();
            System.out.println(CANCELED + e);
            return false;
        }
    }

    protected List<T> find(Class<T> entityType, Map<String, Serializable> parameters) {
        List<T> entities = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(entityType);
            Root<T> root = criteria.from(entityType);
            for(String key : parameters.keySet()) {
                if(parameters.get(key) instanceof Integer)
                    criteria.where(builder.equal(root.get(key), parameters.get(key)));
                else
                    criteria.where(builder.like(root.get(key), "%" + parameters.get(key) + "%"));
            }
            entities = session.createQuery(criteria).getResultList();
        } catch(HibernateException e) {
            System.out.println(CANCELED + e);
        }

        return entities;
    }

    protected boolean update(T entity) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
             transaction = session.beginTransaction();

             session.merge(entity);
             transaction.commit();
             return true;
        } catch(HibernateException e) {
            if(transaction != null) transaction.rollback();
            System.out.println(CANCELED + e);
            return false;
        }
    }

    protected boolean delete(T entity) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.remove(entity);
            transaction.commit();
            return true;
        } catch(HibernateException e) {
            if(transaction != null) transaction.rollback();
            System.out.println(CANCELED + e);
            return false;
        }
    }

}
