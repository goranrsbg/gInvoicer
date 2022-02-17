package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.entity.embedable.PersonName;
import com.goranrsbg.gi.etc.Functions;
import com.goranrsbg.gi.etc.UserNodeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author goranrsbg
 */
public class PersonNameController {

    @FXML
    private TextField tf_firstName;
    @FXML
    private TextField tf_lastName;

    private PersonName personName;

    private void initialize() {
        tf_firstName.setUserData("First name");
        tf_lastName.setUserData("Last name");
    }

    @FXML
    private void onOK(ActionEvent event) {
        try {
            validate();
            getWindow().hide();
        } catch (UserNodeException ex) {
            ex.getNode().requestFocus();
        }
    }

    @FXML
    private void onReset(ActionEvent event) {
        show();
    }

    @FXML
    private void onCancel(ActionEvent event) {
        getWindow().hide();
    }

    private void show() {
        tf_firstName.setText(personName.getFirstName());
        tf_lastName.setText(personName.getLastName());
    }

    public void connectPersonName(PersonName personName) {
        this.personName = personName;
        show();
    }

    private Window getWindow() {
        return tf_firstName.getScene().getWindow();
    }

    private void validate() throws UserNodeException {
        String fname = Functions.get().validateShort(tf_firstName);
        String sname = Functions.get().validateShort(tf_lastName);
        personName.setFirstName(fname);
        personName.setLastName(sname);
    }

}
