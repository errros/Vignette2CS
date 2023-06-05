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
        QtyTfd.setText(String.valueOf(passedProduit.getQtyTotale()));
        PrixAchatTfd.setText(String.valueOf(passedProduit.getPrixAchatUnite()));
        PrixVenteTfd.setText(String.valueOf(passedProduit.getPrixVenteUnite()));
        if(!passedProduit.getEstPerissable()) {
            DateTfd.setDisable(true);
        }else {
            Optional.ofNullable(passedProduit.getDatePeremption()).ifPresent(date -> DateTfd.setValue(date.toLocalDate()));
        }
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
        if (passedProduit.getEstPerissable() && Optional.ofNullable(DateTfd.getValue()).isPresent()) {

            date = Date.valueOf(DateTfd.getValue());
        }


        if(validateInput(qty,prixAchat,prixVente,passedProduit.getEstPerissable(),date)) {
            passedProduit.setQtyTotale(Float.valueOf(qty));
            passedProduit.setPrixAchatUnite(Integer.valueOf(prixAchat));
            passedProduit.setPrixVenteUnite(Integer.valueOf(prixVente));
            if (DateTfd.getValue() != null) {
                passedProduit.setDatePeremption(Date.valueOf(DateTfd.getValue()));
            }

            produitService.update(passedProduit,passedProduit.getCategorie());

            dialog1.setResult(ButtonType.OK);
            dialog1.close();

        }
    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog1.setResult(ButtonType.CLOSE);
        dialog1.close();
    }


    private Boolean validateInput(String qtyTotale , String prixAchatTotale , String prixVenteTotale,
                                  Boolean perissable , Date datePeeremption){


//STOCK NEGATIVE TEST
       String qtyValidation = passedProduit.getSystemMeasure() == SystemMeasure.UNITE ? isQtyIntValid(qtyTotale,true) : isQtyFloatValid(qtyTotale,true);

       if(!qtyValidation.equals("true")){
           AlertLbl.setVisible(true);
           AlertLbl.setText(qtyValidation);
           return false;
       }

        var prixAchatValidation = isQtyIntPositiveValid(prixAchatTotale,true,true);
        if(!prixAchatValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText("Le Prix d'Achat est un nombre rond positive");
            return false;
        }
        var prixVenteValidation = isQtyIntPositiveValid(prixAchatTotale,true,true);
        if(!prixVenteValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText("Le Prix d'Vente est un nombre rond positive");
            return false;
        }

        if(perissable && datePeeremption!= null){

            var dateValidation = isDatePeeremptionValid(datePeeremption.toString(),true);
            if(!dateValidation.equals("true")){
                AlertLbl.setVisible(true);
                AlertLbl.setText(dateValidation);
                return false;
            }


        }

        return true;

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AlertLbl.setVisible(false);
    }
}
