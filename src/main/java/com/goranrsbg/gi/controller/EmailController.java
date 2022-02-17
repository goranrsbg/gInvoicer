package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.dao.DAOemail;
import com.goranrsbg.gi.database.entity.Email;
import com.goranrsbg.gi.etc.Functions;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

/**
 *
 * @author goranrsbg
 */
public class EmailController {

    @FXML
    private Label messageLabel;
    @FXML
    private SearchableComboBox<Email> itemsCombo;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField emailText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField outgoingServerText;
    @FXML
    private TextField portText;
    @FXML
    private Button actionButton;
    @FXML
    private Button deleteButton;

    private Timeline messageClearTimeline;

    private List<Email> data;

    public void initialize() {
        //init message Timeline, clear after timeout.
        messageClearTimeline = new Timeline(new KeyFrame(Duration.seconds(11d), e -> {
        }));
        messageClearTimeline.setOnFinished(e -> {
            messageLabel.textProperty().setValue("");
        });
        // init combobox
        itemsCombo.setConverter(new StringConverter<Email>() {
            @Override
            public String toString(Email email) {
                if (email == null) {
                    return "";
                }
                if (email.getId() == 0L) {
                    return "--new entry--";
                }
                return email.toShortString();
            }

            @Override
            public Email fromString(String string) {
                return null;
            }

        });
        itemsCombo.getSelectionModel().selectedItemProperty().addListener(e -> {
            showItem();
            updateButtons();
        });
        // collect and show
        data = DAOemail.get().readAll();
        pushEmptyItem();
        updateComboData();
        itemsCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onSaveOrUpdate(ActionEvent event) {
        final int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final Email email = data.get(index);
        final boolean isSave = email.getId() == 0L;
        try {
            Email old = validate(email);
            if (isSave) {
                DAOemail.get().save(email);
                pushEmptyItem();
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().selectFirst();
                showMessage("Saved. " + email.toShortString());
            } else {
                Email merged = DAOemail.get().update(email);
                data.set(index, merged);
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().select(merged);
                showMessage(String.format("Updated. %s to %s", old.toShortString(), merged.toShortString()));
            }
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        itemsCombo.requestFocus();
    }

    @FXML
    private void onReset(ActionEvent event) {
        showItem();
        itemsCombo.requestFocus();
    }

    @FXML
    private void onDelete(ActionEvent event) {
        final int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final Email email = data.get(index);
        if (index == 0) {
            return;
        }
        if (Functions.get().isConfirmed("Delete Warning", "Item < " + email.toShortString() + " > will be deleted.\nAre you sure?")) {
            try {
                DAOemail.get().delete(email);
                showMessage("Deleted. " + email.toShortString());
                data.remove(index);
                updateComboData();
                itemsCombo.getSelectionModel().selectFirst();
            } catch (Exception e) {
                showMessage(e.getMessage());
            }
        }
        itemsCombo.getScene().getWindow().requestFocus();
        itemsCombo.requestFocus();
    }

    @FXML
    private void onCancel(ActionEvent event) {
        itemsCombo.getScene().getWindow().hide();
    }

    private void showItem() {
        Email email = itemsCombo.getSelectionModel().getSelectedItem();
        if (email != null) {
            descriptionText.textProperty().setValue(email.getDescription());
            emailText.textProperty().setValue(email.getEmail());
            passwordText.textProperty().setValue(email.getPassword());
            outgoingServerText.textProperty().setValue(email.getOutServer());
            portText.textProperty().setValue(email.getSmtpPort() == 0 ? "" : email.getSmtpPort() + "");
        }
    }

    private void updateButtons() {
        Email email = itemsCombo.getSelectionModel().getSelectedItem();
        if (email != null) {
            final boolean isSave = email.getId() == 0;
            actionButton.setText(isSave ? "Save" : "Update");
            deleteButton.setDisable(isSave);
        }
    }

    private void pushEmptyItem() {
        final Email email = new Email();
        data.add(0, email);
    }

    private void updateComboData() {
        itemsCombo.getItems().setAll(data);
    }

    private void sortData() {
        data.sort((o1, o2) -> o1.getDescription().compareTo(o2.getDescription()));
    }

    private void showMessage(String message) {
        messageLabel.textProperty().setValue(message);
        messageClearTimeline.playFromStart();
    }

    private Email validate(Email email) throws Exception {
        final Email old = new Email(email);
        String descripton = Functions.get().validateShort(descriptionText);
        String emailString = Functions.get().validateEmail(emailText);
        String password = Functions.get().validateShort(passwordText);
        String outServer = Functions.get().validateLong(outgoingServerText);
        int port = Functions.get().validateNumber(portText);
        email.setDescription(descripton);
        email.setEmail(emailString);
        email.setPassword(password);
        email.setOutServer(outServer);
        email.setSmtpPort(port);
        return old;
    }

}
