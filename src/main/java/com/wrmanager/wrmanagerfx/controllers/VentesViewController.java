package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.Vente;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static com.wrmanager.wrmanagerfx.Constants.achatService;
import static com.wrmanager.wrmanagerfx.Constants.venteService;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class VentesViewController implements Initializable {

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
    private TableView<Vente> VentesTable;

    @FXML
    private AnchorPane icon;

    @FXML
    private AnchorPane icon1;

    @FXML
    private AnchorPane icon2;

    @FXML
    private AnchorPane icon3;


    TableColumn numBonColumn = new TableColumn("Numéro de Bon");
    TableColumn dateVenteColumn = new TableColumn("Date de Vente");
    TableColumn nomClientColumn = new TableColumn("Nom de Client");
    TableColumn nbrProduitsColumn = new TableColumn("Nbr de Produits");
    TableColumn totaleColumn = new TableColumn("Totale");
    TableColumn ActionsColumn = new TableColumn("Actions");

    static ObservableList<Vente> ventes = FXCollections.observableArrayList();



    private Predicate<Vente> createFilteringPredicate(String searchText){
        return vente -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsVente(vente,searchText);
        };
    }

    private boolean searchFindsVente(Vente vente, String searchText){
        return (vente.getId().toString().equals(searchText)) ||
                (vente.getClient().toLowerCase().contains(searchText.toLowerCase()));
    }




    static public void sortRowsByLastTimeAdded(){

        ventes.sort(Comparator.comparing(Vente::getCreeLe).reversed());

    }

    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(VentesTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        numBonColumn.prefWidthProperty().bind(VentesTable.widthProperty().multiply(sizeCoulumn));
        nomClientColumn.prefWidthProperty().bind(VentesTable.widthProperty().multiply(sizeCoulumn));
        dateVenteColumn.prefWidthProperty().bind(VentesTable.widthProperty().multiply(sizeCoulumn));
        nbrProduitsColumn.prefWidthProperty().bind(VentesTable.widthProperty().multiply(sizeCoulumn));
        totaleColumn.prefWidthProperty().bind(VentesTable.widthProperty().multiply(sizeCoulumn));
        ActionsColumn.prefWidthProperty().bind(VentesTable.widthProperty().multiply(sizeCoulumn));


    }


    private void setupValueFactories(){

        //define the cells factory
        numBonColumn.setCellValueFactory(
                new PropertyValueFactory<Vente, Long>("id"));
        totaleColumn.setCellValueFactory(
                new PropertyValueFactory<Vente, Integer>("totale"));
        dateVenteColumn.setCellValueFactory(
                new PropertyValueFactory<Vente, Timestamp>("creeLe"));
        nomClientColumn.setCellValueFactory(new PropertyValueFactory<Vente,String>("client"));

        setupNbrProduitsCellValueFactory();

        setupActionsCellValueFactory();


    }


    private void setupNbrProduitsCellValueFactory() {
        nbrProduitsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vente,Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Vente,Integer> cellDataFeatures) {
                return new SimpleIntegerProperty(cellDataFeatures.getValue().getLigneVentes().size());
            }
        });


    }


    private void setupActionsCellValueFactory() {
        Callback<TableColumn<Vente, Void>, TableCell<Vente, Void>> cellFactory = new Callback<TableColumn<Vente, Void>, TableCell<Vente, Void>>() {
            @Override
            public TableCell<Vente, Void> call(final TableColumn<Vente, Void> param) {
                final TableCell<Vente, Void> cell = new TableCell<Vente, Void>() {

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
                            Vente data = getTableView().getItems().get(getIndex());

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
                                OuiNonDialogController.TitleLabel.setText("Supprimer Vente");
                                OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de ce Vente ?");
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                dialog.showAndWait();
                                if (dialog.getResult()==ButtonType.YES)
                                {

                                    deleteVente(data);
                                }
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
                        hBox.getChildren().addAll(bt);
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

    private void deleteVente(Vente data) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        VentesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ventes.addAll(venteService.getAll());

        ventes.addListener(new ListChangeListener<Vente>() {
            @Override
            public void onChanged(Change<? extends Vente> change) {
                VentesTable.refresh();

            }
        });

        FilteredList<Vente> filteredData = new FilteredList<>(ventes);

        SearchBarTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createFilteringPredicate(newValue))
        );


        VentesTable.setItems(filteredData);
        //add columns to the table
        VentesTable.getColumns().addAll(numBonColumn,dateVenteColumn,nomClientColumn,nbrProduitsColumn,totaleColumn,ActionsColumn);
        setupValueFactories();
        divideTableWidthOnColumns();




    }
}