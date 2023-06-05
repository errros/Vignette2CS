package com.wrmanager.wrmanagerfx.controllers;
import com.wrmanager.wrmanagerfx.entities.GroupeFavoris;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AjouterGroupDialogController implements Initializable {


    private GroupeFavoris passedGroupeFavoris;

    public Dialog<ButtonType> getDialog1() {
        return dialog1;
    }

    public void setDialog1(Dialog<ButtonType> dialog1) {
        this.dialog1 = dialog1;
    }

    Dialog<ButtonType> dialog1;

    @FXML
    private MFXButton SupprimerButton;

    @FXML
    private HBox buttonsHb;

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private TextField NomTfd;

    @FXML
    private Label titleLabel;
    @FXML
    private Label AlertLbl;

    private Boolean onEditMode;

    @FXML
    void AjouterButtonOnAction(ActionEvent event) {

if(onEditMode){
  if  (update()) {
      dialog1.setResult(ButtonType.CLOSE);
      dialog1.close();

  } else {
      AlertLbl.setVisible(true);
      AlertLbl.setText("Veulleiz Entrer Un Nom Valid");
  }

}
else {

    if(retrieveAndAdd()) {

        dialog1.setResult(ButtonType.CLOSE);
        dialog1.close();

    }else {
        AlertLbl.setVisible(true);
        AlertLbl.setText("Veulleiz Entrer Un Nom Valid");
    }

}


    }


    @FXML
    void SupprimerButtonOnAction(ActionEvent event) {

          FavorieDialogController.removeGroupe(passedGroupeFavoris);

          dialog1.setResult(ButtonType.CLOSE);
        dialog1.close();


    }

    private Boolean update(){

        if(!NomTfd.getText().isEmpty()) {
            passedGroupeFavoris.setNom(NomTfd.getText());

            FavorieDialogController.renameGroupe(passedGroupeFavoris);
         return true;
        }
        return false;

    }
    private Boolean retrieveAndAdd(){
        if(!NomTfd.getText().isEmpty()) {
            FavorieDialogController.addGroupe(NomTfd.getText());
             return true;

        }
        return false;
    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog1.setResult(ButtonType.CLOSE);
        dialog1.close();
    }

    public Boolean getOnEditMode() {
        return onEditMode;
    }

    public void setOnEditMode(Boolean onEditMode) {
        this.onEditMode = onEditMode;
        if(onEditMode){

            titleLabel.setText("Renommer un Groupe");
            AjouterButton.setText("Renommer");
            NomTfd.setText(passedGroupeFavoris.getNom());
            buttonsHb.getChildren().add(1,SupprimerButton);

        }


    }

    public GroupeFavoris getPassedGroupeFavoris() {
        return passedGroupeFavoris;
    }

    public void setPassedGroupeFavoris(GroupeFavoris passedGroupeFavoris) {
        this.passedGroupeFavoris = passedGroupeFavoris;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AlertLbl.setVisible(false);
        buttonsHb.getChildren().remove(SupprimerButton);


    }
}