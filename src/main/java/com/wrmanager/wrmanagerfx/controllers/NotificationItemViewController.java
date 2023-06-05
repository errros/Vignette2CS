package com.wrmanager.wrmanagerfx.controllers;
import com.wrmanager.wrmanagerfx.entities.Notification;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NotificationItemViewController implements Initializable {


    @FXML
    private GridPane itemGridPane;


    @FXML
    private Label DateLbl;

    @FXML
    private Label SubjectLbl;

    private ObjectProperty<Notification> notifcation = new SimpleObjectProperty<>();




    @FXML
    void itemGridPaneOnMouseClicked(MouseEvent event) {

    }

    public Notification getNotifcation() {
        return notifcation.get();
    }

    public ObjectProperty<Notification> notifcationProperty() {
        return notifcation;
    }

    public void setNotifcation(Notification notifcation) {
        this.notifcation.set(notifcation);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        notifcation.addListener(new ChangeListener<Notification>() {
            @Override
            public void changed(ObservableValue<? extends Notification> observableValue, Notification notification, Notification t1) {
                DateLbl.setText(new Date(t1.getCreeLe().getTime()).toString());
                SubjectLbl.setText(t1.getContenu());
            }
        });

    }

}
