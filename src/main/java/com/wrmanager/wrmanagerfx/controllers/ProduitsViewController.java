package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Categorie;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.models.SystemMeasure;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.persistence.RollbackException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class ProduitsViewController implements Initializable {

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
    private TableView<Produit> ProduitsTable;

    public TableView<Produit> getProduitsTable() {
        return ProduitsTable;
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

    GridPane root;




    TableColumn codeBarreColumn = new TableColumn("Code à Barre");
    TableColumn designationColumn = new TableColumn("Designation");
    TableColumn categorieColumn = new TableColumn("Catégorie");
    TableColumn formColumn = new TableColumn("Forme");
    TableColumn dosageColumn = new TableColumn("Dosage");
    TableColumn<Produit, Void> ActionsColumn = new TableColumn("Actions");





    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
        FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(Constants.AjouterProduitDialogView));
        DialogPane AjouterProduitDialog= fxmlLoader.load();
        AjouterProduitDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
        AjouterProduitDialog.setStyle(
                "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                        .replaceAll("0x","#")
                        +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                        .replaceAll("0x","#")+";");
        AjouterProduitDialogController ajouterProduitDialogController=fxmlLoader.getController();
        Dialog<ButtonType> dialog =new Dialog<>();
        dialog.setDialogPane(AjouterProduitDialog);
        ajouterProduitDialogController.setDialog(dialog);
        dialog.initStyle(StageStyle.TRANSPARENT);
        AjouterProduitDialog.getScene().setFill(Color.rgb(0,0,0,0));
        ajouterProduitDialogController.setDialog(dialog);
        dialog.showAndWait();
        SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void TelechargerButtonOnAction(ActionEvent event) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file =        fileChooser.showOpenDialog(Label1.getScene().getWindow());

     fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        try {
           // produitService.saveExcelSheet(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Predicate<Produit> createFilteringPredicate(String searchText){
        return produit -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsProduit(produit,searchText);
        };
    }

    private boolean searchFindsProduit(Produit produit, String searchText){
        return (produit.getDesignation().toLowerCase().contains(searchText.toLowerCase())) ||
                (produit.getCodeBarre().toLowerCase().contains(searchText.toLowerCase())) ||
                (produit.getCategorie().getNom().toLowerCase().contains(searchText.toLowerCase()));
    }






    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(ProduitsTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        codeBarreColumn.prefWidthProperty().bind(ProduitsTable.widthProperty().multiply(sizeCoulumn));
        codeBarreColumn.setSortable(true);
        designationColumn.prefWidthProperty().bind(ProduitsTable.widthProperty().multiply(sizeCoulumn));
        categorieColumn.prefWidthProperty().bind(ProduitsTable.widthProperty().multiply(sizeCoulumn));
        formColumn.prefWidthProperty().bind(ProduitsTable.widthProperty().multiply(sizeCoulumn));
        dosageColumn.prefWidthProperty().bind(ProduitsTable.widthProperty().multiply(sizeCoulumn));

        ActionsColumn.prefWidthProperty().bind(ProduitsTable.widthProperty().multiply((sizeCoulumn*1.5)));


    }

    private void setupValueFactories(){

        //define the cells factory
        codeBarreColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, String>("codeBarre"));
        designationColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, String>("designation"));

        formColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, SystemMeasure>("Forme"));
        dosageColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, Integer>("Dosage"));

        setupCategorieCellValueFactory();
        setupActionsCellValueFactory();


    }

    private void setupCategorieCellValueFactory(){

         categorieColumn.setCellValueFactory(
                 new Callback<TableColumn.CellDataFeatures<Produit,Categorie>, ObservableValue<String>>() {
                     @Override
                     public ObservableValue<String> call(TableColumn.CellDataFeatures<Produit, Categorie> produitCategorieCellDataFeatures) {
                         return new SimpleStringProperty(produitCategorieCellDataFeatures.getValue().getCategorie().getNom());
                     }

                     }
         );




    }

    private void setupActionsCellValueFactory() {
        Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> cellFactory = new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
            @Override
            public TableCell<Produit, Void> call(final TableColumn<Produit, Void> param) {
                final TableCell<Produit, Void> cell = new TableCell<Produit, Void>() {

                    private final MFXButton trushBt = new MFXButton("");
                    private final MFXButton editBt = new MFXButton("");
                   private final MFXButton printBt = new MFXButton("");
                   private final MFXButton favorisBt = new MFXButton("");
                    private final HBox hBox =new HBox();


                    {
                        trushBt.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView trush =new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        trush.setSize("22.0");
                        trush.setFill(Color.rgb(193,39,39));
                        trushBt.setGraphic(trush);
                        trushBt.setOnAction((ActionEvent event) -> {
                            Produit data = getTableView().getItems().get(getIndex());

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
                                OuiNonDialogController.TitleLabel.setText("Supprimer Produit");
                                OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de ce Produit ?");
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                dialog.showAndWait();
                                if (dialog.getResult()==ButtonType.YES)
                                {


                                   produitService.delete(data);
                                }
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });



                        editBt.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView pencil =new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                        pencil.setFill(Color.valueOf(prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")));
                        pencil.setSize("19.0");
                        editBt.setGraphic(pencil);
                        editBt.setOnAction(event -> {
                            Produit data = getTableView().getItems().get(getIndex());
                            //// show the modification dialog
                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterProduitDialogView));
                                DialogPane AjouterProduitDialog = fxmlLoader.load();
                                AjouterProduitDialog.getStylesheets().add(
                                        Main.class.getResource("images/dialog.css").toExternalForm());
                                AjouterProduitDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");
                                AjouterProduitDialogController ajouterProduitDialogController = fxmlLoader.getController();

                                ajouterProduitDialogController.setPassedProduit(data);
                                ajouterProduitDialogController.setOnEditMode(true);
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(AjouterProduitDialog);
                                ajouterProduitDialogController.setDialog(dialog);

                                dialog.initStyle(StageStyle.TRANSPARENT);
                                AjouterProduitDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                ajouterProduitDialogController.setDialog(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });

                          printBt.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView printer =new FontAwesomeIconView(FontAwesomeIcon.BARCODE);
                        printer.setFill(Color.rgb(0,0,0));
                        printer.setSize("19.0");
                        printBt.setGraphic(printer);
                        printBt.setOnAction(event->{
                            Produit data = getTableView().getItems().get(getIndex());


                            //OPEN printing dialog
                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.ImpressionCodeBarreDialogView));
                                DialogPane ImpressionDialog = fxmlLoader.load();
                                ImpressionDialog.getStylesheets().add(
                                        Main.class.getResource("images/dialog.css").toExternalForm());
                                ImpressionDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");
                                ImpressionCodeBarreDialogController impressionCodeBarreDialogController = fxmlLoader.getController();
                                javafx.scene.control.Dialog<javafx.scene.control.ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(ImpressionDialog);
                                //pass the current dialog to the dialog controller
                                impressionCodeBarreDialogController.setDialog(dialog);
                                impressionCodeBarreDialogController.setPassedProduit(data);
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                ImpressionDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                impressionCodeBarreDialogController.setDialog(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }








                        });


                          favorisBt.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView favoris =new FontAwesomeIconView(FontAwesomeIcon.HEART);
                        favoris.setFill(Color.rgb(0,0,0));
                        favoris.setSize("19.0");
                        favorisBt.setGraphic(favoris);
                        favorisBt.setOnAction(event->{

                            //OPEN favorite dialog
                            Produit data = getTableView().getItems().get(getIndex());

                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource("fxml/FavorieDialog.fxml"));
                                DialogPane FavorieDialog= fxmlLoader.load();
                                FavorieDialog.getStylesheets().add(Main.class.getResource("images/dialog.css").toExternalForm());
                                FavorieDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");

                                FavorieDialogController favorieDialogController  =fxmlLoader.getController();
                                //pass produit that will be added to a favorite item

                                favorieDialogController.setPassedProduit(data);



                                Dialog<ButtonType> dialog =new Dialog<>();
                                dialog.setDialogPane(FavorieDialog);
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                favorieDialogController.setDialog(dialog);

                            FavorieDialog.getScene().setFill(Color.rgb(0,0,0,0));
                                BoxBlur boxBlur=new BoxBlur();
                                boxBlur.setIterations(10);
                                boxBlur.setHeight(7);
                                boxBlur.setWidth(7);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();



                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });




                        trushBt.setAlignment(Pos.TOP_CENTER);
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0,0,0,0));
                        hBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
                        hBox.setPadding(new Insets(0,0, 0,0));
                        hBox.getChildren().addAll(trushBt,editBt,printBt,favorisBt);
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {



        ProduitsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //add columns to the table
        ProduitsTable.getColumns().addAll(codeBarreColumn,designationColumn,categorieColumn,formColumn,dosageColumn,ActionsColumn);



        //searchBarFilter(people, SearchBarTextField.textProperty(),ProduitsTable);


        FilteredList<Produit> filteredData = new FilteredList<>(produitsList);

        SearchBarTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createFilteringPredicate(newValue))
        );


        ProduitsTable.setItems(filteredData);
        setupValueFactories();
        divideTableWidthOnColumns();
        // added by rabah
        //

    }



}











