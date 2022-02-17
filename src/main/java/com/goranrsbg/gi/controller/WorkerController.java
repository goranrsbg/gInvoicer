package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.dao.DAOworker;
import com.goranrsbg.gi.database.entity.Worker;
import com.goranrsbg.gi.database.entity.embedable.Address;
import com.goranrsbg.gi.database.entity.embedable.PersonName;
import com.goranrsbg.gi.etc.View;
import com.goranrsbg.gi.etc.ViewManager;
import java.io.IOException;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.SearchableComboBox;

/**
 *
 * @author goranrsbg
 */
public class WorkerController {

    private static final Logger LOGGER = LogManager.getLogger(WorkerController.class);

    @FXML
    private SearchableComboBox<Worker> itemsCombo;
    @FXML
    private Label labelName;
    @FXML
    private Label labelAddress;
    @FXML
    private Button actionButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label messageLabel;

    private Timeline messageClearTimeline;

    private List<Worker> data;

    public void initialize() {
        // init message Timeline, clear after timeout.
        messageClearTimeline = new Timeline(new KeyFrame(Duration.seconds(11d), e -> {
        }));
        messageClearTimeline.setOnFinished(e -> {
            messageLabel.textProperty().setValue("");
        });
        // init combo
        itemsCombo.setConverter(new StringConverter<Worker>() {
            @Override
            public String toString(Worker worker
            ) {
                if (worker == null) {
                    return "";
                }
                if (worker.getId() == 0) {
                    return "--new entry--";
                }
                return worker.getPersonName().toReverseString();
            }

            @Override
            public Worker fromString(String text) {
                return null;
            }
        });
        itemsCombo.getSelectionModel().selectedItemProperty().addListener(e -> {
            showItem();
            updateButtons();
        });
        // init data
        data = DAOworker.get().readAll();
        pushEmptyItem();
        updateComboData();
        selectFirst();
    }

    @FXML
    private void onEditName(ActionEvent event) {
        final Worker worker = itemsCombo.getSelectionModel().getSelectedItem();
        if (worker != null) {
            try {
                View<PersonNameController> node = ViewManager.load(ViewManager.FXML_PERSON_NAME);
                Window window = messageLabel.getScene().getWindow();
                Scene sene = new Scene(node.getNode());
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(sene);
                stage.initOwner(window);
                stage.initModality(Modality.WINDOW_MODAL);
                node.getController().connectPersonName(worker.getPersonName());
                stage.showAndWait();
                labelName.setText(worker.getPersonName().toReverseString());
            } catch (IOException ex) {
                LOGGER.error("Template fxml not loaded." + ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }

        }
    }

    @FXML
    private void onEditAddress(ActionEvent event) {
        final Worker worker = itemsCombo.getSelectionModel().getSelectedItem();
        if (worker != null) {
            try {
                View<AddressController> node = ViewManager.load(ViewManager.FXML_ADDRESS);
                Window window = messageLabel.getScene().getWindow();
                Scene sene = new Scene(node.getNode());
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(sene);
                stage.initOwner(window);
                stage.initModality(Modality.WINDOW_MODAL);
                node.getController().connectAddress(worker.getAddress());
                stage.showAndWait();
                labelAddress.setText(worker.getAddress().toString());
            } catch (IOException ex) {
                LOGGER.error("Template fxml not loaded." + ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void onSaveOrUpdate(ActionEvent event) {
    }

    @FXML
    private void onReset(ActionEvent event) {
    }

    @FXML
    private void onDelete(ActionEvent event) {
    }

    @FXML
    private void onCancel(ActionEvent event) {
        getWindow().hide();
    }

    private void showItem() {

    }

    private void updateButtons() {
        Worker worker = itemsCombo.getSelectionModel().getSelectedItem();
        if (worker != null) {
            boolean isSave = worker.getId() == 0L;
            actionButton.setText(isSave ? "Save" : "Update");
            deleteButton.setDisable(isSave);
        }
    }

    private Window getWindow() {
        return messageLabel.getScene().getWindow();
    }

    private void pushEmptyItem() {
        Worker worker = new Worker();
        worker.setPersonName(new PersonName());
        worker.setAddress(new Address());
        data.add(0, worker);
    }

    private void updateComboData() {
        itemsCombo.getItems().setAll(data);
    }

    private void selectFirst() {
        itemsCombo.getSelectionModel().selectFirst();
    }

}
