package com.wrmanager.wrmanagerfx;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;

import java.io.*;

import static com.wrmanager.wrmanagerfx.Constants.notificationService;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {



/*
//         Constants.vendeurDAO.save(Vendeur.builder().role(Role.ADMIN).num("0255555").password("admin").username("admin").build());
*/

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Constants.SideBarView));
        Scene scene = new Scene(fxmlLoader.load(), 950, 700);

        scene.getStylesheets().add(getClass().getResource("images/application.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("images/dialog.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setMinHeight(600);
        stage.setMinWidth(950);
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        Constants.initObservableLists();


        setupTodayNotifications(stage);

    }

    public static void setupTodayNotifications(Stage stage){


        FontAwesomeIconView trush = new FontAwesomeIconView(FontAwesomeIcon.BELL);
        trush.setSize("40");
        trush.setFill(Color.rgb(35, 140, 131));
        stage.getScene().setFill(Color.rgb(0, 0, 0, 0));

        var notifications = notificationService.saveTodayNotifs();

        notifications.forEach(notification ->

                Notifications.create().owner(stage).graphic(trush).title("     ").text(notification.getContenu()).hideAfter(Duration.INDEFINITE).show());




    }


    public static void main(String[] args) {
        launch();
    }

}

