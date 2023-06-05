package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import com.wrmanager.wrmanagerfx.entities.Categorie;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Parametres2ViewController implements Initializable {

    @FXML
    private ChoiceBox<?> CategoryChoiceBox;

    @FXML
    private TextField CodeBarreTfd14;

    @FXML
    private TextField JrsAlertTfd;

    @FXML
    private TextField MargeBenefTfd;

    @FXML
    private TextField MargeKgTfd;

    @FXML
    private MFXToggleButton NotificationToggle;

    @FXML
    private TextField QtyAlertTfd;

    @FXML
    private MFXButton ResetButton;

    @FXML
    private MFXButton SauvgarderButton;

    @FXML
    private MFXToggleButton StockNegatifToggle;

    @FXML
    private MFXToggleButton dgValidationToggle;
    @FXML
    private MFXToggleButton dgvalidationBonAchat;
    @FXML
    private ColorPicker PrimaryColor;
    @FXML
    private ColorPicker SecondaryColor;


    private Preferences prefs;

    @FXML
    void ResetButtonOnAction(ActionEvent event) {

    }
    @FXML
    void ResetColorsButtonOnAction(ActionEvent event) {
        prefs.put("PrimaryColor", "rgba(35, 140, 131, 1)");
        prefs.put("SecondaryColor","#C8E2E0");
        SideBarController.ChangeTheme();
    }

    @FXML
    void SauvgarderButtonOnAction(ActionEvent event) {
        prefs.putBoolean("DialogValidation",dgValidationToggle.isSelected());
        prefs.putBoolean("DialogValidationBonAchat",dgvalidationBonAchat.isSelected());
        prefs.putBoolean("Notifications",NotificationToggle.isSelected());
        prefs.putBoolean("StockNegatif",StockNegatifToggle.isSelected());
        prefs.putInt("MargeKg", Integer.parseInt(MargeKgTfd.getText()));
        prefs.putInt("MargeBenef", Integer.parseInt(MargeBenefTfd.getText()));
        prefs.putFloat("QtyAlert", Float.parseFloat(QtyAlertTfd.getText()));
        prefs.putInt("JrsAlert", Integer.parseInt(JrsAlertTfd.getText()));
        prefs.put("PrimaryColor", String.valueOf(PrimaryColor.getValue()));
        prefs.put("SecondaryColor",String.valueOf(SecondaryColor.getValue()));
        SideBarController.ChangeTheme();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prefs = Preferences.userRoot().node(Main.class.getName());
        dgValidationToggle.setSelected(prefs.getBoolean("DialogValidation",true));
        dgvalidationBonAchat.setSelected(prefs.getBoolean("DialogValidationBonAchat",true));
        NotificationToggle.setSelected(prefs.getBoolean("Notifications",true));
        StockNegatifToggle.setSelected(prefs.getBoolean("StockNegatif",true));
        MargeKgTfd.setText(String.valueOf(prefs.getInt("MargeKg",1)));
        MargeBenefTfd.setText(String.valueOf(prefs.getInt("MargeBenef", Constants.DEFAULT_MARGE_CATEGORIE )));
        QtyAlertTfd.setText(String.valueOf(prefs.getFloat("QtyAlert", Constants.DEFAULT_QTY_ALERTE_PRODUIT )));
        JrsAlertTfd.setText(String.valueOf(prefs.getInt("JrsAlert", Constants.DEFAULT_JOURS_ALERTE_CATEGORIE )));
        PrimaryColor.setValue(Color.web(prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")));
        SecondaryColor.setValue(Color.web(prefs.get("SecondaryColor","#C8E2E0")));

    }
}
