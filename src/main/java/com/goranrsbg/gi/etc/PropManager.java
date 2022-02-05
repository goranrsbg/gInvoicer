package com.goranrsbg.gi.etc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages application properties from external properties file.
 *
 * @author Goran Cvijanovic
 */
public class PropManager {

    private static final Logger LOGGER = LogManager.getLogger(PropManager.class);

    public static final String APP_NAME = "app.name";
    public static final String DB_URL = "db.url";
    public static final String LOGO_64 = "logo.64";
    public static final String LOGO = "logo";

    private static PropManager instance;

    private final Properties properties;

    private PropManager() {
        properties = new Properties();
        openFile();
    }

    public Properties get() {
        return properties;
    }

    private void openFile() {
        try (InputStream input = new FileInputStream("cfg/ginvoicer.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Finds value for given key.
     *
     * @param prop Property key for property file.
     *
     * @return Value for given key.
     */
    public static String getProperty(String prop) {
        if (instance == null) {
            instance = new PropManager();
        }
        return instance.get().getProperty(prop);
    }
}
