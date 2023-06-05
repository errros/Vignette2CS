package com.wrmanager.wrmanagerfx.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OuiNonDialogController implements Initializable {
    Dialog<ButtonType> dialog;
    @FXML
    private MFXButton NonButton;

    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    @FXML
    private MFXButton OuiButton;

    @FXML
    public Label SubjectLabel;

    @FXML
    public  Label TitleLabel;

    @FXML
    void NonButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.NO);
        dialog.close();
    }

    @FXML
    void OuiButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.YES);
        dialog.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
