package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.entities.Categorie;
import com.wrmanager.wrmanagerfx.entities.Produit;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.wrmanager.wrmanagerfx.validation.Validators.*;
import static com.wrmanager.wrmanagerfx.Constants.*;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AjouterCategoryDialogController implements Initializable {
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
    private MFXButton AjouterEtResterButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private TextField JoursAlerteTf;

    @FXML
    private TextField MargeTfd;

    @FXML
    private TextField NomTfd;

    @FXML
    private TextField QTeAlerte;

    @FXML
    private Label uniteTF;



    @FXML
    void AjouterButtonOnAction(ActionEvent event) {


      if(retrieveAndAdd()) {
          dialog.setResult(ButtonType.YES);
          dialog.close();

      }
    }



    private Boolean retrieveAndAdd(){
        var nom = NomTfd.getText();
        var marge = MargeTfd.getText();
        var qty = QTeAlerte.getText();
        var jrs = JoursAlerteTf.getText();

        if(validateInput(nom,qty,jrs,marge)) {
            Categorie categorie = Categorie.builder().nom(nom).marge(Math.round(Float.valueOf(marge))).
                    qtyAlerte(Float.valueOf(qty)).joursAlerte(Math.round(Float.valueOf(jrs))).build();

           var addedCategorie = categorieService.save(categorie);


            return true;

        }
return false;

    }

    private Boolean validateInput(String nom , String qty, String jours,String marge){

        var nomValidation = isNomValid(nom,true);
        if(!nomValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(nomValidation);
            return false;
        }
        var margeValidation = isQtyIntPositiveValid(marge,true,true);
        if(!margeValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText("la marge doit etre un nombre rond");
            return false;
        }

        var qtyValidation = isQtyFloatPositiveValid(qty,true,true);
        if(!qtyValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(qtyValidation);
            return false;
        }

        var joursValidation = isQtyIntPositiveValid(jours,true,true);
        if(!joursValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText("le nombre des jours doit etre superieur a 0");
            return false;
        }


        return true;



    }


    @FXML
    void AjouterEtResterButtonOnAction(ActionEvent event) {
      if(retrieveAndAdd()){
          viderInput();
      }
    }
private void viderInput(){
    NomTfd.setText("");
    QTeAlerte.setText("");
    JoursAlerteTf.setText("");
    MargeTfd.setText("");

}

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
      AlertLbl.setVisible(false);


      //SET DEFAULT VALUES FROM PREFERENCES




    }
}