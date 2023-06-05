package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class Parametres1ViewController {

    @FXML
    private MFXButton AjouterCategorieButton;

    @FXML
    private MFXButton AjouterVendeurButton;

    @FXML
    private TableView<?> CategoriesTable;

    @FXML
    private TableView<?> VendeursTable;

    @FXML
    void AjouterCategorieButtonOnAction(ActionEvent event) {

    }

    @FXML
    void AjouterVendeurButtonIOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterVendeurDialogView));
            DialogPane AjouterVendeurDialog = fxmlLoader.load();
            AjouterVendeurDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            AjouterVendeurDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterVendeurDialogController ajouterVendeurDialogController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(AjouterVendeurDialog);
            //pass the current dialog to the dialog controller
            ajouterVendeurDialogController.setDialog(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterVendeurDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
            ajouterVendeurDialogController.setDialog(dialog);
            dialog.showAndWait();
            SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
