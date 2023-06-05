package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class AjouterAchat1DialogController implements Initializable {
    Dialog<ButtonType> dialog;


    @FXML
    private Label titleLabel;

    @FXML
    private Label AlertLbl;


    @FXML
    private MFXButton AjouterButton;

    @FXML
    private Button AjouterFournisseurButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private ChoiceBox<String> FournisseurChoiceBox;

    private BooleanProperty onEditMode = new SimpleBooleanProperty(false);

    private Achat passedAchat;

    static private ObservableList<String> fournisseursDisplay = FXCollections.observableArrayList();





    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
        try {

            if (Optional.ofNullable(FournisseurChoiceBox.getValue()).isPresent()) {
                SideBarController.BlurBackground();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterAchat2DialogView));
                DialogPane AjouterAchat2Dialog = fxmlLoader.load();
                AjouterAchat2Dialog.getStylesheets().add(
                        Main.class.getResource("images/dialog.css").toExternalForm());





                AjouterAchat2Dialog.setStyle(
                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                .replaceAll("0x","#")
                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                .replaceAll("0x","#")+";");


                AjouterAchat2DialogController ajouterAchat2DialogController = fxmlLoader.getController();
            SideBarController.BlurBackground();

            var dialogFournissuer = fournisseursList.stream().filter(fournisseur -> fournisseur.getNom().
                    equals(FournisseurChoiceBox.getValue())).findAny().get();


                //instantiate an achat object and persist it , pass it to the next dialog too
                    Achat achat = Achat.builder().
                            fournisseur(dialogFournissuer).build();
                    achat = achatService.save(achat);

                    ajouterAchat2DialogController.setPassedAchat(achat);



                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(AjouterAchat2Dialog);
                ajouterAchat2DialogController.setDialog1(dialog);
                ajouterAchat2DialogController.setDialog2(this.dialog);
                dialog.initStyle(StageStyle.TRANSPARENT);
                AjouterAchat2Dialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                dialog.showAndWait();

                SideBarController.DeleteBlurBackground();
            }else {
                AlertLbl.setText("Ajouter un fournisseur pour cette achat");
                AlertLbl.setVisible(true);
            }
            } catch(IOException e){
                e.printStackTrace();
            }


    }

    EventHandler<ActionEvent> ModifierOnActionOnEditMode(){
        return actionEvent -> {

            try {


                    SideBarController.BlurBackground();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterAchat2DialogView));
                    DialogPane AjouterAchat2Dialog = fxmlLoader.load();
                    AjouterAchat2Dialog.getStylesheets().add(
                            Main.class.getResource("images/dialog.css").toExternalForm());

                    AjouterAchat2Dialog.setStyle(
                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                .replaceAll("0x","#")
                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                .replaceAll("0x","#")+";");
                    AjouterAchat2DialogController ajouterAchat2DialogController = fxmlLoader.getController();

                    var dialogFournissuer = fournisseursList.stream().filter(fournisseur -> fournisseur.getNom().
                            equals(FournisseurChoiceBox.getValue())).findAny().get();

                    //instantiate an achat object and persist it , pass it to the next dialog too
                        passedAchat.setFournisseur(dialogFournissuer);
                        ajouterAchat2DialogController.setPassedAchat(passedAchat);
                    ajouterAchat2DialogController.setOnEditMode(onEditMode.get());

                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setDialogPane(AjouterAchat2Dialog);
                    ajouterAchat2DialogController.setDialog1(dialog);
                    ajouterAchat2DialogController.setDialog2(this.dialog);
                    dialog.initStyle(StageStyle.TRANSPARENT);
                    AjouterAchat2Dialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                    dialog.showAndWait();

                    SideBarController.DeleteBlurBackground();
                } catch(IOException e){
                e.printStackTrace();
            }



        };


    }


    @FXML
    void AjouterFournisseurButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterFournisseurDialogView));
            DialogPane AjouterFournisseurDialog = fxmlLoader.load();
            AjouterFournisseurDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());

            AjouterFournisseurDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterFournisseurDialogController ajouterFournisseurDialogController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(AjouterFournisseurDialog);
            //pass the current dialog to the dialog controller
            ajouterFournisseurDialogController.setDialog(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterFournisseurDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
            ajouterFournisseurDialogController.setDialog(dialog);
            Optional<ButtonType> buttonType = dialog.showAndWait();
            if(buttonType.isPresent() && buttonType.get() == ButtonType.CLOSE){
                refreshFournisseurs();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();

    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
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

    public Achat getPassedAchat() {
        return passedAchat;
    }

    public void setPassedAchat(Achat passedAchat) {
        this.passedAchat = passedAchat;
    }

    private void refreshFournisseurs(){

        fournisseursDisplay.clear();
        fournisseursDisplay.addAll(fournisseursList.stream().map(Fournisseur::getNom).collect(Collectors.toList()));


        if(!fournisseursDisplay.isEmpty()){
            FournisseurChoiceBox.getSelectionModel().selectLast();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        AlertLbl.setVisible(false);

         onEditMode.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {

                if(t1){
                    titleLabel.setText("Modifier Achat");
                    FournisseurChoiceBox.getSelectionModel().select(passedAchat.getFournisseur().getNom());
                    AjouterButton.setOnAction(ModifierOnActionOnEditMode());

                }else {
                    titleLabel.setText("Ajouter Achat");
                }

            }
        });

        FournisseurChoiceBox.setItems(fournisseursDisplay);
        refreshFournisseurs();





    }


}

