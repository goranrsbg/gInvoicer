package com.goranrsbg.gi.database.dao;

import com.goranrsbg.gi.database.DBFactory;
import com.goranrsbg.gi.database.entity.Email;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;

/**
 *
 * @author goranrsbg
 */
public class DAOemail {

    private static final DAOemail INSTANCE = new DAOemail();

    private static final SessionFactory SF = DBFactory.getSessionFactory();

    private DAOemail() {
    }

    public static DAOemail get() {
        return INSTANCE;
    }

    public List<Email> readAll() {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Email> query = em.createNamedQuery("Email.all", Email.class);
        final List<Email> items = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return items;
    }

    public void save(Email email) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.persist(email);
        em.getTransaction().commit();
        em.close();
    }

    public Email update(Email email) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        Email merged = em.merge(email);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(Email email) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.remove(email);
        em.getTransaction().commit();
        em.close();
    }
}
