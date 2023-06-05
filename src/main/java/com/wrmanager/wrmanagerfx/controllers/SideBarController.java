package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import com.wrmanager.wrmanagerfx.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SideBarController implements Initializable {
    Boolean CaisseActive=false;



    @FXML
    private MFXButton AchatsButton;

    @FXML
    private FontAwesomeIconView AchatsIcon;

    @FXML
    private MFXButton CommandesButton;

    @FXML
    private FontAwesomeIconView CommandesIcon;

    @FXML
    private MFXButton FournisseursButton;

    @FXML
    private FontAwesomeIconView FournisseursIcon;

    @FXML
    private Button NotificationIcon;


    @FXML
    private MFXButton ParametresButton;

    @FXML
    private MFXButton CaisseButton;

    @FXML
    private FontAwesomeIconView ParametresIcon;

    @FXML
    private MFXButton ProduitsButton;

    @FXML
    private FontAwesomeIconView ProduitsIcon;

    @FXML
    private MFXButton StatistiquesButton;

    @FXML
    private FontAwesomeIconView StatistiquesIcon;

    @FXML
    private MFXButton StockButton;

    @FXML
    private FontAwesomeIconView StockIcon;

    @FXML
    private FontAwesomeIconView CaisseIcon;

    @FXML
    private MFXButton VentesButton;

    @FXML
    private FontAwesomeIconView VentesIcon;

    @FXML
    private StackPane contentArea;

    @FXML
    private AnchorPane logo;

    @FXML
    private GridPane root;
    static GridPane root1;

    static public MFXButton currentPressedBtn ;
    public static Preferences prefs;


    public void VentesButtonOnAction(ActionEvent event){ ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.VentesViewLoader); }
    public void StockButtonOnAction(ActionEvent event){ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.StockViewLoader); }
    public void AchatsButtonOnAction(ActionEvent event){ ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.AchatsViewLoader);}
    public void ProduitsButtonOnAction(ActionEvent event){ ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.ProduitsViewLoader);}
    public void CommandesButtonOnAction(ActionEvent event){ ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.CommandesViewLoader);}
    public void FournisseursButtonOnAction(ActionEvent event){ ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.FournisseursViewLoader);}
    public void StatistiquesButtonOnAction(ActionEvent event){ChangeButtonColor2((MFXButton) event.getSource()); loadContent(Constants.StatistiquesViewLoader);}
    public void ParametresButtonOnAction(ActionEvent event){ ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.ParametresViewLoader);}
    @FXML
    public void CaisseButtonOnAction(ActionEvent event) {
        if (!CaisseActive){
            Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.CaisseView));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 950, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.getStylesheets().add(Main.class.getResource("images/application.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setMinHeight(600);
        stage.setMinWidth(950);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
        CaisseActive=true;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                CaisseActive=!CaisseActive;
            }
        });
    }
    }

    @FXML
    void NotificationIconOnAction(ActionEvent event) {
        try {
            SideBarController.BlurBackground();
            FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(Constants.NotificationsDialogView));
            DialogPane NotificationsDialog= fxmlLoader.load();
            NotificationsDialog.getStylesheets().add(
                    Main.class.getResource("images/dialog.css").toExternalForm());
            NotificationsDialog.setStyle(
                    "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                            .replaceAll("0x","#")
                            +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                            .replaceAll("0x","#")+";");
            NotificationsViewController notificationsViewController=fxmlLoader.getController();
            Dialog<ButtonType> dialog =new Dialog<>();
            dialog.setDialogPane(NotificationsDialog);
            notificationsViewController.setDialog(dialog);
            dialog.initStyle(StageStyle.TRANSPARENT);
            NotificationsDialog.getScene().setFill(Color.rgb(0,0,0,0));
            notificationsViewController.setDialog(dialog);
            dialog.showAndWait();
            SideBarController.DeleteBlurBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void ChangeButtonColor2(MFXButton mfxButton){

        if(currentPressedBtn!=null){
            currentPressedBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-text-fill:#aeb9be;");
            var b =(FontAwesomeIconView)currentPressedBtn.getGraphic();
            b.setFill(Color.rgb(191,191,191,1));
        }
        mfxButton.setStyle("-fx-background-color: "+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                .replaceAll("0x","#")+";-fx-text-fill:\"white\";");
        var a =(FontAwesomeIconView)mfxButton.getGraphic();
        a.setFill(Color.rgb(255,255,255,1));


        currentPressedBtn=mfxButton;
    }

    private void loadContent(Parent loader){

        try {

            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(loader);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void BlurBackground(){
        BoxBlur boxBlur=new BoxBlur();
        boxBlur.setIterations(10);
        boxBlur.setHeight(7);
        boxBlur.setWidth(7);
        root1.setEffect(boxBlur);
    }
    public static void DeleteBlurBackground(){
        root1.setEffect(null);
    }

    public static void ChangeTheme(){

        root1.setStyle(
                "primary:"+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                        .replaceAll("0x","#")
                +";"+"secondary:"+prefs.get("SecondaryColor","#C8E2E0")
                        .replaceAll("0x","#")+";");
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prefs = Preferences.userRoot().node(Main.class.getName());
            Constants.load();
            StockButton.fire();
            root1=root;
            SideBarController.ChangeTheme();
    }
}
