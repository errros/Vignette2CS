package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.entities.GroupeFavoris;
import com.wrmanager.wrmanagerfx.entities.Produit;
import com.wrmanager.wrmanagerfx.entities.ProduitFavori;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FavorieItemController implements Initializable {


    private Produit passedProduit;


    private ProduitFavori passedProduitFavori;

    Integer row;

    Integer column;


    @FXML
    private Label NomLbl;

    @FXML
    private Label PrixLbl;

    @FXML
    private Button SupprimerButton;


    @FXML
     GridPane itemGrid;

    private BooleanProperty empty = new SimpleBooleanProperty();


    @FXML
    void FavorieItemOnMouseClick(MouseEvent event) {

        System.out.println("hada howa passed produit = " +passedProduit);
        var groupeFavoris =  (GroupeFavoris) (FavorieDialogController.currentPressedBtn.getUserData());
        //item is empty and also this produit isn't part of this favorite group already
        if(empty.get() &&
                groupeFavoris.getProduitFavoris().stream().filter(pf -> pf.getProduit().equals(passedProduit)).findAny().isEmpty()){


            ProduitFavori produitFavori = ProduitFavori.builder().row(row).column(column).produit(passedProduit).groupeFavoris(groupeFavoris).build();

            setPassedProduitFavori(produitFavori);
            //insert produit favoris in the group
            FavorieDialogController.addToAGroup(produitFavori);
             empty.setValue(false);

        }

    }

    @FXML
    void SupprimerButtonOnAction(ActionEvent event) {
        empty.setValue(true);
        FavorieDialogController.removeFromAGroup(passedProduitFavori);


    }


    public Produit getPassedProduit() {
        return passedProduit;
    }

    public void setPassedProduit(Produit passedProduit) {
        this.passedProduit = passedProduit;
    }

    public boolean isEmpty() {
        return empty.get();
    }

    public BooleanProperty emptyProperty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty.set(empty);
    }


    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public ProduitFavori getPassedProduitFavori() {
        return passedProduitFavori;
    }

    public void setPassedProduitFavori(ProduitFavori passedProduitFavori) {
        this.passedProduitFavori = passedProduitFavori;

        empty.setValue(false);

 }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        empty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                 if(t1){

                     NomLbl.setText("");
                     PrixLbl.setText("");
                     itemGrid.getChildren().remove(SupprimerButton);

                 }else {


                     itemGrid.add(SupprimerButton,0,0);
                     NomLbl.setText(passedProduitFavori.getProduit().getDesignation());
                     PrixLbl.setText(passedProduitFavori.getProduit().getPrixVenteTotale().toString());

                 }
            }
        });


    }


    @Override
    public String toString() {
        System.out.println("");
        return "FavorieItemController{" +
                "passedProduit="+ passedProduit +
                ", passedProduitFavori=" + passedProduitFavori +
                ", row=" + row +
                ", column=" + column +
                ", NomLbl=" + NomLbl +
                ", PrixLbl=" + PrixLbl +
                ", SupprimerButton=" + SupprimerButton +
                ", itemGrid=" + itemGrid.getChildren() +
                '}';

    }
}

