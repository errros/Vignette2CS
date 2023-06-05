package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Commande;
import com.wrmanager.wrmanagerfx.entities.LigneCommande;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;
import static com.wrmanager.wrmanagerfx.validation.Validators.*;

public class AjouterCommande2DialogController implements Initializable{


    private Commande passedCommande;

    private     Dialog<ButtonType> dialog1;

    private Dialog<ButtonType> dialog2;



    public Dialog<ButtonType> getDialog1() {
        return dialog1;
    }

    public void setDialog1(Dialog<ButtonType> dialog1) {
        this.dialog1 = dialog1;
    }

    public Dialog<ButtonType> getDialog2() {
        return dialog2;
    }

    public void setDialog2(Dialog<ButtonType> dialog2) {
        this.dialog2 = dialog2;
    }

    public Commande getPassedCommande() {
        return passedCommande;
    }

    public void setPassedCommande(Commande passedCommande) {
        this.passedCommande = passedCommande;
    }

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private Label Label3;

    @FXML
    private Label Label4;
    @FXML
    private Label AlertLbl;

    @FXML
    private TableView<LigneCommande> LignesCommandeTable;

    @FXML
    private MFXCheckbox PayeCheckBox;

    @FXML
    private MFXButton ValiderButton;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField produitTf;

    @FXML
    private TextField qtyTf;

    @FXML
    private TextField remarqueTf;


    private BooleanProperty onEditMode = new SimpleBooleanProperty(false);


    static ObservableList<LigneCommande> people = FXCollections.observableArrayList();


    TableColumn produitColumn = new TableColumn("Nom de Produit");
    TableColumn qtyColumn = new TableColumn("Qty");
    TableColumn remarqueColumn = new TableColumn("Remarque");


    TableColumn<LigneCommande, Void> actionsColumn = new TableColumn("Actions");



private Boolean validateInput(String produit , String qty , String remarque){
    var nomValidation = isNomValid(produit,true);
    if(!nomValidation.equals("true")){
        AlertLbl.setVisible(true);
        AlertLbl.setText(nomValidation);
        return false;
    }

    var remarqueValidation = isRemarqueValid(remarque,false);
    if(!remarqueValidation.equals("true")){
        AlertLbl.setVisible(true);
        AlertLbl.setText(remarqueValidation);
        return false;
    }

    var qtyValidation = isQtyIntPositiveValid(qty,true,false);
    if(!qtyValidation.equals("true")){
        AlertLbl.setVisible(true);
        AlertLbl.setText(qtyValidation);
        return false;
    }
return true;



}

    @FXML
    void AjouterButtonOnAction(ActionEvent event) {

      var produit = produitTf.getText();
      var qty = qtyTf.getText();
      var remarque = remarqueTf.getText();


   if(validateInput(produit,qty,remarque)) {
       LigneCommande lc = LigneCommande.builder().produit(produit).
               qty(Integer.valueOf(Math.round(Float.parseFloat(qty)))).remarque(remarque).build();

       people.add(0, lc);



       produitTf.setText("");
       qtyTf.setText("1");

       AlertLbl.setVisible(false);
   }

    }

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.OuiNonDialogView));
            DialogPane OuiNonDialog = fxmlLoader.load();
            OuiNonDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            OuiNonDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            OuiNonDialogController OuiNonDialogController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(OuiNonDialog);
            OuiNonDialogController.setDialog(dialog);
            OuiNonDialogController.TitleLabel.setText("Annulation de Commande");
            OuiNonDialogController.SubjectLabel.setText("Vous etes sur de l'annulation de cette Commande ?");
            dialog.initStyle(StageStyle.TRANSPARENT);
            OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
            BoxBlur boxBlur=new BoxBlur();
            boxBlur.setIterations(10);
            boxBlur.setHeight(7);
            boxBlur.setWidth(7);
            dialog1.getDialogPane().setEffect(boxBlur);
            dialog.showAndWait();
            dialog1.getDialogPane().setEffect(null);
            if (dialog.getResult()==ButtonType.YES)
            {
                dialog1.setResult(ButtonType.CLOSE);
                dialog1.close();
                dialog2.setResult(ButtonType.CLOSE);
                dialog2.close();
                SideBarController.DeleteBlurBackground();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ValiderButtonOnAction(ActionEvent event) {

        //retrieve lignes de commande from table to get sent to the service
         List<LigneCommande> lcFromTable = people.stream().collect(Collectors.toList());


        passedCommande.setPaye(PayeCheckBox.isSelected());

            CommandesViewController.addCommandeToTable(passedCommande,lcFromTable);

            dialog1.setResult(ButtonType.CLOSE);
            dialog1.close();
            dialog2.setResult(ButtonType.CLOSE);
            dialog2.close();
            SideBarController.DeleteBlurBackground();




    }

    EventHandler<ActionEvent> ValiderButtonOnActionOnEditMode(){
        return actionEvent -> {

            List<LigneCommande> lcFromTable = people.stream().collect(Collectors.toList());


            passedCommande.setPaye(PayeCheckBox.isSelected());


                CommandesViewController.updateExistingCommande(passedCommande,lcFromTable);


            dialog1.setResult(ButtonType.CLOSE);
            dialog1.close();
            dialog2.setResult(ButtonType.CLOSE);
            dialog2.close();
            SideBarController.DeleteBlurBackground();






        };


    }

    public void updateUpperLabels(){

        Label1.setText(Label1.getText().concat(String.valueOf(Constants.commandesList.size()+1)));

        Label2.setText(Label2.getText().concat(passedCommande.getClient()));

        Label3.setText(Label3.getText().concat(passedCommande.getNum()));

        Label4.setText(Label4.getText().concat(passedCommande.getAdr()));


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

    private void setupValueFactories(){

        //define the cells factory
        produitColumn.setCellValueFactory(
                new PropertyValueFactory<LigneCommande, String>("produit"));
        qtyColumn.setCellValueFactory(
                new PropertyValueFactory<LigneCommande, Integer>("qty"));
        remarqueColumn.setCellValueFactory(
                new PropertyValueFactory<LigneCommande, String>("remarque"));
        setupActionsCellValueFactory();


    }


    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(LignesCommandeTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        produitColumn.prefWidthProperty().bind(LignesCommandeTable.widthProperty().multiply(sizeCoulumn));
        qtyColumn.prefWidthProperty().bind(LignesCommandeTable.widthProperty().multiply(sizeCoulumn));
        remarqueColumn.prefWidthProperty().bind(LignesCommandeTable.widthProperty().multiply(sizeCoulumn));
        actionsColumn.prefWidthProperty().bind(LignesCommandeTable.widthProperty().multiply(sizeCoulumn));


    }



    private void setupActionsCellValueFactory() {

        Callback<TableColumn<LigneCommande, Void>, TableCell<LigneCommande, Void>> cellFactory =
                new Callback<TableColumn<LigneCommande, Void>, TableCell<LigneCommande, Void>>() {


                    @Override
                    public TableCell<LigneCommande, Void> call(TableColumn<LigneCommande, Void> ligneCommandeVoidTableColumn) {


                        final TableCell<LigneCommande, Void> cell = new TableCell<LigneCommande, Void>() {

                            private final MFXButton bt = new MFXButton("");


                            {
                                bt.setStyle("-fx-background-color:rgba(0,0,0,0);");
                                FontAwesomeIconView trush = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                trush.setSize("22.0");
                                trush.setFill(Color.rgb(193, 39, 39));
                                bt.setGraphic(trush);
                                bt.setOnAction((ActionEvent event) -> {
                                    LigneCommande data = getTableView().getItems().get(getIndex());

                                    //Show the dialog pane
                                    try {
                                        SideBarController.BlurBackground();
                                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.OuiNonDialogView));
                                        DialogPane OuiNonDialog = fxmlLoader.load();
                                        OuiNonDialog.getStylesheets().add(
                                                Main.class.getResource("images/dialog.css").toExternalForm());
                                        OuiNonDialog.setStyle(
                                                "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                        .replaceAll("0x","#")
                                                        +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                        .replaceAll("0x","#")+";");
                                        OuiNonDialogController OuiNonDialogController = fxmlLoader.getController();
                                        Dialog<ButtonType> dialog = new Dialog<>();
                                        dialog.setDialogPane(OuiNonDialog);
                                        OuiNonDialogController.setDialog(dialog);
                                        OuiNonDialogController.TitleLabel.setText("Supprimer Ligne de Commande");
                                        OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de cette Ligne de Commande ?");
                                        dialog.initStyle(StageStyle.TRANSPARENT);
                                        OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                        dialog.showAndWait();
                                        if (dialog.getResult() == ButtonType.YES) {

                                            people.remove(getIndex());
                                            LignesCommandeTable.refresh();

                                        }
                                        SideBarController.DeleteBlurBackground();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                });




                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(bt);
                                }
                            }
                        };

                          return cell;
                    }
                };

        actionsColumn.setCellFactory(cellFactory);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AlertLbl.setVisible(false);

        onEditMode.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    titleLabel.setText("Modifier une Commande");
                    ValiderButton.setText("Modifier");
                    PayeCheckBox.setSelected(passedCommande.getPaye());
                    people.clear();
                    people.addAll(passedCommande.getLignesCommande());
                    ValiderButton.setOnAction(ValiderButtonOnActionOnEditMode());

                }
            }
        });
        LignesCommandeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        LignesCommandeTable.setItems(people);
        LignesCommandeTable.getColumns().addAll(produitColumn,qtyColumn,remarqueColumn,actionsColumn);
        setupValueFactories();
        divideTableWidthOnColumns();



    }
}
