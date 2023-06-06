package com.wrmanager.wrmanagerfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;


import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class CaptureCameraController implements Initializable {

    public Mat frame;
    public Image image;
    public static Thread thread;
    public static Object lock = new Object();
    public VideoCapture video;
    ImageProcessing imageProcessing = new ImageProcessing();
    private Dialog<ButtonType> dialog;

    @FXML
    private ImageView imageView;
    private String scriptPath;


    @FXML
    void CameraButtonOnAction(ActionEvent event) throws InterruptedException {
        thread.stop();
        thread.stop();
        imageView.setImage(image);
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
        thread.stop();

    }

    @FXML
    void repeatOnMouseClicked(MouseEvent event) throws InterruptedException {

        thread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    video = new VideoCapture(0);
                    if (video.isOpened()) System.out.println("it works 22");
                    video.read(frame);
                    Mat processedFrame = imageProcessing.processFrame(frame);
                    image = imageProcessing.matToImage(processedFrame);
                    imageView.setImage(image);
                }
            }
        });

        thread.start();


    }










    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        thread = new Thread(() -> {
            frame=new Mat();
            while (true) {
                synchronized (lock) {
                    video = new VideoCapture(0);
                    if (video.isOpened()) System.out.println("it works");
                    video.read(frame);
                    Mat processedFrame = imageProcessing.processFrame(frame);
                    image = imageProcessing.matToImage(processedFrame);
                    imageView.setImage(image);
                }
            }
        });

        thread.start();


    }


    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }
}
