package com.goranrsbg.gi;

import com.goranrsbg.apppreloader.SimplePreloader;
import com.goranrsbg.gi.database.DBFactory;
import com.goranrsbg.gi.etc.Formats;
import com.goranrsbg.gi.etc.PropManager;
import com.goranrsbg.gi.etc.ViewManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GInvoicer extends Application {

    private static final Logger LOGGER = LogManager.getLogger(GInvoicer.class);

    @Override
    public void init() {
        if (!DBFactory.connect()) {
            Platform.exit();
        }
    }

    @Override
    public void stop() {
        DBFactory.close();
    }

    @Override
    public void start(Stage stage) {
        try {
            Scene scene = new Scene(ViewManager.load(ViewManager.FXML_MAIN).getNode(), 640d, 480d);
            stage.setScene(scene);
            stage.setMinWidth(640d);
            stage.setMinHeight(480d);
            stage.setResizable(true);
            // stage.setMaximized(true);
            stage.setTitle(PropManager.getProperty(PropManager.APP_NAME));
            stage.getIcons().add(Formats.THE_LOGO);
            stage.show();
        } catch (IOException ex) {
            LOGGER.error("Main fxml file not loaded: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", SimplePreloader.class.getCanonicalName());
        launch();
    }

}
