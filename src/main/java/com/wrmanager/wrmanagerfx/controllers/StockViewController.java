package com.wrmanager.wrmanagerfx.controllers;
import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Fournisseur;
import com.wrmanager.wrmanagerfx.entities.Produit;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
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

import javax.swing.text.TabableView;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class StockViewController implements Initializable {

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
    private TableView<Produit> StockTable;

    public TableView<Produit> getStockTable() {
        return StockTable;
    }

    @FXML
    private AnchorPane icon;

    @FXML
    private AnchorPane icon1;

    @FXML
    private AnchorPane icon2;

    @FXML
    private AnchorPane icon3;


    TableColumn CodeBarreColumn = new TableColumn("Code Barre");
    TableColumn DesignationColumn = new TableColumn("Désignation");
    TableColumn QuantiteColumn = new TableColumn("Qty");
    TableColumn PrixventeColumn = new TableColumn("Prix de Vente");
    TableColumn PrixAchatColumn = new TableColumn("Prix d'Achat");
    TableColumn DateColumn = new TableColumn("Date de péremption");
    TableColumn<Produit, Void> ActionsColumn = new TableColumn("Actions");


    private Predicate<Produit> createFilteringPredicate(String searchText){
        return produit -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsProduit(produit,searchText);
        };
    }

    private boolean searchFindsProduit(Produit produit, String searchText){
        return (produit.getCodeBarre().toLowerCase().contains(searchText.toLowerCase())) ||
                (produit.getDesignation().toLowerCase().contains(searchText.toLowerCase()));
    }

    static public void sortRowsByLastTimeAdded(){
//        StockViewController.produits.sort(Comparator.comparing(Produit::getCreeLe).reversed());
    }


    private void setupValueFactories(){


        //define the cells factory
        CodeBarreColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, String>("codeBarre"));
        DesignationColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, String>("designation"));
        QuantiteColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, Integer>("qtyTotale"));
        PrixventeColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, Integer>("prixVenteUnite"));
        PrixAchatColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, Integer>("prixAchatUnite"));
        DateColumn.setCellValueFactory(
                new PropertyValueFactory<Produit, Date>("datePeremption"));

        setupActionsCellValueFactory();

    }


    private void setupActionsCellValueFactory() {
        Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> cellFactory = new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
            @Override
            public TableCell<Produit, Void> call(final TableColumn<Produit, Void> param) {
                final TableCell<Produit, Void> cell = new TableCell<Produit, Void>() {

                    private final MFXButton bt1 = new MFXButton("");
                    private final HBox hBox =new HBox();


                    {
                        bt1.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView pencil =new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                        pencil.setFill(Color.valueOf(prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")));
                        pencil.setSize("22.0");
                        bt1.setGraphic(pencil);
                        bt1.setOnAction(event -> {
                            Produit data = getTableView().getItems().get(getIndex());
                            //// show the modification dialog
                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.ModifierStockDialogView));
                                DialogPane ModifierProduitDialog = fxmlLoader.load();
                                ModifierProduitDialog.getStylesheets().add(
                                        Main.class.getResource("images/dialog.css").toExternalForm());
                                ModifierProduitDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");
                                ModifierStockDialogController modifierStockDialogController  = fxmlLoader.getController();

                                modifierStockDialogController.setPassedProduit(data);
                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(ModifierProduitDialog);
                                modifierStockDialogController.setDialog1(dialog);

                                dialog.initStyle(StageStyle.TRANSPARENT);
                                ModifierProduitDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                modifierStockDialogController.setDialog1(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });

                        hBox.setSpacing(17);
                        hBox.setPadding(new Insets(0,15,0,15));
                        hBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
                        hBox.setPadding(new Insets(0,0, 0,0));
                        hBox.getChildren().addAll(bt1);
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

    private void divideTableWidthOnColumns(){

        Double size = Double.valueOf(StockTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        CodeBarreColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));
        DesignationColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));
        QuantiteColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));
        PrixAchatColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));
        PrixventeColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));
        DateColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));
        ActionsColumn.prefWidthProperty().bind(StockTable.widthProperty().multiply(sizeCoulumn));

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        StockTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        produitsList.addListener(new ListChangeListener<Produit>() {
            @Override
            public void onChanged(Change<? extends Produit> change) {
                StockTable.refresh();
                Label1.setText(String.valueOf(produitsList.size()));
            }
        });


        FilteredList<Produit> filteredData = new FilteredList<>(produitsList);

        SearchBarTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createFilteringPredicate(newValue))
        );


        StockTable.getColumns().addAll(CodeBarreColumn,DesignationColumn,QuantiteColumn,PrixventeColumn,PrixAchatColumn,DateColumn,ActionsColumn);
        setupValueFactories();
        divideTableWidthOnColumns();
        StockTable.setItems(filteredData);
    }



}
