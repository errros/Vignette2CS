package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Commande;
import com.wrmanager.wrmanagerfx.entities.LigneCommande;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import static com.wrmanager.wrmanagerfx.Constants.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import static com.wrmanager.wrmanagerfx.Constants.commandeService;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class CommandesViewController implements Initializable {

    @FXML
    private MFXButton AjouterButton;

    @FXML
    private TableView<Commande> CommandesTable;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private Label Label3;

    @FXML
    private Label Label4;

    @FXML
    private TextField SearchBarTextField;

    @FXML
    private AnchorPane icon;

    @FXML
    private AnchorPane icon1;

    @FXML
    private AnchorPane icon2;

    @FXML
    private AnchorPane icon3;


    TableColumn clienColumn = new TableColumn("Nom de Client");
    TableColumn numColumn = new TableColumn("NumTel");
    TableColumn addressColumn = new TableColumn("Address");
    TableColumn nbrProduitsColumn = new TableColumn("Nbr de Produits");

    TableColumn payeColumn = new TableColumn("Payé");

    TableColumn<Commande, Void> ActionsColumn = new TableColumn("Actions");




    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(Constants.AjouterCommande1DialogView));
            DialogPane AjouterCommande1Dialog= fxmlLoader.load();
            AjouterCommande1Dialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            AjouterCommande1Dialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterCommande1DialogController ajouterCommande1DialogController=fxmlLoader.getController();
            Dialog<ButtonType> dialog =new Dialog<>();
            dialog.setDialogPane(AjouterCommande1Dialog);
            ajouterCommande1DialogController.setDialog(dialog);

            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterCommande1Dialog.getScene().setFill(Color.rgb(0,0,0,0));
            ajouterCommande1DialogController.setDialog(dialog);
            dialog.showAndWait();
            SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        CommandesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



        FilteredList<Commande> filteredData = new FilteredList<>(commandesList);
        SearchBarTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createFilteringPredicate(newValue))
        );


        CommandesTable.setItems(filteredData);
        //add columns to the table
        CommandesTable.getColumns().addAll(clienColumn,numColumn,addressColumn,nbrProduitsColumn,payeColumn,ActionsColumn);
        setupValueFactories();
        divideTableWidthOnColumns();

    }



    private boolean searchFindsCommande(Commande commande, String searchText){
        return (commande.getClient().toLowerCase().contains(searchText.toLowerCase())) ||
                (commande.getAdr().toLowerCase().contains(searchText.toLowerCase()));
    }

    private Predicate<Commande> createFilteringPredicate(String searchText){
        return commande -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsCommande(commande,searchText);
        };
    }


    static public void sortRowsByLastTimeAdded(){

        commandesList.sort(Comparator.comparing(Commande::getCreeLe).reversed());

    }



    static public  void  addCommandeToTable(Commande commande, List<LigneCommande> lcFromTable){

       commandeService.save(commande,lcFromTable);


    }
    static public  void  updateExistingCommande(Commande commande, List<LigneCommande> lcFromTable){

        commandeService.update(commande,lcFromTable);

    }

    static public void deleteCommande(Commande commande){
        commandeService.delete(commande);
    }

    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(CommandesTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        clienColumn.prefWidthProperty().bind(CommandesTable.widthProperty().multiply(sizeCoulumn));
        numColumn.prefWidthProperty().bind(CommandesTable.widthProperty().multiply(sizeCoulumn));
        addressColumn.prefWidthProperty().bind(CommandesTable.widthProperty().multiply(sizeCoulumn));
        nbrProduitsColumn.prefWidthProperty().bind(CommandesTable.widthProperty().multiply(sizeCoulumn));
        payeColumn.prefWidthProperty().bind(CommandesTable.widthProperty().multiply(sizeCoulumn));
        ActionsColumn.prefWidthProperty().bind(CommandesTable.widthProperty().multiply(sizeCoulumn));


    }

    private void setupValueFactories(){

        //define the cells factory
        clienColumn.setCellValueFactory(
                new PropertyValueFactory<Commande, String>("client"));
        numColumn.setCellValueFactory(
                new PropertyValueFactory<Commande, String>("num"));
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<Commande, String>("adr"));
       setupNbrProduitsCellValueFactory();
        setupPayeCellValueFactory();
        setupActionsCellValueFactory();


    }


    private void setupNbrProduitsCellValueFactory(){
        nbrProduitsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Commande, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Commande, Integer> commandeIntegerCellDataFeatures) {
                return new SimpleIntegerProperty(commandeIntegerCellDataFeatures.getValue().getLignesCommande().size());
            }


        });



    }

    private void setupPayeCellValueFactory() {

      payeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Commande , Boolean>, ObservableValue<Boolean>>() {
          @Override
          public ObservableValue call(TableColumn.CellDataFeatures<Commande,Boolean> cellDataFeatures) {
              return new SimpleBooleanProperty(cellDataFeatures.getValue().getPaye());
          }
      });


     payeColumn.setCellFactory(CheckBoxTableCell.forTableColumn(payeColumn));

    }

    private void setupActionsCellValueFactory() {
        Callback<TableColumn<Commande, Void>, TableCell<Commande, Void>> cellFactory = new Callback<TableColumn<Commande, Void>, TableCell<Commande, Void>>() {
            @Override
            public TableCell<Commande, Void> call(final TableColumn<Commande, Void> param) {
                final TableCell<Commande, Void> cell = new TableCell<Commande, Void>() {

                    private final MFXButton bt = new MFXButton("");
                    private final MFXButton bt1 = new MFXButton("");
                    private final HBox hBox =new HBox();


                    {
                        bt.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView trush =new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        trush.setSize("22.0");
                        trush.setFill(Color.rgb(193,39,39));
                        bt.setGraphic(trush);
                        bt.setOnAction((ActionEvent event) -> {
                            Commande data = getTableView().getItems().get(getIndex());

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
                                OuiNonDialogController.TitleLabel.setText("Supprimer Commande");
                                OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de cette Commande ?");
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                dialog.showAndWait();
                                if (dialog.getResult()==ButtonType.YES)
                                {

                                    deleteCommande(data);
                                }
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });



                        bt1.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView pencil =new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                        pencil.setFill(Color.valueOf(prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")));
                        pencil.setSize("22.0");
                        bt1.setGraphic(pencil);
                        bt1.setOnAction(event -> {
                            Commande data = getTableView().getItems().get(getIndex());
                            //// show the modification dialog
                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterCommande1DialogView));
                                DialogPane AjouterCommandeDialog = fxmlLoader.load();
                                AjouterCommandeDialog.getStylesheets().add(
                                        Main.class.getResource("images/dialog.css").toExternalForm());
                                AjouterCommandeDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");
                                AjouterCommande1DialogController ajouterCommandeDialogController = fxmlLoader.getController();
                                ajouterCommandeDialogController.setPassedCommande(data);
                                ajouterCommandeDialogController.setOnEditMode(true);
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(AjouterCommandeDialog);
                                ajouterCommandeDialogController.setDialog(dialog);
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                AjouterCommandeDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                ajouterCommandeDialogController.setDialog(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });

                        bt.setAlignment(Pos.TOP_CENTER);
                        hBox.setSpacing(17);
                        hBox.setPadding(new Insets(0,15,0,15));
                        hBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
                        hBox.setPadding(new Insets(0,0, 0,0));
                        hBox.getChildren().addAll(bt,bt1);
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
