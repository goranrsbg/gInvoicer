package com.goranrsbg.gi.etc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author goranrsbg
 */
public class Functions {

    private static final Logger LOGGER = LogManager.getLogger(Functions.class);

    private static final Functions INSTANCE = new Functions();

    private final long HOUR = 3600000L;
    private final long MINUT = 60000L;
    private final long SECOND = 1000L;

    private Functions() {
    }

    public static Functions get() {
        return INSTANCE;
    }

    /**
     * Validates text against null and passed minimum length value.True if text
     * is valid.<p>
     * null -> false
     * text.length >= length -> true
     *
     * @param text   text to be validateTextLength
     * @param name   Name of the given string
     * @param length min length for given string
     *
     * @return true text is valid
     *         false text is not valid
     *
     * @throws java.lang.Exception
     */
    public String validateTextLength(String text, String name, int length) throws Exception {
        if (text == null) {
            throw new Exception(name + " can not be empty.");
        }
        String theText = text.trim();
        if (theText.length() < length) {
            throw new Exception(String.format("%s must have minimum length of %d", name, length));
        }
        return theText;
    }

    /**
     * Finds first Sunday starting from today.
     *
     * @return First found date as Sunday.
     */
    public LocalDate lastWeekEnd() {
        return lastWeekEnd(LocalDate.now());
    }

    /**
     * Finds first Sunday starting from given date and going backward.
     *
     * @param date The date from which Sunday is seeking.
     *
     * @return First found date as Sunday.
     */
    public LocalDate lastWeekEnd(LocalDate date) {
        final long A_DAY = 1L;
        while (!date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            date = date.minusDays(A_DAY);
        }
        return date;
    }

    /**
     * Transforms the name into the name first letter plus dot.
     * <code>Goran -> G.</code>
     *
     * @param name First or Last name to be shortened.
     *
     * @return Shortened name.
     */
    public String fistLetterName(String name) {
        return name.charAt(0) + ".";
    }

    /**
     * Creates alert with given title and text and
     * wait for user response.
     *
     * @param title of alert
     * @param text  of alert
     *
     * @return true if user response was button type yes
     */
    public boolean isConfirmed(String title, String text) {
        final Alert alert = new Alert(AlertType.WARNING, text, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        Optional<ButtonType> result = alert.showAndWait().filter(b -> b.equals(ButtonType.YES));
        return result.isPresent();
    }

    /**
     * Elapsed time from the app started in format h:m:s.
     *
     * @return time in string format
     */
    public String timeOn() {
        long now = System.currentTimeMillis();
        long dif = now - SharedData.get().TIME_STARTED;
        long hours = dif / HOUR;
        long minutes = dif % HOUR / MINUT;
        // long seconds = dif % HOUR / SECOND;
        return String.format("%02d:%02d", hours, minutes);
    }
}
