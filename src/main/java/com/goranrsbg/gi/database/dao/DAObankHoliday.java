package com.goranrsbg.gi.database.dao;

import com.goranrsbg.gi.database.DBFactory;
import com.goranrsbg.gi.database.entity.BankHoliday;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;

/**
 *
 * @author goranrsbg
 */
public class DAObankHoliday {

    private static final DAObankHoliday INSTANCE = new DAObankHoliday();

    private final SessionFactory SF = DBFactory.getSessionFactory();

    private DAObankHoliday() {
    }

    public static DAObankHoliday get() {
        return INSTANCE;
    }

    public List<BankHoliday> readAll() {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<BankHoliday> query = em.createNamedQuery("BankHoliday.all", BankHoliday.class);
        final List<BankHoliday> items = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return items;
    }

    public void save(BankHoliday bh) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.persist(bh);
        em.getTransaction().commit();
        em.close();
    }

    public BankHoliday update(BankHoliday bh) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        BankHoliday merged = em.merge(bh);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public void delete(BankHoliday bh) {
        final EntityManager em = SF.createEntityManager();
        em.getTransaction().begin();
        em.remove(bh);
        em.getTransaction().commit();
        em.close();
    }

}
