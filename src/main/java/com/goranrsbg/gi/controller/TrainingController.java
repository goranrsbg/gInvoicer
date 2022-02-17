package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.dao.DAOtraning;
import com.goranrsbg.gi.database.entity.Training;
import com.goranrsbg.gi.etc.Functions;
import com.goranrsbg.gi.etc.UserNodeException;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

/**
 *
 * @author goranrsbg
 */
public class TrainingController {

    @FXML
    private Label messageLabel;
    @FXML
    private SearchableComboBox<Training> itemsCombo;
    @FXML
    private TextField trainingNameText;
    @FXML
    private TextField trainingDescriptionText;
    @FXML
    private Button actionButton;
    @FXML
    private Button deleteButton;

    private Timeline messageClearTimeline;

    private List<Training> data;

    public void initialize() {
        // texts
        trainingNameText.setUserData("Training Name");
        trainingDescriptionText.setUserData("Training Description");
        // message timeline
        messageClearTimeline = new Timeline(new KeyFrame(Duration.seconds(11d), e -> {
        }));
        messageClearTimeline.setOnFinished(e -> {
            messageLabel.textProperty().setValue("");
        });
        // init combobox
        itemsCombo.setConverter(new StringConverter<Training>() {
            @Override
            public String toString(Training t) {
                if (t == null) {
                    return "";
                }
                if (t.getId() == 0) {
                    return "--new entry--";
                }
                return t.toShortString();
            }

            @Override
            public Training fromString(String s) {
                return null;
            }
        });
        itemsCombo.getSelectionModel().selectedItemProperty().addListener(e -> {
            showItem();
            updateButtons();
        });
        // collect and show
        data = DAOtraning.get().readAll();
        pushEmeptyItem();
        updateComboData();
        itemsCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onSaveOrUpdate(ActionEvent event) {
        final int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final Training training = data.get(index);
        final boolean isSave = training.getId() == 0;
        try {
            Training old = validate(training);
            if (isSave) {
                DAOtraning.get().save(training);
                pushEmeptyItem();
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().selectFirst();
                showMessage("Saved. " + training.toShortString());
            } else {
                Training merged = DAOtraning.get().update(training);
                data.set(index, merged);
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().select(merged);
                showMessage(String.format("Updated. %s to %s.", old.toShortString(), merged.toShortString()));
            }
            itemsCombo.requestFocus();
        } catch (UserNodeException e) {
            showMessage(e.getMessage());
            e.getNode().requestFocus();
        }
    }

    @FXML
    private void onReset(ActionEvent event) {
        showItem();
        itemsCombo.requestFocus();
    }

    @FXML
    private void onDelete(ActionEvent event) {
        int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final Training training = data.get(index);
        if (index == 0) {
            return;
        }
        if (Functions.get().isConfirmed("Delete warning.", "Item < " + training.toShortString() + " > will be deleted.\nAre you sure?")) {
            try {
                DAOtraning.get().delete(training);
                data.remove(index);
                updateComboData();
                itemsCombo.getSelectionModel().selectFirst();
            } catch (Exception e) {
                showMessage(e.toString());
            }
        }
        itemsCombo.getScene().getWindow().requestFocus();
        itemsCombo.requestFocus();
    }

    @FXML
    private void onCancel(ActionEvent event) {
        messageLabel.getScene().getWindow().hide();
    }

    private void pushEmeptyItem() {
        Training training = new Training();
        data.add(0, training);
    }

    private void showItem() {
        final Training training = itemsCombo.getSelectionModel().getSelectedItem();
        if (training != null) {
            trainingNameText.textProperty().setValue(training.getName());
            trainingDescriptionText.textProperty().setValue(training.getDescription());
        }
    }

    private void updateButtons() {
        Training training = itemsCombo.getSelectionModel().getSelectedItem();
        if (training != null) {
            boolean isSave = training.getId() == 0L;
            actionButton.setText(isSave ? "Save" : "Update");
            deleteButton.setDisable(isSave);
        }
    }

    private void updateComboData() {
        itemsCombo.getItems().setAll(data);
    }

    private void sortData() {
        data.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
    }

    private Training validate(Training training) throws UserNodeException {
        String name = Functions.get().validateShort(trainingNameText);
        String descripton = Functions.get().validateLong(trainingDescriptionText);
        Training old = new Training(training);
        training.setName(name);
        training.setDescription(descripton);
        return old;
    }

    private void showMessage(String message) {
        messageLabel.textProperty().setValue(message);
        messageClearTimeline.playFromStart();
    }

}
