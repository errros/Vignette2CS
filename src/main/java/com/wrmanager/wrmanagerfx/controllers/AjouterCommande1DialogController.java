package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Commande;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;
import static com.wrmanager.wrmanagerfx.validation.Validators.*;
import static com.wrmanager.wrmanagerfx.validation.Validators.isNumValid;

public class AjouterCommande1DialogController implements Initializable {


    private Dialog<ButtonType> dialog;
    //passed from the the first
    private Commande passedCommande;




    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    @FXML
    private TextField AddressTfd;

    @FXML
    private MFXButton ContinuerButton;
    @FXML
    private Label AlertLbl;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private TextField NomTfd;

    @FXML
    private TextField NumeroTfd;

    private BooleanProperty onEditMode = new SimpleBooleanProperty(false);

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    private Commande retrieveCommandeFromInput(){

         Commande commande = Commande.builder().client(NomTfd.getText()).num(NumeroTfd.getText()).adr(AddressTfd.getText()).build();

        return commande;
    }







//Continuer Button OnEditMode
    EventHandler<ActionEvent> ContinuerButtonOnActionOnEditMode(){

        return actionEvent -> {

            Commande commande = retrieveCommandeFromInput();
if(validateInput(commande)) {
    try {
        SideBarController.BlurBackground();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterCommande2DialogView));
        DialogPane AjouterCommande2Dialog = fxmlLoader.load();
        AjouterCommande2Dialog.getStylesheets().add(
                Main.class.getResource("images/dialog.css").toExternalForm());
AjouterCommande2Dialog.setStyle(
                "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                        .replaceAll("0x","#")
                        +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                        .replaceAll("0x","#")+";");
        AjouterCommande2DialogController ajouterCommande2DialogController = fxmlLoader.getController();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(AjouterCommande2Dialog);
        ajouterCommande2DialogController.setDialog1(dialog);
        ajouterCommande2DialogController.setDialog2(this.dialog);

        passedCommande.setClient(NomTfd.getText());
        passedCommande.setAdr(AddressTfd.getText());
        passedCommande.setNum(NumeroTfd.getText());

        ajouterCommande2DialogController.setPassedCommande(passedCommande);
        ajouterCommande2DialogController.setOnEditMode(true);

        ajouterCommande2DialogController.updateUpperLabels();
        dialog.initStyle(StageStyle.TRANSPARENT);
        AjouterCommande2Dialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
        dialog.showAndWait();
        SideBarController.DeleteBlurBackground();

    } catch (IOException e) {
        e.printStackTrace();
    }


}

        };

    }


    //Continuer Button
    @FXML
    void ContinuerButtonOnAction(ActionEvent event) {
        Commande commande = retrieveCommandeFromInput();

        if(validateInput(commande)) {
            try {


                SideBarController.BlurBackground();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterCommande2DialogView));
                DialogPane AjouterCommande2Dialog = fxmlLoader.load();
                AjouterCommande2Dialog.getStylesheets().add(
                        Main.class.getResource("images/dialog.css").toExternalForm());
AjouterCommande2Dialog.setStyle(
                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                .replaceAll("0x","#")
                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                .replaceAll("0x","#")+";");
                AjouterCommande2DialogController ajouterCommande2DialogController = fxmlLoader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(AjouterCommande2Dialog);
                ajouterCommande2DialogController.setDialog1(dialog);
                ajouterCommande2DialogController.setDialog2(this.dialog);

                //if not in edit mode create an empty commande to get passed to next dialog


                ajouterCommande2DialogController.setPassedCommande(commande);
                ajouterCommande2DialogController.setOnEditMode(false);

                ajouterCommande2DialogController.updateUpperLabels();
                dialog.initStyle(StageStyle.TRANSPARENT);
                AjouterCommande2Dialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                dialog.showAndWait();
                SideBarController.DeleteBlurBackground();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {

        dialog.setResult(ButtonType.CLOSE);
        dialog.close();

    }

    private void updateInput(){
        NomTfd.setText(getPassedCommande().getClient());
        AddressTfd.setText(getPassedCommande().getAdr());
        NumeroTfd.setText(getPassedCommande().getNum());
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

    public Commande getPassedCommande() {
        return passedCommande;
    }
    public void setPassedCommande(Commande passedCommande) {
        this.passedCommande = passedCommande;
    }


    private Boolean validateInput(Commande commande) {
        var nomValidation = isNomValid(commande.getClient(),true);
        if(!nomValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(nomValidation);
            return false;
        }

        var adrValidation = isAdrValid(commande.getAdr(),false);
        if(!adrValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(adrValidation);
            return false;
        }

        var numValidation = isNumValid(commande.getNum(),false);
        if(!numValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(numValidation);
            return false;
        }


        return true;

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     AlertLbl.setVisible(false);

        onEditMode.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    titleLabel.setText("Modifier une Commande");
                    ContinuerButton.setText("Modifier");
                    updateInput();
                    ContinuerButton.setOnAction(ContinuerButtonOnActionOnEditMode());
                }
            }
        });

    }
}
