package com.goranrsbg.gi.controller;

import com.goranrsbg.gi.database.entity.embedable.Address;
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
public class AddressController {

    @FXML
    private TextField tf_street;
    @FXML
    private TextField tf_town;
    @FXML
    private TextField tf_zip;

    private Address address;

    public void initialize() {
        tf_street.setUserData("Street");
        tf_town.setUserData("Town");
        tf_zip.setUserData("Zip code");
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
        tf_street.setText(address.getStreet());
        tf_town.setText(address.getTown());
        tf_zip.setText(address.getZipcode());
    }

    public void connectAddress(Address address) {
        this.address = address;
    }

    private Window getWindow() {
        return tf_street.getScene().getWindow();
    }

    private void validate() throws UserNodeException {
        String street = Functions.get().validateLetNum(tf_street);
        String town = Functions.get().validateLetNum(tf_town);
        String zip = Functions.get().validateLetNum(tf_zip).toUpperCase();
        address.setStreet(street);
        address.setTown(town);
        address.setZipcode(zip);
    }

}
