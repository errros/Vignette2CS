package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.GroupeFavoris;
import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.entities.ProduitFavori;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

import static com.wrmanager.wrmanagerfx.Constants.*;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class FavorieDialogController implements Initializable {
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    private Dialog<ButtonType> dialog;
    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private GridPane FavorieItemGrid;

    @FXML
    private GridPane GroupItemGrid;


    static ObservableList<GroupeFavoris> groupesTmp = FXCollections.observableArrayList();

    private ObservableList<MFXButton> groupesButtons = FXCollections.observableArrayList();

    private List<FavorieItemController> favorieItemControllers = new ArrayList<>();


    static public Produit passedProduit;

    static public MFXButton currentPressedBtn ;

    static public void addToAGroup(ProduitFavori produitFavori){

        ((GroupeFavoris)currentPressedBtn.getUserData()).addProduitFavori(produitFavori);
        //passedProduit.addFavori(produitFavori);




    }
    static public void removeFromAGroup(ProduitFavori produitFavori){

        ((GroupeFavoris)currentPressedBtn.getUserData()).removeProduitFavori(produitFavori);


    }


    static public void addGroupe(String name){


        var gf = GroupeFavoris.builder().nom(name).build();
         groupesTmp.add(gf);


    }

    static public void renameGroupe(GroupeFavoris groupeFavoris){
        //find the index where the groupe was
       var index = IntStream.range(0, groupesTmp.size()).filter(i-> groupesTmp.get(i).equals(groupeFavoris)).findFirst();
    groupesTmp.remove(index.getAsInt());
    groupesTmp.add(index.getAsInt(),groupeFavoris);



    }

    static public void removeGroupe(GroupeFavoris groupeFavoris){
        var index = IntStream.range(0, groupesTmp.size()).filter(i-> groupesTmp.get(i).equals(groupeFavoris)).findFirst();
        groupesTmp.remove(index.getAsInt());


    }




    @FXML
    void AnnulerButtonOnClick(ActionEvent event) {

        groupesTmp.clear();
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();

        }
    @FXML
    void AjouterButtonOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterGroupDialogView));
            DialogPane AjouterGroupDialog = fxmlLoader.load();
            AjouterGroupDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            AjouterGroupDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterGroupDialogController ajouterGroupDialogController = fxmlLoader.getController();
            ajouterGroupDialogController.setOnEditMode(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(AjouterGroupDialog);


            //pass the current dialog to the dialog controller
            ajouterGroupDialogController.setDialog1(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterGroupDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
            ajouterGroupDialogController.setDialog1(dialog);

            //blur the favorie dialog and show the AjouterGroupDialog
            AjouterGroupDialog.getScene().setFill(Color.rgb(0,0,0,0));
            BoxBlur boxBlur=new BoxBlur();
            boxBlur.setIterations(10);
            boxBlur.setHeight(7);
            boxBlur.setWidth(7);
            this.dialog.getDialogPane().setEffect(boxBlur);


            dialog.showAndWait();

            this.dialog.getDialogPane().setEffect(null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void SauvgarderButtonOnAction(ActionEvent event) {



         favorisService.sauvegarderUpdateGroupeFavoris(groupesTmp);
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();

    }


    void  SetupItemsGrid(){

        favorieItemControllers.clear();
        int column = 0;
        int row = 0;
        for (int i = 0; i < 18; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("fxml/FavorieItem.fxml"));

            AnchorPane anchorPane = null;
            try {
                anchorPane = fxmlLoader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }

            FavorieItemController favorieItemController = fxmlLoader.getController();

            if (column == 3) {
                column = 0;
                row++;
            }
            favorieItemController.setColumn(column);
            favorieItemController.setRow(row);
             FavorieItemGrid.add(anchorPane, column++, row); //(child,column,row)


            favorieItemController.setEmpty(true);
            favorieItemController.setPassedProduit(passedProduit);

            favorieItemControllers.add(favorieItemController);
            //set grid width


            FavorieItemGrid.setAlignment(Pos.CENTER);

        }
    }



/*
    void cleanGroupsGrid(){


        groupesButtons.stream().forEach(mfxButton -> GroupItemGrid.getChildren().remove(mfxButton));


    }*/


    void setupNewGroupBtn(GroupeFavoris groupeFavoris , int position){


            MFXButton button = new MFXButton(groupeFavoris.getNom());
            button.setMinWidth(130);
            button.setMinHeight(60);

            //link the groupeFavoris instance with the suitable button
            button.setUserData(groupeFavoris);
            //button.setStyle("-fx-background-color: rgba(35, 140, 131, 1);-fx-text-fill:\"white\";-fx-background-radius:8;-fx-font-size: 18;");

            GroupItemGrid.add(button, 0, position); //(child,column,row)
            //set grid width
            button.setAlignment(Pos.CENTER);
            button.setOnAction(this::handleOnClick);
            //long click to rename or delete a groupd
            button.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

                long startTime;

                @Override
                public void handle(MouseEvent event) {
                    if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                        startTime = System.currentTimeMillis();
                    } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                        if (System.currentTimeMillis() - startTime > 250) {
                            try {
                                SideBarController.BlurBackground();
                                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(AjouterGroupDialogView));
                                DialogPane AjouterGroupDialog = fxmlLoader.load();
                                AjouterGroupDialog.getStylesheets().add(Main.class.getResource("images/dialog.css").toExternalForm());
                                AjouterGroupDialog.setStyle(
                                        "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                                                .replaceAll("0x","#")
                                                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                                                .replaceAll("0x","#")+";");
                                AjouterGroupDialogController ajouterGroupDialogController = fxmlLoader.getController();
                                //get the object that the click occured on , cast it into a MFXButton , get the GroupeFavoris Linked with it
                                ajouterGroupDialogController.setPassedGroupeFavoris((GroupeFavoris) ((MFXButton) event.getSource()).getUserData());
                                ajouterGroupDialogController.setOnEditMode(true);

                                Dialog<ButtonType> dialog = new Dialog<>();
                                dialog.setDialogPane(AjouterGroupDialog);
                                //pass the current dialog to the dialog controller
                                ajouterGroupDialogController.setDialog1(dialog);
                                dialog.initStyle(StageStyle.TRANSPARENT);
                                AjouterGroupDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                ajouterGroupDialogController.setDialog1(dialog);

                                //blur the favorie dialog and show the AjouterGroupDialog
                                AjouterGroupDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
                                BoxBlur boxBlur = new BoxBlur();
                                boxBlur.setIterations(10);
                                boxBlur.setHeight(7);
                                boxBlur.setWidth(7);
                                //this.dialog.getDialogPane().setEffect(boxBlur);*
                                dialog.showAndWait();

                                //this.dialog.getDialogPane().setEffect(null);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            groupesButtons.add(button);
            GroupItemGrid.setAlignment(Pos.CENTER);


        }
    void refreshGroupBtn(){

        groupesButtons.stream().forEach(mfxButton -> GroupItemGrid.getChildren().remove(mfxButton));
        groupesButtons.stream().forEach(mfxButton -> setupNewGroupBtn((GroupeFavoris) mfxButton.getUserData(), groupesTmp.size() - 1));

    }

    void refreshGroupGrid(){

        GroupItemGrid.getChildren().clear();
        IntStream.range(0,groupesButtons.size()).forEach(i-> GroupItemGrid.add(groupesButtons.get(i),0,i));

    }

    private void populateAndSortGroupes(){


    }
    private void removeBtn(GroupeFavoris gf ){

        //get the button linked to gf
        var button = groupesButtons.stream().filter(mfx-> ((GroupeFavoris) mfx.getUserData()).equals(gf)).findFirst().get();

        //remove that button from buttons group
        groupesButtons.remove(button);

        //remove the button from the grid
        refreshGroupGrid();


    }


    private void setupGroupesTmpList() {

        groupesTmp.clear();
        groupeFavorisesList.forEach(groupeFavoris -> {
            GroupeFavoris gf = new GroupeFavoris(groupeFavoris.getId(),groupeFavoris.getNom(),groupeFavoris.getCreeLe(),groupeFavoris.getProduitFavoris());
            groupesTmp.add(gf);
        });


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        GroupItemGrid.setVisible(true);
        FavorieItemGrid.setVisible(true);
        groupesTmp.clear();
        groupesButtons.clear();


        SetupItemsGrid();

        groupesTmp.addListener(new ListChangeListener<GroupeFavoris>() {
            @Override
            public void onChanged(Change<? extends GroupeFavoris> change) {

             while (change.next()) {

                 if (groupesTmp.isEmpty()) {
                     GroupItemGrid.setVisible(false);
                     FavorieItemGrid.setVisible(false);
                 } else if (change.wasAdded()) {
                     setupNewGroupBtn(change.getAddedSubList().get(0), groupesTmp.size() - 1);
                     FavorieItemGrid.setVisible(true);
                  groupesButtons.get(groupesButtons.size()-1).fire();
                 } else if (change.wasRemoved()) {
                    removeBtn(change.getRemoved().get(0));
                     groupesButtons.get(groupesButtons.size()-1).fire();

                 }

             }
              }
            }
        );



        setupGroupesTmpList();
        if(groupesTmp.isEmpty()){
            FavorieItemGrid.setVisible(false);
        }

    }


    public Produit getPassedProduit() {
        return passedProduit;
    }

    public void setPassedProduit(Produit passedProduit) {
        this.passedProduit = passedProduit;
        favorieItemControllers.stream().forEach(controller->controller.setPassedProduit(passedProduit));

    }
    private void handleOnClick(ActionEvent event) {
        //added by rabah
        if (currentPressedBtn!=null) {
            currentPressedBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-text-fill:#000000;-fx-background-radius:8;-fx-font-size: 18;");
        }
        currentPressedBtn = (MFXButton) event.getSource();
        currentPressedBtn.setStyle("-fx-background-color: rgba(35, 140, 131, 1);-fx-text-fill:\"white\";-fx-background-radius:8;-fx-font-size: 18;");
        currentPressedBtn.setDepthLevel(DepthLevel.LEVEL3);
        //

        //get the GroupeFavoris Linked with the clickedBtn
        GroupeFavoris groupeFavoris = (GroupeFavoris) currentPressedBtn.getUserData();
        for (int r = 0; r < 6; r++)
            for (int c = 0; c < 3; c++) {
                final var rr = r;
                final var cc = c;
                groupeFavoris.getProduitFavoris().stream().filter(produitFavori -> produitFavori.getRow() == rr
                        && produitFavori.getColumn() == cc ).findAny().ifPresentOrElse(produitFavori -> {

                    favorieItemControllers.get(rr * 3 + cc).setPassedProduitFavori(produitFavori);

                    // THE SAVIOR

                }, new Runnable() {
                    @Override
                    public void run() {
                        int index = (rr * 3 + cc);
             if(!favorieItemControllers.isEmpty()) {
                 favorieItemControllers.get(index).setEmpty(true);
             }

                    }
                });
            }

    }
}
