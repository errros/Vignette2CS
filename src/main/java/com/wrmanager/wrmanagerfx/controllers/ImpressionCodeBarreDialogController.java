package com.wrmanager.wrmanagerfx.controllers;

import com.wrmanager.wrmanagerfx.entities.Produit;
import de.jonato.jfxc.controls.barcode.BarcodeView;
import de.jonato.jfxc.controls.barcode.core.BarcodeEncoding;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Button;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ImpressionCodeBarreDialogController implements Initializable {

    private Produit passedProduit;

    Dialog<ButtonType> dialog;

    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    @FXML
    private DialogPane total;

    @FXML
    private MFXButton AnnulerButton;

    @FXML
    private Label Cod;

    @FXML
    private MFXCheckbox CodeBarreCheckBox;

    @FXML
    private MFXCheckbox DateCheckBox;

    @FXML
    private Label DateLbl;

    @FXML
    private AnchorPane ImageContainerPane;

    @FXML
    private MFXButton ImprimerButton;

    @FXML
    private MFXCheckbox NomCheckBox;

    @FXML
    private Label NomLbl;

    @FXML
    private MFXCheckbox PrixCheckBox;

    @FXML
    private Label PrixLbl;

    @FXML
    private TextField QtyTfd;

    @FXML
    private HBox buttonsHb;

    @FXML
    private Label titleLabel;


    @FXML
    private Pane codeBarrePane;

    BarcodeView barcodeView;

    @FXML
    void CodeBarreCheckBoxOnAction(ActionEvent event) {

        barcodeView.setVisible(CodeBarreCheckBox.isSelected());

    }

    void displayCodeBarre(){

       barcodeView = new BarcodeView(BarcodeEncoding.EAN13,passedProduit.getCodeBarre().substring(0,12));
        barcodeView.setFitHeight(codeBarrePane.getMaxHeight());
        barcodeView.setFitWidth(codeBarrePane.getMaxWidth());


        codeBarrePane.getChildren().add(barcodeView);

    }

    @FXML
    void DateCheckBoxOnAction(ActionEvent event) {
        if (DateCheckBox.isSelected()) {
            DateLbl.setVisible(true);
        }
        else{DateLbl.setVisible(false);}
    }
    @FXML
    void NomCheckBoxOnAction(ActionEvent event) {
        if (NomCheckBox.isSelected()) {
            NomLbl.setVisible(true);
        }
        else{NomLbl.setVisible(false);}
    }

    @FXML
    void PrixCheckBoxOnAction(ActionEvent event) {
        if (PrixCheckBox.isSelected()) {
            PrixLbl.setVisible(true);
        }
        else{PrixLbl.setVisible(false);}
    }


    public Produit getPassedProduit() {
        return passedProduit;
    }

    public void setPassedProduit(Produit passedProduit) {
        this.passedProduit = passedProduit;
        displayCodeBarre();


    }



    @FXML
    void AnnulerButtonOnAction(ActionEvent event) {
        dialog.setResult(ButtonType.CLOSE);
        dialog.close();
    }

    @FXML
    void ImprimerButtonOnAction(ActionEvent event) {

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(ImprimerButton.getScene().getWindow())) {

            // Print the node
            job.getJobSettings().setJobName("CodeBarre");
             var layout = job.getJobSettings().getPageLayout();

             ImageContainerPane.setMaxHeight(50);
             ImageContainerPane.setMaxWidth(100);
            boolean printed2 = job.printPage(ImageContainerPane);




            job.endJob();
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QtyTfd.setText("1");
    }
}

