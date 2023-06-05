package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.Constants;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static com.wrmanager.wrmanagerfx.controllers.SideBarController.prefs;

public class ParametresViewController implements Initializable {

    @FXML
    private StackPane ParamStackPane;

    @FXML
    private MFXButton param1Button;

    @FXML
    private MFXButton param2Button;

    static public MFXButton currentPressedBtn ;

    @FXML
    void param1ButtonOnAction(ActionEvent event) {
        ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.Parametres1ViewLoader);
    }

    @FXML
    void param2ButtonOnAction(ActionEvent event) {
        ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.Parametres2ViewLoader);
    }
    @FXML
    void param3ButtonOnAction(ActionEvent event) {
        ChangeButtonColor2((MFXButton) event.getSource());loadContent(Constants.Parametres3ViewLoader);
    }

    private void ChangeButtonColor2(MFXButton mfxButton){

        /*
        var a =(FontAwesomeIconView)mfxButton.getGraphic();
        a.setFill(Color.rgb(255,255,255,1));

         */
        if(currentPressedBtn!=null){
            currentPressedBtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-text-fill:#aeb9be;");
            /*
            var b =(FontAwesomeIconView)currentPressedBtn.getGraphic();
            b.setFill(Color.rgb(191,191,191,1));

             */
        }
        mfxButton.setStyle("-fx-background-color: "+prefs.get("PrimaryColor","rgba(35, 140, 131, 1)")
                .replaceAll("0x","#")+";-fx-text-fill:\"white\";");


        currentPressedBtn=mfxButton;
    }

    private void loadContent(Parent loader){

        try {
            System.out.println(loader);
            ParamStackPane.getChildren().removeAll();
            ParamStackPane.getChildren().setAll(loader);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        param1Button.fire();
    }
}
