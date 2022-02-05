package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.dao.DAObankHoliday;
import com.goranrsbg.gi.database.entity.BankHoliday;
import com.goranrsbg.gi.etc.Formats;
import com.goranrsbg.gi.etc.Functions;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

/**
 *
 * @author goranrsbg
 */
public class BankHolidayController {

    @FXML
    private Button actionButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField nameText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private SearchableComboBox<BankHoliday> itemsCombo;
    @FXML
    private Label messageLabel;

    private Timeline messageClearTimeline;

    private List<BankHoliday> data;

    public void initialize() {
        //init message Timeline, clear after timeout.
        messageClearTimeline = new Timeline(new KeyFrame(Duration.seconds(11d), e -> {
        }));
        messageClearTimeline.setOnFinished(e -> {
            messageLabel.textProperty().setValue("");
        });
        // init combo
        itemsCombo.setConverter(new StringConverter<BankHoliday>() {
            @Override
            public String toString(BankHoliday bh) {
                if (bh == null) {
                    return "";
                }
                if (bh.getId() == 0) {
                    return "--new entry--";
                }
                return bh.toShortString();
            }

            @Override
            public BankHoliday fromString(String string) {
                return null;
            }
        });
        itemsCombo.getSelectionModel().selectedItemProperty().addListener(e -> {
            showItem();
            updateButtons();
        });
        // init date picker
        datePicker.setPromptText("day/month/year");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date == null ? "" : date.format(Formats.DAY);
            }

            @Override
            public LocalDate fromString(String text) {
                return (text == null || text.isEmpty()) ? null : LocalDate.parse(text, Formats.DAY);
            }
        });
        datePicker.setDayCellFactory((t) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    itemsCombo.getItems().stream()
                            .filter(e -> Objects.equals(e.getDate(), item))
                            .findFirst()
                            .ifPresent(e -> {
                                setStyle("-fx-font-weight:bold;-fx-text-fill:yellow;");
                                setTooltip(new Tooltip(e.getName()));
                            });
                }
            };
        });
        // init selected item
        data = DAObankHoliday.get().readAll();
        pushEmptyItem();
        updateComboData();
        itemsCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onDatePicker(ActionEvent event) {
        final LocalDate date = datePicker.getValue();
        itemsCombo.getItems().stream()
                .filter(e -> Objects.equals(e.getDate(), date))
                .findFirst()
                .ifPresent(e -> itemsCombo.getSelectionModel().select(e));
    }

    @FXML
    private void onSaveOrUpdate(ActionEvent event) {
        final int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final BankHoliday bh = data.get(index);
        final boolean isSave = bh.getId() == 0L;
        try {
            BankHoliday old = validate(bh);
            if (isSave) {
                DAObankHoliday.get().save(bh);
                pushEmptyItem();
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().selectFirst();
                showMessage("Saved. " + bh.toShortString());
            } else {
                BankHoliday merged = DAObankHoliday.get().update(bh);
                data.set(index, merged);
                sortData();
                updateComboData();
                itemsCombo.getSelectionModel().select(merged);
                showMessage(String.format("Updated. %s to %s.", old.toShortString(), merged.toShortString()));
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
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
    private void onCancel(ActionEvent event) {
        messageLabel.getScene().getWindow().hide();
    }

    @FXML
    private void onDelete(ActionEvent event) {
        final int index = itemsCombo.getSelectionModel().getSelectedIndex();
        final BankHoliday bh = itemsCombo.getSelectionModel().getSelectedItem();
        if (index == 0) {
            return;
        }
        if (Functions.get().isConfirmed("Delete Warning", "Item < " + bh.toShortString() + " > will be deleted.\nAre you sure?")) {
            try {
                DAObankHoliday.get().delete(bh);
                showMessage("Deleted. " + bh.toShortString());
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

    /**
     * Create empty item and add it as first item in
     * items list.
     *
     * @return New empty not null entity instance.
     */
    private void pushEmptyItem() {
        final BankHoliday bh = new BankHoliday();
        data.add(0, bh);
    }

    /**
     * Gets currently selected item from combo box and populate fields with item
     * values. Action and delete buttons are styled accordingly.
     *
     * @param resetMessage if true message label text is set to empty string if
     *                     not unchanged.
     */
    private void showItem() {
        final BankHoliday bh = itemsCombo.getSelectionModel().getSelectedItem();
        if (bh != null) {
            nameText.setText(bh.getName());
            datePicker.setValue(bh.getDate());
        }
    }

    private void updateButtons() {
        BankHoliday bh = itemsCombo.getSelectionModel().getSelectedItem();
        if (bh != null) {
            boolean isSave = bh.getId() == 0L;
            actionButton.setText(isSave ? "Save" : "Update");
            deleteButton.setDisable(isSave);
        }
    }

    /**
     * Performs validation of all fields and if all are valid then
     * the entity initiated.
     *
     * @param bh BankHoliday entity to be initiated after validation.
     *
     * @throws Exception New exception with message about not valid value.
     */
    private BankHoliday validate(BankHoliday bh) throws Exception {
        String name = Functions.get().validateTextLength(nameText.getText(), "Name", 2);
        LocalDate date = datePicker.getValue();
        if (date == null) {
            throw new Exception("Date must be picked.");
        }
        BankHoliday old = new BankHoliday(bh);
        bh.setName(name);
        bh.setDate(date);
        return old;
    }

    /**
     * Display given message in message label.
     *
     * @param message Text to be displayed in message label.
     */
    private void showMessage(String message) {
        messageLabel.textProperty().setValue(message);
        messageClearTimeline.playFromStart();
    }

    private void sortData() {
        data.sort((o1, o2) -> {
            if (o1.getDate() == null) {
                return -1;
            }
            if (o2.getDate() == null) {
                return 1;
            }
            return -o1.getDate().compareTo(o2.getDate());
        });
    }

    private void updateComboData() {
        itemsCombo.getItems().setAll(data);
    }

}
