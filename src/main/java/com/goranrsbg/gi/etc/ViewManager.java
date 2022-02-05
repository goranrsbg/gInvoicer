package com.goranrsbg.gi.etc;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages application nodes and controllers.
 *
 * @author Goran Cvijanovic
 */
public class ViewManager {

    private static final Logger LOGGER = LogManager.getLogger(ViewManager.class);

    public static final String FXML_MAIN = "main";
    public static final String FXML_BH = "bankHoliday";
    public static final String FXML_TITLE = "title";
    public static final String FXML_TRAINING = "training";
    public static final String FXML_EMAIL = "email";

    private ViewManager() {
    }

    /**
     * Loads <code>fxml</code> view.
     *
     * @param <T>  Controller Class.
     * @param name <code>Fxml</code> file name.
     *
     * @return View Object containing Controller and Node.
     *
     * @throws IOException
     */
    public static <T> View<T> load(String name) throws IOException {
        String resource = String.format("/com/goranrsbg/gi/fxml/%s.fxml", name);
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(resource));
        Parent parent = loader.load();
        View<T> view = new View<>(parent, loader.getController());
        return view;
    }

}
