module com.wrmanager.wrmanagerfx{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.swing;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.xml;
    requires java.persistence;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires static lombok;
    requires MaterialFX;
    requires de.jensd.fx.glyphs.fontawesome;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires org.controlsfx.controls;
    //requires org.apache.poi.poi;
    requires poi;
    requires poi.ooxml;
    requires jfxc;
    requires okapibarcode;
    requires net.synedra.validatorfx;
    requires java.prefs;
    requires jasperreports;


    opens com.wrmanager.wrmanagerfx to javafx.fxml,javafx.base,javafx.controls , org.controlsfx.controls;

    opens com.wrmanager.wrmanagerfx.repositories to  javafx.fxml,javafx.base,javafx.controls;

    opens com.wrmanager.wrmanagerfx.services to javafx.fxml, javafx.base, javafx.controls, jfxc;

    opens com.wrmanager.wrmanagerfx.controllers to javafx.base, javafx.controls, javafx.fxml,org.controlsfx.controls;

    opens com.wrmanager.wrmanagerfx.entities to  javafx.fxml,javafx.base,javafx.controls , org.hibernate.orm.core;

    exports com.wrmanager.wrmanagerfx;
    exports com.wrmanager.wrmanagerfx.repositories;
    exports com.wrmanager.wrmanagerfx.entities;
    exports com.wrmanager.wrmanagerfx.services;
    exports com.wrmanager.wrmanagerfx.models;

    exports com.wrmanager.wrmanagerfx.controllers;

}