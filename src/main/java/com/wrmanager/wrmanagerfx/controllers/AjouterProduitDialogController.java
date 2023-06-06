package com.wrmanager.wrmanagerfx.controllers;
import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Categorie;
import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.models.SystemMeasure;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;
import static com.wrmanager.wrmanagerfx.validation.Validators.*;

public class AjouterProduitDialogController implements Initializable {
    private Dialog<ButtonType> dialog;
    private Produit passedProduit;


    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public Produit getPassedProduit() {
        return passedProduit;
    }

    public void setPassedProduit(Produit passedProduit) {
        this.passedProduit = passedProduit;
    }

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private Button CameraButton;

    @FXML
    private Button AjouterCategoryButton;

    @FXML
    private MFXButton AjouterEtResterButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private ChoiceBox<String> CategoryChoiceBox;



    @FXML
    private Label Cod;

    @FXML
    private TextField CodeBarreTfd;


    @FXML
    private TextField DesignationTfd;

    @FXML
    private MFXCheckbox PeerisableCheckBox;

    @FXML
    private TextField FormTfd;

    @FXML
    private TextField DosageTfd;
    @FXML
    private Label AlertLbl;


    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox vbox;


    private BooleanProperty onEditMode = new SimpleBooleanProperty(false);




    public void setCodeBarreTfd(String codeBarreTfd) {
        CodeBarreTfd.setText(codeBarreTfd);
    }

    @FXML
    void AjouterCategoryButtonOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(Constants.AjouterCategoryDialogView));
            DialogPane AjouterCategoryDialog= fxmlLoader.load();
            AjouterCategoryDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            AjouterCategoryDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterCategoryDialogController ajouterCategoryDialogController  =fxmlLoader.getController();
            Dialog<ButtonType> dialog =new Dialog<>();
            dialog.setDialogPane(AjouterCategoryDialog);
            ajouterCategoryDialogController.setDialog(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterCategoryDialog.getScene().setFill(Color.rgb(0,0,0,0));
            BoxBlur boxBlur=new BoxBlur();
            boxBlur.setIterations(10);
            boxBlur.setHeight(7);
            boxBlur.setWidth(7);
            this.dialog.getDialogPane().setEffect(boxBlur);
           var result =  dialog.showAndWait();
           if(result.isPresent() && result.get().equals(ButtonType.YES)){
               CategoryChoiceBox.getSelectionModel().select(categoriesList.get(0).getNom());
           }
            this.dialog.getDialogPane().setEffect(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean retrieveAndAdd(){

        var codeBarre = CodeBarreTfd.getText().isEmpty()?produitService.generateEAN13() : CodeBarreTfd.getText();
        var designation = DesignationTfd.getText();
        var form = FormTfd.getText();
        var dosage = DosageTfd.getText();


        if(validateInput(codeBarre,designation,form,dosage)) {
    Produit produit = Produit.builder().
            codeBarre(codeBarre).
            designation(designation)
            .forme(form)
            .dosage(dosage)
            .build();


    Categorie categorie = categoriesList.stream().filter(categorie1 ->
            categorie1.getNom().equals(CategoryChoiceBox.getSelectionModel().getSelectedItem())).findAny().get();

    produitService.save(produit,categorie);

    return true;

}

        return false;

    }

    private Boolean update() {

        var codeBarre = CodeBarreTfd.getText().isEmpty()?produitService.generateEAN13() : CodeBarreTfd.getText();
        var designation = DesignationTfd.getText();


        var form = FormTfd.getText();
        var dosage = DosageTfd.getText();
        if(validateInput(codeBarre,designation,form,dosage)) {


            passedProduit.setCodeBarre(codeBarre);
            passedProduit.setDesignation(designation);
            passedProduit.setForme(form);
            passedProduit.setDosage(dosage);

            Categorie categorie = categoriesList.stream().filter(categorie1 ->
                    categorie1.getNom().equals(CategoryChoiceBox.getSelectionModel().getSelectedItem())).findAny().get();

            produitService.update(passedProduit,categorie);

            return true;
        }

        return false;

    }


    private Boolean validateInput(String codeBarre , String designation , String form, String Dosage){


        var codeBarreValidation = isCodeBarreValid(codeBarre,true);
        if(!codeBarreValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(codeBarreValidation);
            return false;
        }

        return true;

    }

    @FXML
    void AjouterButtonOnAction(ActionEvent event) {

        if(retrieveAndAdd()) {


            dialog.setResult(ButtonType.CLOSE);
            dialog.close();

        }
    }

    EventHandler<ActionEvent> ModifierButtonOnActionOnEditMode(){
        return actionEvent -> {

           if(update())
            {

                dialog.setResult(ButtonType.CLOSE);
                dialog.close();

            }
        };


    }





    @FXML
    void AjouterEtResterButtonOnAction(ActionEvent event) {



         if (retrieveAndAdd()) {

            AlertLbl.setVisible(false);
             CodeBarreTfd.setText("");
             DesignationTfd.setText("");

         }
    }
    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }
    @FXML
    void CheckBoxOnAction(ActionEvent event) {
    }



    @FXML
    void CameraButtonOnAction(ActionEvent event) throws IOException {



        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource("fxml/CaptureCameraDialog.fxml"));
            DialogPane CaptureCameraDialog= fxmlLoader.load();
            CaptureCameraDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            CaptureCameraDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            CaptureCameraController captureCameraController  =fxmlLoader.getController();
            Dialog<ButtonType> dialog =new Dialog<>();
            dialog.setDialogPane(CaptureCameraDialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            captureCameraController.setDialog(dialog);
            CaptureCameraDialog.getScene().setFill(Color.rgb(0,0,0,0));
            BoxBlur boxBlur=new BoxBlur();
            boxBlur.setIterations(10);
            boxBlur.setHeight(7);
            boxBlur.setWidth(7);
            this.dialog.getDialogPane().setEffect(boxBlur);
            var result =  dialog.showAndWait();
            if(result.isPresent() && result.get().equals(ButtonType.YES)){
                CategoryChoiceBox.getSelectionModel().select(categoriesList.get(0).getNom());
            }
            this.dialog.getDialogPane().setEffect(null);

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void updateInput(){
        DesignationTfd.setText(passedProduit.getDesignation());
        CodeBarreTfd.setText(passedProduit.getCodeBarre());
        CodeBarreTfd.setText(passedProduit.getCodeBarre());
        CategoryChoiceBox.getSelectionModel().select(passedProduit.getCategorie().getNom());
        FormTfd.setText(passedProduit.getForme());
        DosageTfd.setText(passedProduit.getDosage());


    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AlertLbl.setVisible(false);


        CategoryChoiceBox.getItems().addAll(categoriesList.stream().map(Categorie::getNom).collect(Collectors.toSet()));
        CategoryChoiceBox.getSelectionModel().selectFirst();


        categoriesList.addListener(new ListChangeListener<Categorie>() {
            @Override
            public void onChanged(Change<? extends Categorie> change) {
                CategoryChoiceBox.getItems().clear();
                CategoryChoiceBox.getItems().addAll(categoriesList.stream().map(Categorie::getNom).collect(Collectors.toSet()));
                CategoryChoiceBox.getSelectionModel().selectFirst();

            }
        });



        onEditMode.addListener(new ChangeListener<Boolean>() {
              @Override
              public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                  if(t1){
                      titleLabel.setText("Modifier Produit");
                      AjouterEtResterButton.setVisible(false);
                      AjouterButton.setText("Modifier");
                      AjouterButton.setOnAction(ModifierButtonOnActionOnEditMode());
                      updateInput();
                  }
              }
          });


    }

}


