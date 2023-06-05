package com.wrmanager.wrmanagerfx.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AjouterClientDialogController {
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    Dialog<ButtonType> dialog;

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private Label AlertLbl;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    public TextField NomTfd;

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.OK);
        dialog.close();
    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

}
