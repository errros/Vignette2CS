package com.wrmanager.wrmanagerfx.controllers;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class StatistiquesViewController implements Initializable {
    static ObservableList<PieChart.Data> PieChartData = FXCollections.observableArrayList(
            new PieChart.Data("coca",50),
            new PieChart.Data("eau",15),
            new PieChart.Data("lit",17),
            new PieChart.Data("café",20),
            new PieChart.Data("thé",13)
    );

    @FXML
    private AreaChart<Float, Float> AreaChart;

    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

    @FXML
    private Label Label3;

    @FXML
    private Label Label4;

    @FXML
    private PieChart PieChart;

    @FXML
    private TableView<?> PlusVenduTable;

    @FXML
    private MFXDatePicker datePeremptionDP;

    @FXML
    private MFXDatePicker datePeremptionDP1;

    @FXML
    private AnchorPane icon;

    @FXML
    private AnchorPane icon1;

    @FXML
    private AnchorPane icon2;

    @FXML
    private AnchorPane icon3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetupPieChart();
        SetupAreaChart();
        SetupPlusVenduTable();



    }

    private void SetupPlusVenduTable() {
    }

    private void SetupAreaChart() {
        AreaChart.setTitle("Chart de Bénifice");
        AreaChart.setLegendVisible(false);
        XYChart.Series series1 = new XYChart.Series();
        int max = 10;
        int min = 1;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        for (int i = 0; i < 20; i++) {
            int rand = (int) (Math.random() * range) + min;
            series1.getData().add(new XYChart.Data(String.valueOf(i), rand));
        }


        AreaChart.getData().addAll(series1);
    }

    private void SetupPieChart() {
        PieChart.setTitle("Produits en Stock ");
        PieChart.setData(PieChartData);
        PieChart.setStartAngle(90);
    }
}

