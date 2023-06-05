package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Achat;
import com.wrmanager.wrmanagerfx.entities.LigneAchat;
import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.models.LigneAchatTest;
import com.wrmanager.wrmanagerfx.models.SystemMeasure;
import com.wrmanager.wrmanagerfx.validation.Validators;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;
import static com.wrmanager.wrmanagerfx.validation.Validators.*;

public class AjouterAchat2DialogController implements Initializable {

    private Achat passedAchat;

    Dialog<ButtonType> dialog1;

    Dialog<ButtonType> dialog2;

    @FXML
    private Label titleLabel;
    @FXML
    private GridPane inputGridPane;

    @FXML
    private MFXButton AjouterButton;
    @FXML
    private Label AlertLbl;

    @FXML
    private MFXButton AnnulerButton;


    @FXML
    private MFXButton CodeBarreButton;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private Label Label3;

    @FXML
    private TableView<LigneAchat> LignesAchatTable;

    @FXML
    private Label TotaleLabel;

    @FXML
    private MFXButton ValiderButton;

    @FXML
    private MFXButton ViderButton;

    @FXML
    private TextField codeBarreTF;

    @FXML
    private MFXDatePicker datePeremptionDP;

    @FXML
    private TextField prxAchatTotaleTF;


    @FXML
    private TextField designationTF;

    @FXML
    private TextField prxAchatUnitaireTF;

    @FXML
    private TextField prxVenteTotaleTF;

    @FXML
    private TextField prxVenteUnitaireTF;

    @FXML
    private TextField qtyTotaleTF;

    @FXML
    private TextField qtyUnitaireTF;

    @FXML
    private TextField remarqueTF;


    @FXML
    private MFXCheckbox payeCheckBox;

    Map<String, Object> parameters = new HashMap<String, Object>();
    List<LigneAchatTest> ligneAchatTests=new ArrayList<>();

    private TableColumn codeBarreColumn = new TableColumn("Code a Barre");
    private TableColumn designationColumn = new TableColumn("Designation");
    private TableColumn qtyTotaleColumn = new TableColumn("Qty Totale");
    private TableColumn prixAchatColumn = new TableColumn("Prix d'Achat");
    private TableColumn prixVenteColumn = new TableColumn("Prix d'Vente");
    private TableColumn datePeremptionColumn = new TableColumn("Date de Peremption");
    private TableColumn totaleColumn = new TableColumn("Totale");
    private TableColumn actionsColumn = new TableColumn("Actions");



    private AutoCompletionBinding<String> autocompletion;



    private BooleanProperty onEditMode = new SimpleBooleanProperty(false);

    private ObservableList<LigneAchat> ligneAchats = FXCollections.observableArrayList();


    private void divideTableWidthOnColumns() {

        Double size = Double.valueOf(LignesAchatTable.getColumns().size());

        //calculate the size of each column
        double sizeCoulumn = 1 / (size);
        //set the size of each column
        codeBarreColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        designationColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        datePeremptionColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        qtyTotaleColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        totaleColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        prixAchatColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        prixVenteColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));
        actionsColumn.prefWidthProperty().bind(LignesAchatTable.widthProperty().multiply(sizeCoulumn));


    }


    private void setupValueFactories() {

        //define the cells factory
        setupCodeBarreCellValueFactory();
        setupDesignationCellValueFactory();
        setupTotaleCellValueFactory();

        datePeremptionColumn.setCellValueFactory(new PropertyValueFactory<LigneAchat, Date>("datePeremption"));
        qtyTotaleColumn.setCellValueFactory(new PropertyValueFactory<LigneAchat, Integer>("qtyTotale"));
        prixAchatColumn.setCellValueFactory(new PropertyValueFactory<LigneAchat, Integer>("prixAchatTotale"));
        prixVenteColumn.setCellValueFactory(new PropertyValueFactory<LigneAchat, Integer>("prixVenteTotale"));


        //nomFournisseur
        //nbrProduits
        setupActionsCellValueFactory();


    }



    private void setupActionsCellValueFactory() {

        Callback<TableColumn<LigneAchat, Void>, TableCell<LigneAchat, Void>> cellFactory = new Callback<TableColumn<LigneAchat, Void>, TableCell<LigneAchat, Void>>() {
            @Override
            public TableCell<LigneAchat, Void> call(final TableColumn<LigneAchat, Void> param) {
                final TableCell<LigneAchat, Void> cell = new TableCell<LigneAchat, Void>() {

                    private final MFXButton trushBtn = new MFXButton("");
                    private final MFXButton printBtn = new MFXButton("");
                    private final MFXButton editBtn = new MFXButton("");
                    private final HBox hBox = new HBox();


                    {
                        trushBtn.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView trush = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        trush.setSize("22.0");
                        trush.setFill(Color.rgb(193, 39, 39));
                        trushBtn.setGraphic(trush);
                        trushBtn.setOnAction((ActionEvent event) -> {
                            LigneAchat data = getTableView().getItems().get(getIndex());

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
                                OuiNonDialogController.TitleLabel.setText("Supprimer LigneAchat");
                                OuiNonDialogController.SubjectLabel.setText("Vous etes sur de la suppression de ce LigneAchat ?");
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                dialog.showAndWait();
                                if (dialog.getResult() == ButtonType.YES) {
                                    ligneAchats.remove(data);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });


                        editBtn.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView pencil = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE_ALT);
                        pencil.setFill(Color.rgb(35, 140, 131));
                        pencil.setSize("22.0");
                        editBtn.setGraphic(pencil);

                        if (onEditMode.get()) {
                            editBtn.setOnAction(event -> {
                                //// show the modification dialog
                                codeBarreTF.setEditable(false);
                                designationTF.setEditable(false);


                                LigneAchat data = getTableView().getItems().get(getIndex());
                                updateInputTextFields(data);
                                ViderButton.setVisible(false);
                                AjouterButton.setVisible(true);

                            });

                        } else {
                            editBtn.setOnAction(event -> {

                                codeBarreTF.setEditable(false);
                                designationTF.setEditable(false);
                                LigneAchat data = getTableView().getItems().get(getIndex());
                                updateInputTextFields(data);
                                AjouterButton.setText("Modifier");
                                AjouterButton.setOnAction(ModifierButtonOnActionOnEditMode());

                                ViderButton.setVisible(false);

                            });

                        }

                        printBtn.setStyle("-fx-background-color:rgba(0,0,0,0);");
                        FontAwesomeIconView printer = new FontAwesomeIconView(FontAwesomeIcon.BARCODE);
                        printer.setFill(Color.rgb(0, 0, 0));
                        printer.setSize("19.0");
                        printBtn.setGraphic(printer);
                        printBtn.setOnAction(event -> {
                            LigneAchat data = getTableView().getItems().get(getIndex());

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
                                impressionCodeBarreDialogController.setPassedProduit(data.getProduit());
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                ImpressionDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                impressionCodeBarreDialogController.setDialog(dialog);
                                dialog.showAndWait();
                                SideBarController.DeleteBlurBackground();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        trushBtn.setAlignment(Pos.TOP_CENTER);
                        hBox.setSpacing(10);
                        hBox.setPadding(new Insets(0, 0, 0, 0));
                        hBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), Insets.EMPTY)));
                        hBox.setPadding(new Insets(0, 0, 0, 0));
                        hBox.getChildren().addAll(trushBtn, editBtn, printBtn);
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


        actionsColumn.setCellFactory(cellFactory);


    }

    private void setupTotaleCellValueFactory() {
        totaleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneAchat, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LigneAchat, Integer> ligneAchatIntegerCellDataFeatures) {

                return new SimpleIntegerProperty(ligneAchatIntegerCellDataFeatures.getValue().getTotale()).asObject();
            }
        });

    }

    private void setupDesignationCellValueFactory() {

        designationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneAchat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LigneAchat, String> ligneAchatStringCellDataFeatures) {
                return new SimpleStringProperty(ligneAchatStringCellDataFeatures.getValue().getProduit().getDesignation());
            }
        });


    }

    private void setupCodeBarreCellValueFactory() {


        codeBarreColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneAchat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LigneAchat, String> ligneAchatStringCellDataFeatures) {
                return new SimpleStringProperty(ligneAchatStringCellDataFeatures.getValue().getProduit().getCodeBarre());
            }
        });


    }


    private LigneAchat retrieveLigneAchatFromInput() {
        var produit = produitsList.stream().filter(p -> p.getCodeBarre().equals(codeBarreTF.getText())).findAny().get();
        var prixAchatUnite = prxAchatUnitaireTF.getText();
        var prixVenteUnite = prxVenteUnitaireTF.getText();
        var prixVenteTotale = prxVenteTotaleTF.getText();
        var prixAchatTotale = prxAchatTotaleTF.getText();
        var qtyTotale = qtyTotaleTF.getText();
        var qtyUnite = qtyUnitaireTF.getText();
        var remarque = remarqueTF.getText();
        String date = produit.getEstPerissable() ? datePeremptionDP.getValue().toString() : null;



        if(validateInput(qtyTotale,qtyUnite,prixAchatTotale,prixAchatUnite,
                prixVenteTotale,prixVenteUnite,produit,date,remarque)) {
            LigneAchat la = LigneAchat.builder().produit(produit).
                    achat(passedAchat).prixAchatUnite(Integer.valueOf(prixAchatUnite)).prixVenteUnite(Integer.valueOf(prixVenteUnite))
                    .prixVenteTotale(Integer.valueOf(prixVenteTotale))
                    .prixAchatTotale(Integer.valueOf(prixAchatTotale)).qtyTotale(Float.valueOf(qtyTotale)).
                    qtyUnite(Float.valueOf(qtyUnite)).remarque(remarque).build();
            if (produit.getEstPerissable()) {
                la.setDatePeremption(Date.valueOf(datePeremptionDP.getValue()));
            }

            la.setTotale(la.getTotale());
            return la;
        } else {
            throw new IllegalArgumentException("validation failed!");
        }



    }



    EventHandler<ActionEvent> ModifierButtonOnActionOnEditMode() {

        return actionEvent -> {


            try {


                LigneAchat la = retrieveLigneAchatFromInput();

                var a = ligneAchats.stream().filter(ligneAchat -> ligneAchat.getProduit().getCodeBarre().equals(la.getProduit().getCodeBarre())).findAny();

                var index = IntStream.range(0, ligneAchats.size()).filter(i -> ligneAchats.get(i).equals(a.get())).findAny().getAsInt();

                la.setId(a.get().getId());
                ligneAchats.remove(a.get());
                ligneAchats.add(index, la);
                AjouterButton.setVisible(false);
                ViderButton.setVisible(true);

                viderInput();

                setupPrxKeyChangeListeners();

                if (!onEditMode.get()) {
                    AjouterButton.setVisible(true);
                    AjouterButton.setText("Ajouter");
                    AjouterButton.setOnAction(AjouterButtonOnActionOnEditMode());
                    ViderButton.setVisible(true);
                    codeBarreTF.setEditable(true);
                    designationTF.setEditable(true);


                }

            } catch (IllegalArgumentException e){

            }


        };
    }

    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
try {


    LigneAchat la = retrieveLigneAchatFromInput();

    if(ligneAchats.stream().filter(ligneAchat -> ligneAchat.getProduit().equals(la.getProduit())).findAny().isPresent()){
        AlertLbl.setVisible(true);
        AlertLbl.setText("ce produit existe deja dans les lignes achats, modifier la qte");
        throw new IllegalArgumentException("ligne achat exist deja");
    }

    ligneAchats.add(la);
    viderInput();
    setupPrxKeyChangeListeners();

} catch (IllegalArgumentException e){

}


    }


    EventHandler<ActionEvent> AjouterButtonOnActionOnEditMode() {

        return actionEvent -> {
            try {
                LigneAchat la = retrieveLigneAchatFromInput();

                if(ligneAchats.stream().filter(ligneAchat -> ligneAchat.getProduit().equals(la.getProduit())).findAny().isPresent()){
                    AlertLbl.setVisible(true);
                    AlertLbl.setText("ce produit existe deja dans les lignes achats, modifier la qte");
                    throw new IllegalArgumentException("ligne achat exist deja");
                }


                ligneAchats.add(la);
                viderInput();
                setupPrxKeyChangeListeners();
            } catch (IllegalArgumentException e){

            }
        };
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
            OuiNonDialogController.TitleLabel.setText("Annulation d'Achat");
            OuiNonDialogController.SubjectLabel.setText("Vous etes sur de l'annulation de cet Achat ?");
            dialog.initStyle(StageStyle.TRANSPARENT);
            OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setIterations(10);
            boxBlur.setHeight(7);
            boxBlur.setWidth(7);
            dialog1.getDialogPane().setEffect(boxBlur);
            var result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {

                achatService.delete(passedAchat);

            }

            dialog1.getDialogPane().setEffect(null);
            if (dialog.getResult() == ButtonType.YES) {
                closeDialogs();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    EventHandler<ActionEvent> AnnulerButtonOnActionOnEditMode() {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
                    OuiNonDialogController.TitleLabel.setText("Annulation d'Achat");
                    OuiNonDialogController.SubjectLabel.setText("Vous etes sur de l'annulation de cet Achat ?");
                    dialog.initStyle(StageStyle.TRANSPARENT);
                    OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                    BoxBlur boxBlur = new BoxBlur();
                    boxBlur.setIterations(10);
                    boxBlur.setHeight(7);
                    boxBlur.setWidth(7);
                    dialog1.getDialogPane().setEffect(boxBlur);
                    var result = dialog.showAndWait();



                    dialog1.getDialogPane().setEffect(null);
                    if (dialog.getResult() == ButtonType.YES) {
                        closeDialogs();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };

    }


    @FXML
    void ValiderButtonOnAction(ActionEvent event) {


        passedAchat.setPaye(payeCheckBox.isSelected());
        passedAchat.setTotale(passedAchat.getTotale());
        AchatsViewController.addAchat(passedAchat,ligneAchats);
        Preferences prefs = Preferences.userRoot().node(Main.class.getName());
        if (prefs.getBoolean("DialogValidationBonAchat",true)) {
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
                OuiNonDialogController.TitleLabel.setText("Impression Bon d'Achat");
                OuiNonDialogController.SubjectLabel.setText("Vous Voulez imprimer un Bon d'Achat ?");
                dialog.initStyle(StageStyle.TRANSPARENT);
                OuiNonDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                BoxBlur boxBlur = new BoxBlur();
                boxBlur.setIterations(10);
                boxBlur.setHeight(7);
                boxBlur.setWidth(7);
                dialog1.getDialogPane().setEffect(boxBlur);
                var result = dialog.showAndWait();
                closeDialogs();

                dialog1.getDialogPane().setEffect(null);

                if (result.isPresent() && result.get() == ButtonType.YES) {


                    FillParam();
                    FillTestList();
                    try {
                        JasperReportLoader(ligneAchatTests, parameters);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeDialogs();








    }





    EventHandler<ActionEvent> ValiderButtonOnActionOnEditMode() {

        return actionEvent -> {

              passedAchat.setPaye(payeCheckBox.isSelected());
              passedAchat.setTotale(passedAchat.getTotale());
              AchatsViewController.addAchat(passedAchat,ligneAchats);

            closeDialogs();

        };

    }

    @FXML
    void ViderButtonOnAction(ActionEvent event) {
        viderInput();
        setupPrxKeyChangeListeners();

    }

    @FXML
    void CodeBarreButtonOnAction(ActionEvent event) {


    }


    EventHandler<KeyEvent> prxAchatTotaleOnInputTextChanged() {

     return inputMethodEvent -> {

             var t1 = prxAchatTotaleTF.getText();
         if(!t1.isEmpty()) {
             var opt = produitsList.stream().filter(produit -> produit.getCodeBarre().equals(codeBarreTF.getText())).findAny();
             opt.ifPresent(produit -> {

                      prxAchatUnitaireTF.setOnKeyTyped(null);

                         Integer prixVenteTotale = Integer.valueOf(t1) + Math.round(Integer.valueOf(t1) * produit.getCategorie().getMarge() / Float.valueOf("100.0"));
                         prxVenteTotaleTF.setText(prixVenteTotale.toString());

                         if(!qtyUnitaireTF.getText().isEmpty()) {
                             Integer prxAchatUnit = Math.round(Integer.valueOf(t1) / Float.valueOf(qtyUnitaireTF.getText()));
                             prxAchatUnitaireTF.setText(prxAchatUnit.toString());
                             Integer prxVenteUnit = Math.round(prixVenteTotale / Float.valueOf(qtyUnitaireTF.getText()));
                             prxVenteUnitaireTF.setText(prxVenteUnit.toString());


                         }
                     }
             );
         }


     };


    }


    EventHandler<KeyEvent> prxAchatUnitaireOnInputTextChanged() {
        return inputMethodEvent -> {

            var t1 = prxAchatUnitaireTF.getText();
            if(!t1.isEmpty()) {
                var opt = produitsList.stream().filter(produit -> produit.getCodeBarre().equals(codeBarreTF.getText())).findAny();
                opt.ifPresent(produit -> {

                          prxAchatTotaleTF.setOnKeyTyped(null);


                          var benif = Math.round(Integer.valueOf(t1) * Float.valueOf(produit.getCategorie().getMarge() / Float.valueOf("100.0")));
                            Integer prxVenteUnit =  Integer.valueOf(t1) + benif;

                            prxVenteUnitaireTF.setText(prxVenteUnit.toString());


                            if(!qtyUnitaireTF.getText().isEmpty()) {

                                Integer prxAchatTotale = Math.round(Integer.valueOf(t1) * Float.valueOf(qtyUnitaireTF.getText()));
                                prxAchatTotaleTF.setText(prxAchatTotale.toString());

                                Integer prixVenteTotale = Math.round(prxVenteUnit * Float.valueOf(qtyUnitaireTF.getText()));
                                prxVenteTotaleTF.setText(prixVenteTotale.toString());


                            }


                        }
                );
            }



        };

    }

    EventHandler<KeyEvent> prxVenteUnitaireOnInputTextChanged(){

        return keyEvent -> {


            var t1 = prxVenteUnitaireTF.getText();
            if(!t1.isEmpty()) {
                var opt = produitsList.stream().filter(produit -> produit.getCodeBarre().equals(codeBarreTF.getText())).findAny();
                opt.ifPresent(produit -> {




                            if(!qtyUnitaireTF.getText().isEmpty() && !qtyTotaleTF.getText().isEmpty()) {
                              Integer prixVenteTotale = Math.round(Integer.valueOf(t1) * Float.valueOf(qtyUnitaireTF.getText()));
                                prxVenteTotaleTF.setText(prixVenteTotale.toString());


                            }


                        }
                );
            }



        };









    }


    private void closeDialogs() {

        dialog1.setResult(ButtonType.CLOSE);
        dialog1.close();
        dialog2.setResult(ButtonType.CLOSE);
        dialog2.close();
        SideBarController.DeleteBlurBackground();


    }


    private void viderInput() {
        codeBarreTF.setText("");
        designationTF.setText("");
        prxAchatTotaleTF.setText("");
        prxAchatUnitaireTF.setText("");
        prxVenteTotaleTF.setText("");
        prxVenteUnitaireTF.setText("");
        qtyTotaleTF.setText("");
        qtyUnitaireTF.setText("");
        remarqueTF.setText("");
AlertLbl.setVisible(false);
    }

    public void setDialog1(Dialog<ButtonType> dialog) {
        this.dialog1 = dialog;
    }

    public void setDialog2(Dialog<ButtonType> dialog) {
        this.dialog2 = dialog;
    }

    public Achat getPassedAchat() {
        return passedAchat;

    }


    public void setPassedAchat(Achat passedAchat) {

        this.passedAchat = passedAchat;
        Label1.setText("Bon Nº : " + getPassedAchat().getId().toString());
        Label2.setText("Fournisseur : " + passedAchat.getFournisseur().getNom() + " " + passedAchat.getFournisseur().getPrenom());

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

    private void codeBarreTFBindWithOtherFields() {

        codeBarreTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                produitsList.stream().filter(produit -> produit.getCodeBarre().equals(t1)).findAny().ifPresentOrElse(produit ->
                {


                    setInputsEditable(true);
                    designationTF.setText(produit.getDesignation());

                    if (produit.getEstPerissable()) {
                        Optional.ofNullable(produit.getDatePeremption()).ifPresent(date -> datePeremptionDP.setValue(date.toLocalDate()));

                    } else {
                        datePeremptionDP.setDisable(true);

                    }
                    prxAchatTotaleTF.setText(produit.getPrixAchatTotale().toString());
                    prxVenteTotaleTF.setText(produit.getPrixVenteTotale().toString());
                    prxAchatUnitaireTF.setText(produit.getPrixAchatUnite().toString());
                    prxVenteUnitaireTF.setText(produit.getPrixVenteUnite().toString());
                    qtyUnitaireTF.setText(produit.getQtyUnite().toString());
                    qtyTotaleTF.setText("1");


                }, new Runnable() {
                    @Override
                    public void run() {

                        setInputsEditable(false);

                    }
                });

            }
        });


    }

    private AjouterProduitDialogController openAjouterProduitDialog(){
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
            dialog.show();
            return ajouterProduitDialogController;

        } catch (IOException e) {
            e.printStackTrace();
        }

return null;

    }


    private void designationTFBindWithOtherFields() {

        designationTF.textProperty().addListener(new ChangeListener<String>() {
                                                     @Override
                                                     public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                                                         produitsList.stream().filter(produit -> produit.getDesignation().equals(t1)).findAny().ifPresentOrElse(produit ->
                                                         {
                                                             codeBarreTF.setText(produit.getCodeBarre());


                                                             setInputsEditable(true);
                                                             if(produit.getEstPerissable()){

                                                             }
                                                             qtyTotaleTF.setText("1");
                                                             prxAchatTotaleTF.setText(produit.getPrixAchatTotale().toString());
                                                             prxVenteTotaleTF.setText(produit.getPrixVenteTotale().toString());
                                                             prxAchatUnitaireTF.setText(produit.getPrixAchatUnite().toString());
                                                             prxVenteUnitaireTF.setText(produit.getPrixVenteUnite().toString());
                                                             qtyUnitaireTF.setText(produit.getQtyUnite().toString());




                                                         }, new Runnable() {
                                                             @Override
                                                             public void run() {
                                                                 setInputsEditable(false);
                                                             }
                                                         });

                                                     }
                                                 }
        );


    }



    private void newCodeBarreChangeListener(){
        codeBarreTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(Validators.isCodeBarreValid(t1,true).equals("true")){
                    if(produitsList.stream().filter(produit -> produit.getCodeBarre().equals(t1)).findAny().isEmpty()){
                       var controller = openAjouterProduitDialog();
                        controller.setCodeBarreTfd(t1);
                    }

                }

            }
        });


    }




    private void updateInputTextFields(LigneAchat ligneAchat) {
        codeBarreTF.setText(ligneAchat.getProduit().getCodeBarre());
        designationTF.setText(ligneAchat.getProduit().getDesignation());

        if(ligneAchat.getProduit().getEstPerissable() && Optional.ofNullable(ligneAchat.getDatePeremption()).isPresent()) {
            datePeremptionDP.setValue(ligneAchat.getDatePeremption().toLocalDate());

        }

        prxVenteUnitaireTF.setText(ligneAchat.getPrixVenteUnite().toString());
        prxAchatUnitaireTF.setText(ligneAchat.getPrixAchatUnite().toString());
        prxAchatTotaleTF.setText(ligneAchat.getPrixAchatTotale().toString());
        prxVenteTotaleTF.setText(ligneAchat.getPrixVenteTotale().toString());
        qtyTotaleTF.setText(ligneAchat.getQtyTotale().toString());
        qtyUnitaireTF.setText(ligneAchat.getQtyUnite().toString());
        if(Optional.ofNullable(ligneAchat.getRemarque()).isPresent()){
            remarqueTF.setText(ligneAchat.getRemarque());
        }

    }

private void setupDesignationAutoComplete(){

     autocompletion = TextFields.bindAutoCompletion(designationTF, produitsList.stream().map(Produit::getDesignation).collect(Collectors.toList()));
    produitsList.addListener(new ListChangeListener<Produit>() {
        @Override
        public void onChanged(Change<? extends Produit> change) {
            updateSuggestions();
        }
    });


}

private void updateSuggestions(){

        autocompletion.dispose();
     autocompletion = TextFields.bindAutoCompletion(designationTF, produitsList.stream().map(Produit::getDesignation).collect(Collectors.toList()));


}

    private void setInputsEditable(boolean b) {
        datePeremptionDP.setEditable(b);
        prxAchatTotaleTF.setEditable(b);
        prxVenteTotaleTF.setEditable(b);
        prxAchatUnitaireTF.setEditable(b);
        prxVenteUnitaireTF.setEditable(b);
        qtyUnitaireTF.setEditable(b);
        qtyTotaleTF.setEditable(b);
        remarqueTF.setEditable(b);
    }

    private Integer calculateTotaleAchat() {
        return ligneAchats.stream().map(LigneAchat::getTotale).reduce(0, Integer::sum);
    }

private Boolean validateInput(String qtyTotale , String qtyUnitaire
        , String prixAchatT , String prixAchatU , String prixVenteT,
          String prixVenteU , Produit produit , String datePeremption, String remarque){



    String qtyTotaleValidation = produit.getSystemMeasure() == SystemMeasure.UNITE ? isQtyIntValid(qtyTotale,true) : isQtyFloatValid(qtyTotale,true);
    if(!qtyTotaleValidation.equals("true")){
        AlertLbl.setVisible(true);
        AlertLbl.setText(qtyTotaleValidation);
        return false;
    }



        String qtyUnitValidation = produit.getSystemMeasure() == SystemMeasure.UNITE ? isQtyIntValid(qtyUnitaire,true) : isQtyFloatValid(qtyUnitaire,true);
        if(!qtyUnitValidation.equals("true")){
            AlertLbl.setVisible(true);
            AlertLbl.setText(qtyUnitValidation);
            return false;
        }

    String prxValidations = isPrixVenteGreaterThanPrixAchat(prixAchatU,prixVenteU,prixAchatT,prixVenteT);
      if(!prxValidations.equals("true")) {
          AlertLbl.setVisible(true);
          AlertLbl.setText(prxValidations);
          return false;
      }

      if(produit.getEstPerissable() && datePeremption != null){
          String dateValidation = isDatePeeremptionValid(datePeremption,true);
          if(!dateValidation.equals("true")){
              AlertLbl.setVisible(true);
              AlertLbl.setText(dateValidation);
              return false;
          }
      }


      String remarqueValidation = isRemarqueValid(remarque,false);
      if(!remarqueValidation.equals("true")){
          AlertLbl.setVisible(true);
          AlertLbl.setText(remarqueValidation);
          return false;
      }


return true;

    }


    private void setupPrxKeyChangeListeners() {
        prxAchatUnitaireTF.setOnKeyTyped(prxAchatUnitaireOnInputTextChanged());
        prxAchatTotaleTF.setOnKeyTyped(prxAchatTotaleOnInputTextChanged());
        prxVenteUnitaireTF.setOnKeyTyped(prxVenteUnitaireOnInputTextChanged());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setInputsEditable(false);
        AlertLbl.setVisible(false);

        codeBarreTFBindWithOtherFields();
        designationTFBindWithOtherFields();
        setupPrxKeyChangeListeners();

        setupDesignationAutoComplete();



        LignesAchatTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ligneAchats.addListener(new ListChangeListener<LigneAchat>() {
            @Override
            public void onChanged(Change<? extends LigneAchat> change) {
                LignesAchatTable.refresh();
                Label3.setText("Nombre de Produits : " + ligneAchats.size());
                TotaleLabel.setText("Totale : " + calculateTotaleAchat() + " DA");
            }
        });


        LignesAchatTable.setItems(ligneAchats);
        LignesAchatTable.getColumns().addAll(codeBarreColumn, designationColumn, qtyTotaleColumn, prixAchatColumn, prixVenteColumn, datePeremptionColumn, totaleColumn, actionsColumn);


        setupValueFactories();
        divideTableWidthOnColumns();


        //ViderButton turn into Ajouter , AjouterButton turn into Modifier
        onEditMode.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {

                    titleLabel.setText("Modifier Achat");
                    TotaleLabel.setText(passedAchat.getTotale().toString());
                    payeCheckBox.setSelected(passedAchat.getPaye());

                    ligneAchats.clear();
                    ligneAchats.addAll(passedAchat.getLigneAchats());

                    ViderButton.setText("Ajouter");

                    ViderButton.setOnAction(AjouterButtonOnActionOnEditMode());

                    AjouterButton.setText("Modifier");

                    AjouterButton.setOnAction(ModifierButtonOnActionOnEditMode());

                    AnnulerButton.setOnAction(AnnulerButtonOnActionOnEditMode());
                    ValiderButton.setOnAction(ValiderButtonOnActionOnEditMode());

                    AjouterButton.setVisible(false);


                }
            }
        });

        newCodeBarreChangeListener();


    }
public  void  FillParam(){
    Preferences prefs = Preferences.userRoot().node(Main.class.getName());
    parameters.put("Fournisseur", "rabah");
    System.out.println(prefs.get("LogoPath", "" ));
    parameters.put("LogoPath", (prefs.get("LogoPath", "" )));
    parameters.put("To", "Fournisseur :");
    parameters.put("NumeroDeMagasin", prefs.get("NumMagasin", "" ));
    parameters.put("NomDeMagasin",prefs.get("NomMagasin", "" ));
    parameters.put("Address", prefs.get("AddressMagasin", "" ));
    parameters.put("Remarque", prefs.get("RemarqueMagasin", "" ));
    parameters.put("NumBon", 10);
    parameters.put("Vendeur", "samir");
    parameters.put("Totale", "1000 DA");


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    LocalDateTime now = LocalDateTime.now();

    parameters.put("Date",  String.valueOf(dtf.format(now)));

}

public void FillTestList(){
    LigneAchatTest l1=new LigneAchatTest("CHIPS MAHBOUL POMME DE TERRE","100","100 DA");
    LigneAchatTest l2=new LigneAchatTest("pepsi 1l","100","100 DA");


    ligneAchatTests.add(l1);
    ligneAchatTests.add(l1);

    ligneAchatTests.add(l2);
    ligneAchatTests.add(l2);
    ligneAchatTests.add(l2);
}



    public void JasperReportLoader(List<LigneAchatTest> ligneAchatTests ,Map<String, Object> parameters ) throws FileNotFoundException, JRException {

        /* Convert List to JRBeanCollectionDataSource */
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(ligneAchatTests);


        parameters.put("collectionparam", itemsJRBean);
        /* Map to hold Jasper report Parameters */

        int j=0;

        //read jrxml file and creating jasperdesign object
        InputStream input = new FileInputStream(new File(Main.class.getResource("jrxml/BonAchat.jrxml").getFile()));


        for (int i = 0; i <ligneAchatTests.size(); i++) {
            if (ligneAchatTests.get(i).getDesignation().length()>13)j++;
        }

        JasperDesign jasperDesign = JRXmlLoader.load(input);
        jasperDesign.setPageHeight(250+(j*30)+((ligneAchatTests.size()-j)*20));

        /*compiling jrxml with help of JasperReport class*/
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        /* Using jasperReport object to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        /*call jasper engine to display report in jasperviewer window*/
        JasperViewer.viewReport(jasperPrint, false);


        /* outputStream to create PDF */
        //OutputStream outputStream = new FileOutputStream(new File(outputFile));


        /* Write content to PDF file */
        //JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        System.out.println("File Generated");


    }








}
