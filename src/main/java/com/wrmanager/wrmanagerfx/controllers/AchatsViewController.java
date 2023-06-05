package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.LigneAchat;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class AchatsViewController implements Initializable {

    @FXML
    private TableView<Achat> AchatsTable;

    public TableView<Achat> getAchatsTable() {
        return AchatsTable;
    }

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
    private TextField SearchBarTextField;

    @FXML
    private AnchorPane icon;

    @FXML
    private AnchorPane icon1;

    @FXML
    private AnchorPane icon2;

    @FXML
    private AnchorPane icon3;



    TableColumn numBonColumn = new TableColumn("Numéro de Bon");
    TableColumn dateAchatColumn = new TableColumn("Date d'Achat");
    TableColumn nomFournisseurColumn = new TableColumn("Fournisseur");
    TableColumn nbrProduitsColumn = new TableColumn("Nbr de Produits");
    TableColumn totaleColumn = new TableColumn("Totale");
    TableColumn payeColumn = new TableColumn("Payé");
    TableColumn ActionsColumn = new TableColumn("Actions");



    private Predicate<Achat> createFilteringPredicate(String searchText){
        return achat -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsAchat(achat,searchText);
        };
    }

    private boolean searchFindsAchat(Achat achat, String searchText){
        return (achat.getId().toString().equals(searchText)) ||
                (achat.getFournisseur().getNom().toLowerCase().contains(searchText.toLowerCase())) ||
                (achat.getFournisseur().getPrenom().toLowerCase().contains(searchText.toLowerCase()));
    }




    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(Constants.AjouterAchat1DialogView));
            DialogPane AjouterAchat1Dialog= fxmlLoader.load();
            AjouterAchat1Dialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            AjouterAchat1Dialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterAchat1DialogController ajouterAchat1DialogController=fxmlLoader.getController();
            Dialog<ButtonType> dialog =new Dialog<>();
            dialog.setDialogPane(AjouterAchat1Dialog);
            ajouterAchat1DialogController.setDialog(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterAchat1Dialog.getScene().setFill(Color.rgb(0,0,0,0));
            ajouterAchat1DialogController.setDialog(dialog);
            dialog.showAndWait();
            SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(AchatsTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        numBonColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));
        nomFournisseurColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));
        dateAchatColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));
        nbrProduitsColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));
        totaleColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));
        payeColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));
        ActionsColumn.prefWidthProperty().bind(AchatsTable.widthProperty().multiply(sizeCoulumn));


    }


    private void setupValueFactories(){

        //define the cells factory
        numBonColumn.setCellValueFactory(
                new PropertyValueFactory<Achat, Long>("id"));

        setupTotaleCellValueFactory();
        setupDateAchatCellValueFactory();
        setupPayeCellValueFactory();
        setupNomFournisseurCellValueFactory();
        setupNbrProduitsCellValueFactory();
        
        //nomFournisseur
        //nbrProduits


        setupActionsCellValueFactory();


    }

    private void setupTotaleCellValueFactory() {
     totaleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Achat , Integer>, ObservableValue<Integer>>() {
         @Override
         public ObservableValue call(TableColumn.CellDataFeatures<Achat, Integer> achatIntegerCellDataFeatures) {
             return new SimpleIntegerProperty(achatIntegerCellDataFeatures.getValue().getLigneAchats().stream().map(LigneAchat::getPrixVenteTotale).reduce(0,Integer::sum));
         }

     });
    }

    private void setupDateAchatCellValueFactory() {
    dateAchatColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Achat , Date>, ObservableValue<Date>>() {
        @Override
        public ObservableValue<Date> call(TableColumn.CellDataFeatures<Achat, Date> achatDateCellDataFeatures) {
            return new SimpleObjectProperty<Date>(new Date(achatDateCellDataFeatures.getValue().getCreeLe().getTime()));
        }
    });

    }

    private void setupPayeCellValueFactory() {
          payeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Achat , Boolean>, ObservableValue<Boolean>>() {
              @Override
              public ObservableValue call(TableColumn.CellDataFeatures<Achat,Boolean> cellDataFeatures) {
                  return new SimpleBooleanProperty(cellDataFeatures.getValue().getPaye());
              }
          });

          payeColumn.setCellFactory(CheckBoxTableCell.forTableColumn(payeColumn));

    }

    private void setupNbrProduitsCellValueFactory() {
        nbrProduitsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Achat,Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Achat,Integer> cellDataFeatures) {
                return new SimpleIntegerProperty(cellDataFeatures.getValue().getLigneAchats().size());
            }
        });


    }

    private void setupNomFournisseurCellValueFactory() {
           nomFournisseurColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Achat,String>, ObservableValue<String>>() {
               @Override
               public ObservableValue call(TableColumn.CellDataFeatures<Achat,String> cellDataFeatures) {
                   return new SimpleStringProperty(cellDataFeatures.getValue().getFournisseur().getNom());
               }
           });


    }

    private void setupActionsCellValueFactory() {
        Callback<TableColumn<Achat, Void>, TableCell<Achat, Void>> cellFactory = new Callback<TableColumn<Achat, Void>, TableCell<Achat, Void>>() {
            @Override
            public TableCell<Achat, Void> call(final TableColumn<Achat, Void> param) {
                final TableCell<Achat, Void> cell = new TableCell<Achat, Void>() {

                    private final MFXButton trushBtn = new MFXButton("");
                    private final MFXButton editBtn = new MFXButton("");
                    private final HBox hBox =new HBox();


                    {
                        trushBtn.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView trush =new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        trush.setSize("22.0");
                        trush.setFill(Color.rgb(193,39,39));
                        this.trushBtn.setGraphic(trush);
                        this.trushBtn.setOnAction((ActionEvent event) -> {
                            Achat data = getTableView().getItems().get(getIndex());

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
                                OuiNonDialogController.TitleLabel.setText("Supprimer Achat");
                                OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de ce Achat ?");
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                dialog.showAndWait();
                                if (dialog.getResult()==ButtonType.YES)
                                {

                                    deleteAchat(data);
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
                            Achat data = getTableView().getItems().get(getIndex());
                            //// show the modification dialog
                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterAchat1DialogView));
                                DialogPane AjouterAchatDialog = fxmlLoader.load();
                                AjouterAchatDialog.getStylesheets().add(
                                        Main.class.getResource("images/dialog.css").toExternalForm());
                                AjouterAchatDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");
                                AjouterAchat1DialogController ajouterAchatDialogController = fxmlLoader.getController();
                                ajouterAchatDialogController.setPassedAchat(data);
                                ajouterAchatDialogController.setOnEditMode(true);

                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(AjouterAchatDialog);
                                ajouterAchatDialogController.setDialog(dialog);
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                AjouterAchatDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                ajouterAchatDialogController.setDialog(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });

                        this.trushBtn.setAlignment(Pos.TOP_CENTER);
                        hBox.setSpacing(17);
                        hBox.setPadding(new Insets(0,15,0,15));
                        hBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
                        hBox.setPadding(new Insets(0,0, 0,0));
                        hBox.getChildren().addAll(this.trushBtn, editBtn);
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






    static public void deleteAchat(Achat data) {

          achatService.delete(data);
    }


    static public void addAchat(Achat data , List<LigneAchat> ligneAchatList) {

        var result = achatService.update(data, ligneAchatList);

       }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            AchatsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        FilteredList<Achat> filteredData = new FilteredList<>(achatsList);

        SearchBarTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createFilteringPredicate(newValue))
        );


        AchatsTable.setItems(filteredData);
        //add columns to the table
        AchatsTable.getColumns().addAll(numBonColumn,dateAchatColumn,nomFournisseurColumn,nbrProduitsColumn,totaleColumn,payeColumn,ActionsColumn);
        setupValueFactories();
        divideTableWidthOnColumns();




    }
}

