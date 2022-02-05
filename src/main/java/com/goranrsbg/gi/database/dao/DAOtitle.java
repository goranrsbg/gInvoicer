package com.goranrsbg.gi.database.dao;

import com.goranrsbg.gi.database.DBFactory;
import com.goranrsbg.gi.database.entity.Title;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;

/**
 *
 * @author goranrsbg
 */
public class DAOtitle {

    private static final DAOtitle INSTANCE = new DAOtitle();

    private static final SessionFactory SF = DBFactory.getSessionFactory();

    private DAOtitle() {
    }

    public static DAOtitle get() {
        return INSTANCE;
    }

    public List<Title> readAll() {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Title> query = em.createNamedQuery("Title.all", Title.class);
        final List<Title> items = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return items;
    }

    public void save(Title title) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.persist(title);
        em.getTransaction().commit();
        em.close();
    }

    public Title update(Title title) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        Title merged = em.merge(title);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(Title title) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.remove(title);
        em.getTransaction().commit();
        em.close();
    }
}
