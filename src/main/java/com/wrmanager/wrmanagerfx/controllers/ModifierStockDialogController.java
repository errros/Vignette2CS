package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.models.SystemMeasure;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.wrmanager.wrmanagerfx.validation.Validators.*;
import static com.wrmanager.wrmanagerfx.Constants.*;

public class ModifierStockDialogController  implements Initializable {
    Dialog<ButtonType> dialog1;
    private Produit passedProduit;

    public Produit getPassedProduit() {
        return passedProduit;
    }

    public void setPassedProduit(Produit passedProduit) {
        this.passedProduit = passedProduit;

    }

    public Dialog<ButtonType> getDialog1() {
        return dialog1;
    }

    public void setDialog1(Dialog<ButtonType> dialog1) {
        this.dialog1 = dialog1;
    }

    @FXML
    private MFXButton ModifierButton;

    @FXML
    private Label AlertLbl;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private MFXDatePicker DateTfd;

    @FXML
    private TextField PrixAchatTfd;

    @FXML
    private TextField PrixVenteTfd;

    @FXML
    private TextField QtyTfd;

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    @FXML
    private Label uniteTF;

    @FXML
    private VBox vbox;

    @FXML
    void ModifierButtonOnAction(ActionEvent event) {
        var qty = QtyTfd.getText();
        var prixAchat = PrixAchatTfd.getText();
        var prixVente = PrixVenteTfd.getText();
        Date date = null;

            date = Date.valueOf(DateTfd.getValue());



            produitService.update(passedProduit,passedProduit.getCategorie());

            dialog1.setResult(ButtonType.OK);
            dialog1.close();

        }


    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog1.setResult(ButtonType.CLOSE);
        dialog1.close();
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AlertLbl.setVisible(false);
    }
}
