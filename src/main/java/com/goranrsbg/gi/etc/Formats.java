package com.goranrsbg.gi.etc;

import java.time.format.DateTimeFormatter;
import javafx.scene.image.Image;

/**
 * All formats for the app.
 *
 * @author goranrsbg
 */
public class Formats {

    public static final Image THE_LOGO = new Image(PropManager.getProperty(PropManager.LOGO_64));

    /**
     * Format for displaying the running clock.
     */
    public static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("kk:mm:ss");

    /**
     * Format for displaying the current last W/E.
     */
    public static final DateTimeFormatter DAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}
