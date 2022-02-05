package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.dao.DAOtitle;
import com.goranrsbg.gi.database.entity.Title;
import com.goranrsbg.gi.etc.Functions;
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
public class TitleController {

    @FXML
    private Label messageLabel;
    @FXML
    private SearchableComboBox<Title> itemsCombo;
    @FXML
    private Button actionButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField titleShortText;
    @FXML
    private TextField titleLongText;

    private Timeline messageClearTimeline;

    private List<Title> data;

    public void initialize() {
        //init message Timeline, clear after timeout.
        messageClearTimeline = new Timeline(new KeyFrame(Duration.seconds(11d), e -> {
        }));
        messageClearTimeline.setOnFinished(e -> {
            messageLabel.textProperty().setValue("");
        });
        // init combobox
        itemsCombo.setConverter(new StringConverter<Title>() {
            @Override
            public String toString(Title title) {
                if (title == null) {
                    return "";
                }
                if (title.getId() == 0L) {
                    return "--new entry--";
                }
                return title.toShortString();
            }

            @Override
            public Title fromString(String string) {
                return null;
            }
        });
        itemsCombo.getSelectionModel().selectedItemProperty().addListener(e -> {
            showItem();
            updateButtons();
        });
        // collect and show
        data = DAOtitle.get().readAll();
        pushEmptyItem();
        updateComboData();
        itemsCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onSaveOrUpdate(ActionEvent event) {
        final int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final Title title = data.get(index);
        final boolean isSave = title.getId() == 0L;
        try {
            Title old = validate(title);
            if (isSave) {
                DAOtitle.get().save(title);
                pushEmptyItem();
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().selectFirst();
                showMessage("Saved. " + title.toShortString());
            } else {
                Title merged = DAOtitle.get().update(title);
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
        final Title title = data.get(index);
        if (index == 0) {
            return;
        }
        if (Functions.get().isConfirmed("Delete Warning", "Item < " + title.toShortString() + " > will be deleted.\nAre you sure?")) {
            try {
                DAOtitle.get().delete(title);
                showMessage("Deleted. " + title.toShortString());
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
        messageLabel.getScene().getWindow().hide();
    }

    private Title validate(Title title) throws Exception {
        String titleShort = Functions.get().validateTextLength(titleShortText.getText(), "Title", 1);
        String titleLong = Functions.get().validateTextLength(titleLongText.getText(), "Title long", 2);
        final Title old = new Title(title);
        title.setName(titleLong);
        title.setShortName(titleShort);
        return old;
    }

    private void showItem() {
        final Title title = itemsCombo.getSelectionModel().getSelectedItem();
        if (title != null) {
            titleShortText.textProperty().setValue(title.getShortName());
            titleLongText.textProperty().setValue(title.getName());
        }
    }

    private void updateButtons() {
        Title title = itemsCombo.getSelectionModel().getSelectedItem();
        if (title != null) {
            boolean isSave = title.getId() == 0L;
            actionButton.setText(isSave ? "Save" : "Update");
            deleteButton.setDisable(isSave);
        }
    }

    private void updateComboData() {
        itemsCombo.getItems().setAll(data);
    }

    private void sortData() {
        data.sort((o1, o2) -> o1.getShortName().compareTo(o2.getShortName()));
    }

    private void pushEmptyItem() {
        final Title title = new Title();
        data.add(0, title);
    }

    private void showMessage(String message) {
        messageLabel.textProperty().setValue(message);
        messageClearTimeline.playFromStart();
    }

}
