package com.goranrsbg.gi.etc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author goranrsbg
 */
public class Functions {

    private static final Logger LOGGER = LogManager.getLogger(Functions.class);

    private static final Functions INSTANCE = new Functions();

    private Pattern ptLetNum;
    private Pattern ptLetters;
    private Pattern ptEmail;
    private Pattern ptNumber;

    private final long HOUR = 3600000L;
    private final long MINUT = 60000L;
    private final long SECOND = 1000L;

    private Functions() {
        initPatterns();
    }

    private void initPatterns() {
        ptLetNum = Pattern.compile("[\\p{L} 0-9\\.@!#$%&'*+/= ?^_`{|}~-]+");
        ptLetters = Pattern.compile("[\\p{L}\\.@!#$%&'*+/= ?^_`{|}~-]+");
        ptEmail = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        ptNumber = Pattern.compile("\\d+\\.?\\d*");
    }

    public static Functions get() {
        return INSTANCE;
    }

    /**
     * Validates text size of minimum one character.
     *
     * @param textField node to be validate.
     *
     * @return text from textField
     *
     * @throws com.goranrsbg.gi.etc.UserNodeException
     *
     */
    public String validateShort(TextField textField) throws UserNodeException {
        return validateSize(textField, 1);
    }

    /**
     * Validates text size of minimum two characters.
     *
     * @param textField
     *
     * @return trimmed text
     *
     * @throws com.goranrsbg.gi.etc.UserNodeException
     *
     */
    public String validateLong(TextField textField) throws UserNodeException {
        return validateSize(textField, 2);
    }

    /**
     * Validate text of <code>textfield</code>
     *
     * @param textField node to be validate.
     * @param size      minimum size for text.
     *
     * @return text from <code>textfield</code>.
     *
     * @throws UserNodeException
     */
    public String validateSize(TextField textField, int size) throws UserNodeException {
        String text = validateBasic(textField);
        String name = (String) textField.getUserData();
        if (!ptLetters.matcher(text).matches()) {
            throw new UserNodeException(String.format("%s have illegal letters.", name), textField);
        }
        if (text.length() < size) {
            throw new UserNodeException(String.format("%s doesn't have minimum length of %d.", name, size), textField);
        }
        return text;
    }

    /**
     * Validates text against null and email pattern.True if text
     * is valid.<p>
     * <b>Pattern:</b>
     * <code>[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?</code>
     *
     * @param textField node to be validate.
     *
     * @return trimmed text
     *
     * @throws com.goranrsbg.gi.etc.UserNodeException
     *
     */
    public String validateEmail(TextField textField) throws UserNodeException {
        String text = validateBasic(textField);
        if (!ptEmail.matcher(text).matches()) {
            throw new UserNodeException(String.format("%s is not valid email.", text), textField);
        }
        return text;
    }

    /**
     * Validates text against null and is number pattern.True if text
     * is valid.<p>
     * <p>
     * <b>Pattern:</b><code>\\d+\\.?\\d*</code>
     *
     * @param textField
     *
     * @return number from text.
     *
     * @throws com.goranrsbg.gi.etc.UserNodeException
     *
     */
    public int validateNumber(TextField textField) throws UserNodeException {
        String text = validateBasic(textField);
        String name = (String) textField.getUserData();
        if (!ptNumber.matcher(text).matches()) {
            throw new UserNodeException(String.format("%s is not number of %s.", text, name), textField);
        }
        return Integer.parseInt(text);
    }

    public String validateLetNum(TextField textField) throws UserNodeException {
        String text = validateBasic(textField);
        String name = (String) textField.getUserData();
        if (!ptLetNum.matcher(text).matches()) {
            throw new UserNodeException(String.format("%s is not format of %s.", text, name), textField);
        }
        return text;
    }

    /**
     * Validate if text of <code>textfield</code> is null or size is 0.
     *
     * @param textField
     *
     * @return
     *
     * @throws UserNodeException
     */
    private String validateBasic(TextField textField) throws UserNodeException {
        String text = textField.getText();
        String name = (String) textField.getUserData();
        if (text == null || text.trim().isEmpty()) {
            throw new UserNodeException(String.format("%s can not be empty.", name), textField);
        }
        return text.trim();
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
     * Creates alert with given title and text then
     * wait and validate user response.
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
