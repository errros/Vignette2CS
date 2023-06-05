package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Notification;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NotificationsViewController implements Initializable {
    Dialog<ButtonType> dialog;



    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private VBox VboxNotifications;

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;

    private ObservableList<Notification> notifications = FXCollections.observableArrayList();

    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();

    }
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

private void setupNotificationItem(Notification notif) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.NotificationItemView));
        GridPane notification = fxmlLoader.load();
        NotificationItemViewController notificationItemViewController = fxmlLoader.getController();

        notificationItemViewController.setNotifcation(notif);
        VboxNotifications.getChildren().add(notification);
    } catch (IOException e) {
        e.printStackTrace();
    }


}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        notifications.addListener(new ListChangeListener<Notification>() {
            @Override
            public void onChanged(Change<? extends Notification> change) {
           while (change.next()) {
               change.getAddedSubList().forEach(notification -> setupNotificationItem(notification));
           }

           }
        });

        notifications.addAll(Constants.notificationService.getAll());
    }



}

