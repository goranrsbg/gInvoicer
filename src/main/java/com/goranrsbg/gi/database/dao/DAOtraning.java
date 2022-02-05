package com.goranrsbg.gi.database.dao;

import com.goranrsbg.gi.database.DBFactory;
import com.goranrsbg.gi.database.entity.Training;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;

/**
 *
 * @author goranrsbg
 */
public class DAOtraning {

    private static DAOtraning INSTANCE;

    private final SessionFactory SF = DBFactory.getSessionFactory();

    private DAOtraning() {
    }

    public static DAOtraning get() {
        if (INSTANCE == null) {
            INSTANCE = new DAOtraning();
        }
        return INSTANCE;
    }

    public List<Training> readAll() {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Training> query = em.createNamedQuery("Training.all", Training.class);
        final List<Training> trainings = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return trainings;
    }

    public void save(Training training) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.persist(training);
        em.getTransaction().commit();
        em.close();
    }

    public Training update(Training training) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        Training saved = em.merge(training);
        em.getTransaction().commit();
        em.close();
        return saved;
    }

    public void delete(Training training) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.remove(training);
        em.getTransaction().commit();
        em.close();
    }

}
