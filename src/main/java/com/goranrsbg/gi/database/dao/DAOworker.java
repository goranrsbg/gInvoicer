package com.goranrsbg.gi.database.dao;

import com.goranrsbg.gi.database.DBFactory;
import com.goranrsbg.gi.database.entity.Worker;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;

/**
 *
 * @author goranrsbg
 */
public class DAOworker {

    private static final DAOworker INSTANCE = new DAOworker();

    private static final SessionFactory SF = DBFactory.getSessionFactory();

    private DAOworker() {
    }

    public static DAOworker get() {
        return INSTANCE;
    }

    public List<Worker> readAll() {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Worker> query = em.createNamedQuery("Worker.all", Worker.class);
        final List<Worker> items = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return items;
    }

    public void save(Worker worker) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.persist(worker);
        em.getTransaction().commit();
        em.close();
    }

    public Worker update(Worker worker) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        Worker merged = em.merge(worker);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(Worker worker) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.remove(worker);
        em.getTransaction().commit();
        em.close();
    }
}
