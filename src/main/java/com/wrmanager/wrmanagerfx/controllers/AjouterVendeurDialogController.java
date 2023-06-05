package com.wrmanager.wrmanagerfx.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AjouterVendeurDialogController {
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    private Dialog<ButtonType> dialog;

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private MFXButton AjouterEtResterButton;

    @FXML
    private Label AlertLbl;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private TextField NumTfd;

    @FXML
    private TextField PasswordTfd;


    @FXML
    private TextField UsernameTfd;

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    @FXML
    void AjouterButtonOnAction(ActionEvent event) {

    }

    @FXML
    void AjouterEtResterButtonOnAction(ActionEvent event) {

    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

    @FXML
    void CheckBoxOnAction(ActionEvent event) {

    }

}
