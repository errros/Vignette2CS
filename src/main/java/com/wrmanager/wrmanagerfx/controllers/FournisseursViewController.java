package com.wrmanager.wrmanagerfx.controllers;
import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.persistence.RollbackException;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class FournisseursViewController implements Initializable {



    @FXML
    private MFXButton AjouterButton;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private Label Label3;

    @FXML
    private Label Label4;

    @FXML
     private TableView<Fournisseur> FournisseursTable;

    public TableView<Fournisseur> getFournisseursTable() {
        return FournisseursTable;
    }


    @FXML
    private TextField SearchBarTextField;

    @FXML
    private MFXButton TelechargerButton;

    @FXML
    private AnchorPane icon;

    @FXML
    private AnchorPane icon1;

    @FXML
    private AnchorPane icon2;

    @FXML
    private AnchorPane icon3;


    TableColumn NomColumn = new TableColumn("Nom");
    TableColumn PrenomColumn = new TableColumn("Prénom");
    TableColumn AddressColumn = new TableColumn("Address");
    TableColumn Numero1Column = new TableColumn("NumTél1");
    TableColumn Numero2Column = new TableColumn("NumTél2");
    TableColumn<Fournisseur, Void> ActionsColumn = new TableColumn("Actions");




    @FXML
    void AjouterButtonOnAction(ActionEvent event) {

        try {
            SideBarController.BlurBackground();
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
            dialog.showAndWait();
            SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private Predicate<Fournisseur> createFilteringPredicate(String searchText){
        return fournisseur -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsFournisseur(fournisseur,searchText);
        };
    }

    private boolean searchFindsFournisseur(Fournisseur fournisseur, String searchText){
        return (fournisseur.getNom().toLowerCase().contains(searchText.toLowerCase())) ||
                (fournisseur.getPrenom().toLowerCase().contains(searchText.toLowerCase())) ||
                (fournisseur.getAdr().toLowerCase().contains(searchText.toLowerCase()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        FournisseursTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);






        FilteredList<Fournisseur> filteredData = new FilteredList<>(fournisseursList);

        SearchBarTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createFilteringPredicate(newValue))
        );


        FournisseursTable.setItems(filteredData);
        //add columns to the table
        FournisseursTable.getColumns().addAll(NomColumn,PrenomColumn,AddressColumn,Numero1Column,Numero2Column,ActionsColumn);
        setupValueFactories();
        divideTableWidthOnColumns();

    }






    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(FournisseursTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        NomColumn.prefWidthProperty().bind(FournisseursTable.widthProperty().multiply(sizeCoulumn));
        PrenomColumn.prefWidthProperty().bind(FournisseursTable.widthProperty().multiply(sizeCoulumn));
        AddressColumn.prefWidthProperty().bind(FournisseursTable.widthProperty().multiply(sizeCoulumn));
        Numero1Column.prefWidthProperty().bind(FournisseursTable.widthProperty().multiply(sizeCoulumn));
        Numero2Column.prefWidthProperty().bind(FournisseursTable.widthProperty().multiply(sizeCoulumn));
        ActionsColumn.prefWidthProperty().bind(FournisseursTable.widthProperty().multiply(sizeCoulumn));


    }

    private void setupValueFactories(){

        //define the cells factory
        NomColumn.setCellValueFactory(
                new PropertyValueFactory<Fournisseur, String>("nom"));
        PrenomColumn.setCellValueFactory(
                new PropertyValueFactory<Fournisseur, String>("prenom"));
        AddressColumn.setCellValueFactory(
                new PropertyValueFactory<Fournisseur, String>("adr"));
        Numero1Column.setCellValueFactory(
                new PropertyValueFactory<Fournisseur, String>("num1"));
        Numero2Column.setCellValueFactory(
                new PropertyValueFactory<Fournisseur, String>("num2"));

        setupActionsCellValueFactory();


    }

    private void setupActionsCellValueFactory() {
        Callback<TableColumn<Fournisseur, Void>, TableCell<Fournisseur, Void>> cellFactory = new Callback<TableColumn<Fournisseur, Void>, TableCell<Fournisseur, Void>>() {
            @Override
            public TableCell<Fournisseur, Void> call(final TableColumn<Fournisseur, Void> param) {
                final TableCell<Fournisseur, Void> cell = new TableCell<Fournisseur, Void>() {

                    private final MFXButton deleteBtn = new MFXButton("");
                    private final MFXButton editBtn = new MFXButton("");
                    private final HBox hBox =new HBox();


                    {
                        deleteBtn.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView trush =new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        trush.setSize("22.0");
                        trush.setFill(Color.rgb(193,39,39));
                        deleteBtn.setGraphic(trush);
                        deleteBtn.setOnAction((ActionEvent event) -> {
                            Fournisseur data = getTableView().getItems().get(getIndex());

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
                                OuiNonDialogController.TitleLabel.setText("Supprimer Fournisseur");
                                OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de ce Fournisseur ?");
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                dialog.showAndWait();
                                if (dialog.getResult()==ButtonType.YES)
                                {
                                    fournisseurService.delete(data);

                                    }
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });



                        editBtn.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView pencil =new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                        pencil.setFill(Color.valueOf(prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")));
                        pencil.setSize("22.0");
                        editBtn.setGraphic(pencil);
                        editBtn.setOnAction(event -> {
                            Fournisseur data = getTableView().getItems().get(getIndex());
                            //// show the modification dialog
                            try {
                                SideBarController.BlurBackground();
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
                                  ajouterFournisseurDialogController.setOnEditMode(true);
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(AjouterFournisseurDialog);
                                ajouterFournisseurDialogController.setDialog(dialog);

                                ajouterFournisseurDialogController.setPassedFournisseur(data);
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                AjouterFournisseurDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                ajouterFournisseurDialogController.setDialog(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });

                        deleteBtn.setAlignment(Pos.TOP_CENTER);
                        hBox.setSpacing(17);
                        hBox.setPadding(new Insets(0,15,0,15));
                        hBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
                        hBox.setPadding(new Insets(0,0, 0,0));
                        hBox.getChildren().addAll(deleteBtn, editBtn);
                        hBox.setAlignment(Pos.CENTER);


                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hBox);
                        }
                    }

                };
                return cell;
            }
        };


        ActionsColumn.setCellFactory(cellFactory);

    }



}


