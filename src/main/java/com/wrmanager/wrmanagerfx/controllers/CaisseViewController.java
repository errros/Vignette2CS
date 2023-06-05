package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;
import static com.wrmanager.wrmanagerfx.controllers.SideBarController.root1;

public class CaisseViewController implements Initializable {
    @FXML
    private Label Time;
    @FXML
    private AnchorPane RootAnchor;


    @FXML
    private TextField ClientTfd;

    @FXML
    private ListView<String> CategoryListView;
    public static ObservableList<String> items = FXCollections.observableArrayList(
            "boissons","laitiers","grasses","sucres ","légumes ","fruits","Pâtes ","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc","bbb","ccc"
    );

    @FXML
    void ClientTfdOnAction(MouseEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.AjouterClientDialogView));
            DialogPane AjouterClientDialog = fxmlLoader.load();
            AjouterClientDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            AjouterClientDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            AjouterClientDialogController ajouterClientDialogController = fxmlLoader.getController();
            javafx.scene.control.Dialog<javafx.scene.control.ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(AjouterClientDialog);
            //pass the current dialog to the dialog controller
            ajouterClientDialogController.setDialog(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            AjouterClientDialog.getScene().setFill(Color.rgb(0, 0, 0, 0));
            ajouterClientDialogController.setDialog(dialog);
            dialog.showAndWait();
            if (dialog.getResult().equals(javafx.scene.control.ButtonType.OK))
            {ClientTfd.setText("Client : "+ajouterClientDialogController.NomTfd.getText());}

            SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RootAnchor.setStyle(
                "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                        .replaceAll("0x","#")
                        +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                        .replaceAll("0x","#")+";");
        CurrentTimeLabel();
        CategoryListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    MFXButton btn =new MFXButton();
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        if (item == null || empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
                            btn.setText(item);
                            btn.setButtonType(ButtonType.RAISED);
                            btn.setDepthLevel(DepthLevel.LEVEL3);
                            btn.setMaxHeight(USE_COMPUTED_SIZE);
                            btn.setMaxWidth(USE_COMPUTED_SIZE);
                            btn.setMinHeight(USE_COMPUTED_SIZE);
                            btn.setMinWidth(USE_COMPUTED_SIZE);
                            btn.setPrefHeight(60);
                            btn.setPrefWidth(120);
                            btn.setPadding(new Insets(0,0,5,0));
                            btn.setStyle("-fx-background-color: rgba(35, 140, 131, 1);-fx-text-fill:#ffffff;-fx-background-radius:10;-fx-font-size:18;-fx-font-weight: bold;");
                            btn.setOnAction((ActionEvent event) -> {
                                System.out.println(getItem());
                            });
                            setText("");
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
        CategoryListView.setItems(items);
    }

    private void CurrentTimeLabel() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();

    }
}
