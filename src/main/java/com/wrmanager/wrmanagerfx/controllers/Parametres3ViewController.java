package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Parametres3ViewController implements Initializable {

    @FXML
    private TextField AddressMagasinTfd;

    @FXML
    private ImageView LogoImageView;

    @FXML
    private TextField NomMagasinTfd;

    @FXML
    private TextField NumMagasinTfd;

    @FXML
    private TextField RemarqueMagasinTfd;
    private Preferences prefs;

    @FXML
    void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles())
        {event.acceptTransferModes(TransferMode.ANY);}

    }

    @FXML
    void handleDrop(DragEvent event) {
        List<File> files =event.getDragboard().getFiles();
        try {
            SaveLogo(files.get(0));
            Image img=new Image(new FileInputStream(files.get(0)));
            LogoImageView.setImage(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void TelechargerButtonOnAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(LogoImageView.getScene().getWindow());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        SaveLogo(file);
        Image img = null;
        try {
            img = new Image(new FileInputStream(file));
            LogoImageView.setImage(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void SauvgarderButtonOnAction(ActionEvent event) {
        prefs.put("NomMagasin",NomMagasinTfd.getText());
        prefs.put("AddressMagasin",AddressMagasinTfd.getText());
        prefs.put("NumMagasin",NumMagasinTfd.getText());
        prefs.put("RemarqueMagasin",RemarqueMagasinTfd.getText());
        //prefs.put("LogoPath",NumMagasinTfd.getText());


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prefs = Preferences.userRoot().node(Main.class.getName());
        NomMagasinTfd.setText(String.valueOf(prefs.get("NomMagasin", "" )));
        AddressMagasinTfd.setText(String.valueOf(prefs.get("AddressMagasin", "" )));
        NumMagasinTfd.setText(String.valueOf(prefs.get("NumMagasin", "" )));
        RemarqueMagasinTfd.setText(String.valueOf(prefs.get("RemarqueMagasin", "" )));
        Image img = null;
        try {
            img = new Image(new FileInputStream(prefs.get("LogoPath","")));
            LogoImageView.setImage(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void SaveLogo(File file){
        File fileToSave=new File("src/main/resources/com/wrmanager/wrmanagerfx/images/"+file.getName());

        prefs.put("LogoPath",String.valueOf(file.getAbsolutePath()));
    }
}
