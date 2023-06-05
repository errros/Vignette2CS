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
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
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
    private Button AjouterCategoryButton;

    @FXML
    private MFXButton AjouterEtResterButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private ChoiceBox<String> CategoryChoiceBox;
    @FXML
    private ChoiceBox<SystemMeasure> SysMeasureChoiceBox;



    @FXML
    private Label Cod;

    @FXML
    private TextField CodeBarreTfd;


    @FXML
    private TextField DesignationTfd;

    @FXML
    private MFXCheckbox PeerisableCheckBox;

    @FXML
    private TextField QtyAlertTf;

    @FXML
    private TextField QtyUnitTfd;
    @FXML
    private Label AlertLbl;


    @FXML
    private TextField JoursAlerteTf;

    @FXML
    private Label uniteTF;

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox vbox;

    @FXML
    private Label joursAlertLabel;

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
        var sys = SysMeasureChoiceBox.getSelectionModel().getSelectedItem();
        var peerisable = PeerisableCheckBox.isSelected();
        var qtyUnit = QtyUnitTfd.getText();
        var qtyAlert = QtyAlertTf.getText();
        var jrsAlert = JoursAlerteTf.getText();


        if(validateInput(codeBarre,designation,qtyUnit,sys,peerisable,jrsAlert,qtyAlert)) {
    Produit produit = Produit.builder().
            codeBarre(codeBarre).
            designation(designation)
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
        var sys = SysMeasureChoiceBox.getSelectionModel().getSelectedItem();
        var peerisable = PeerisableCheckBox.isSelected();
        var qtyUnit = QtyUnitTfd.getText();
        var qtyAlert = QtyAlertTf.getText();
        var jrsAlert = JoursAlerteTf.getText();

        if(validateInput(codeBarre,designation,qtyUnit,sys,peerisable,jrsAlert,qtyAlert)) {


            passedProduit.setCodeBarre(codeBarre);
            passedProduit.setDesignation(designation);
          /*  passedProduit.setSystemMeasure(sys);
            passedProduit.setEstPerissable(peerisable);
            passedProduit.setQtyUnite(Float.valueOf(qtyUnit));
            passedProduit.setQtyUnite(Float.valueOf(qtyAlert));
            passedProduit.setJoursAlerte(Integer.valueOf(Math.round(Float.valueOf(jrsAlert))));
*/
            Categorie categorie = categoriesList.stream().filter(categorie1 ->
                    categorie1.getNom().equals(CategoryChoiceBox.getSelectionModel().getSelectedItem())).findAny().get();

            produitService.update(passedProduit,categorie);

            return true;
        }

        return false;

    }


    private Boolean validateInput(String codeBarre , String designation , String qtyUnit ,SystemMeasure sys ,
                                  Boolean perissable ,String jours , String qtyAlert){


        var codeBarreValidation = isCodeBarreValid(codeBarre,true);
        if(!codeBarreValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(codeBarreValidation);
            return false;
        }
        var designationValidation = isNomValid(designation,true);
        if(!designationValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText("Une Désignation doit etre 3 characters long au moins");
            return false;
        }


            var qtyValidation = sys == SystemMeasure.UNITE ? isQtyIntPositiveValid(qtyUnit, true,false) : isQtyFloatPositiveValid(qtyUnit,true,false);

        if(!qtyValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(qtyValidation);
            return false;
        }

        var qtyAlertValidation = sys == SystemMeasure.UNITE ? isQtyIntPositiveValid(qtyAlert,true,true) : isQtyFloatPositiveValid(qtyAlert,true,true);

        if(!qtyAlertValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(qtyAlertValidation);
            return false;
        }

        if(perissable){

            var jrsValidation = isQtyIntPositiveValid(jours,true,true);
    if(!jrsValidation.equals("true")){
        AlertLbl.setVisible(true);
        AlertLbl.setText("le nombre des jours doit etre superieur a 0");
        return false;
    }


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


    }


    private void choiceBoxChangeListeners(){

        CategoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(t1.intValue()!= -1) {
                        QtyAlertTf.setText(categoriesList.get(t1.intValue()).getQtyAlerte().toString());
                        JoursAlerteTf.setText(categoriesList.get(t1.intValue()).getJoursAlerte().toString());
                }

            }
        });

        SysMeasureChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SystemMeasure>() {
            @Override
            public void changed(ObservableValue<? extends SystemMeasure> observableValue, SystemMeasure systemMeasure, SystemMeasure t1) {
                if (t1 == SystemMeasure.UNITE) {
                    uniteTF.setText("Unité");
                    QtyUnitTfd.setText("1");
                } else {
                    uniteTF.setText("Kg");
                    QtyUnitTfd.setText("1.0");
                }
            }
        });

    }


    private void peerisableChangeListner() {

        PeerisableCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    JoursAlerteTf.setVisible(t1);
                    joursAlertLabel.setVisible(t1);
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AlertLbl.setVisible(false);

        choiceBoxChangeListeners();

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

        SysMeasureChoiceBox.getItems().addAll(SystemMeasure.UNITE,SystemMeasure.POIDS);
        SysMeasureChoiceBox.getSelectionModel().select(0);
        peerisableChangeListner();
        PeerisableCheckBox.setSelected(true);


    }

}


