package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.etc.Formats;
import com.goranrsbg.gi.etc.Functions;
import com.goranrsbg.gi.etc.View;
import com.goranrsbg.gi.etc.ViewManager;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Goran Cvijanovic
 */
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    @FXML
    private Label labelTime;

    @FXML
    private Label labelLastWE;

    @FXML
    private Label labelMessage;

    @FXML
    private Label labelTimeON;

    @FXML
    private BorderPane mainPane;

    public void initialize() {
        initClock();

    }

    private void initClock() {
        Timeline clockTimes = new Timeline(new KeyFrame(Duration.millis(100d), e -> {
            labelTime.textProperty().setValue(LocalDateTime.now().format(Formats.TIME));
            labelLastWE.textProperty().setValue("WE " + Functions.get().lastWeekEnd().format(Formats.DAY));
            labelTimeON.textProperty().setValue(Functions.get().timeOn());
        }));
        clockTimes.setCycleCount(Animation.INDEFINITE);
        clockTimes.play();
    }

    @FXML
    private void onQuit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onTest(ActionEvent event) {

    }

    @FXML
    private void onBankHoliday(ActionEvent event) {
        try {
            View<BankHolidayController> node = ViewManager.load(ViewManager.FXML_BH);
            loadSubview(node.getNode(), "Bank Holiday");
        } catch (IOException ex) {
            LOGGER.error("Template fxml not loaded." + ex.getMessage());
        }
    }

    @FXML
    private void onTitles(ActionEvent event) {
        try {
            View<TitleController> node = ViewManager.load(ViewManager.FXML_TITLE);
            loadSubview(node.getNode(), "Titles");
        } catch (IOException ex) {
            LOGGER.error("Template fxml not loaded." + ex.getMessage());
        }
    }

    @FXML
    private void onTraining(ActionEvent event) {
        try {
            View<TitleController> node = ViewManager.load(ViewManager.FXML_TRAINING);
            loadSubview(node.getNode(), "Trainings");
        } catch (IOException ex) {
            LOGGER.error("Template fxml not loaded." + ex.getMessage());
        }

    }

    private void loadSubview(Parent node, String title) {
        Scene sene = new Scene(node);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setScene(sene);
        stage.setTitle(title);
        stage.getIcons().add(Formats.THE_LOGO);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void onEmail(ActionEvent event) {
        try {
            View<EmailController> node = ViewManager.load(ViewManager.FXML_EMAIL);
            loadSubview(node.getNode(), "Email");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
            LOGGER.error("Template fxml not loaded." + ex.getMessage());
        }
    }

}
