package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import static com.wrmanager.wrmanagerfx.Constants.*;
import java.net.URL;
import java.util.ResourceBundle;

import static com.wrmanager.wrmanagerfx.validation.Validators.*;

public class AjouterFournisseurDialogController implements Initializable {


    private Dialog<ButtonType> dialog;
    //passed from the the first
    private Fournisseur passedFournisseur;

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private MFXButton AjouterEtResterButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private TextField NomTfd;

    @FXML
    private TextField PrenomTfd;

    @FXML
    private TextField Num1Tfd;

    @FXML
    private TextField Num2Tfd;
    @FXML
    private TextField AddressTfd;

    @FXML
    private Label titleLabel;
    @FXML
    private Label AlertLbl;



    @FXML
    private HBox buttonsHb;


    private BooleanProperty onEditMode = new SimpleBooleanProperty(false);



    private Boolean addFournisseur(){
        var nom = NomTfd.getText();
        var prenom = PrenomTfd.getText();
        var adr = AddressTfd.getText();
        var num1 = Num1Tfd.getText();
        var num2 = Num2Tfd.getText();

        Boolean isValid = validateInput(nom,prenom,adr,num1,num2);
        if(isValid){
          Fournisseur f = Fournisseur.builder().nom(nom).prenom(prenom).adr(adr).num1(num1).num2(num2).build();

            fournisseurService.save(f);

            return true;
        }


        return false;



    }

    //Modifier button too
    @FXML
    void AjouterButtonOnAction(ActionEvent event) {

        if(addFournisseur()) {
            dialog.setResult(ButtonType.CLOSE);
            dialog.close();

        }
    }


    EventHandler<ActionEvent> ModifierButtonOnActionOnEditMode() {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(updateFournisseur()) {
                    dialog.setResult(ButtonType.CLOSE);
                    dialog.close();

                }

            }


        };

    }

    //on editMode
    private Boolean updateFournisseur() {
    var nom = NomTfd.getText();
    var prenom = PrenomTfd.getText();
    var adr = AddressTfd.getText();
    var num1 = Num1Tfd.getText();
    var num2 = Num2Tfd.getText();

    Boolean isValid = validateInput(nom,prenom,adr,num1,num2);
    if(isValid){
        passedFournisseur.setPrenom(prenom);
        passedFournisseur.setNom(nom);
        passedFournisseur.setAdr(adr);
        passedFournisseur.setNum1(num1);
        passedFournisseur.setNum2(num2);
        fournisseurService.update(passedFournisseur);

        return true;
    }


    return false;

    }


    @FXML
    void AjouterEtResterButtonOnAction(ActionEvent event) {


       if(addFournisseur()){
           viderInput();
       }
    }

    private void viderInput(){

        NomTfd.setText("");
        PrenomTfd.setText("");
        AddressTfd.setText("");
        Num1Tfd.setText("");
        Num2Tfd.setText("");
        AlertLbl.setVisible(false);

    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }
    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }


    public Fournisseur getPassedFournisseur() {
        return passedFournisseur;
    }

    private void updateInputTextFields(){
        NomTfd.setText(passedFournisseur.getNom());
        PrenomTfd.setText(passedFournisseur.getPrenom());
        AddressTfd.setText(passedFournisseur.getAdr());
        Num1Tfd.setText(passedFournisseur.getNum1());
        Num2Tfd.setText(passedFournisseur.getNum2());

    }

    public void setPassedFournisseur(Fournisseur passedFournisseur) {
        this.passedFournisseur = passedFournisseur;

        updateInputTextFields();

    }

    public boolean isOnEditMode() {
        return onEditMode.get();
    }

    public BooleanProperty onEditModeProperty() {
        return onEditMode;
    }

    public void setOnEditMode(boolean onEditMode) {
        this.onEditMode.set(onEditMode);
    }


    private Boolean validateInput(String nom , String prenom , String adr , String num1 , String num2) {
        var nomValidation = isNomValid(nom,true);
        if(!nomValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(nomValidation);
            return false;
        }

        var prenomValidation = isPrenomValid(prenom,false);
        if(!prenomValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(prenomValidation);
            return false;
        }

        var adrValidation = isAdrValid(adr,false);
        if(!adrValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(adrValidation);
            return false;
        }

        var num1Validation = isNumValid(num1,false);
        if(!num1Validation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(num1Validation);
            return false;
        }

        var num2Validation = isNumValid(num2,false);
        if(!num2Validation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(num2Validation);
            return false;
        }

        return true;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //Ajouter btn turn into ModifierBTN

        AlertLbl.setVisible(false);
        onEditMode.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    titleLabel.setText("Modifier un Fournisseur");
                    AjouterButton.setText("Modifier");
                    AjouterButton.setOnAction(ModifierButtonOnActionOnEditMode());
                    buttonsHb.getChildren().remove(AjouterEtResterButton);
                }

            }
        });

    }




}