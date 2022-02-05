package com.goranrsbg.gi.database;

import com.goranrsbg.gi.etc.PropManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

/**
 *
 * @author Goran Cvijanovic
 */
public class DBFactory {

    private static final Logger LOGGER = LogManager.getLogger(DBFactory.class);

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Connects to database and builds session factory object.
     *
     * @return True if connection is established.
     */
    public static boolean connect() {
        Configuration configuration = new Configuration().configure();
        configuration.setProperty(Environment.URL, PropManager.getProperty(PropManager.DB_URL));
        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException ex) {
            LOGGER.error("Connection to database failed: " + ex.getMessage());
        }
        return sessionFactory != null;
    }

    /**
     * Closes Session Factory.
     */
    public static void close() {
        try {
            sessionFactory.close();
        } catch (HibernateException ex) {
            LOGGER.error("Database close operation threw error: " + ex.getMessage());
        }
    }

}
