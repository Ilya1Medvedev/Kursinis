module com.example.pirmasnamudarbas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires spring.web;
    requires spring.context;
    requires spring.core;


    opens com.example.pirmasnamudarbas to javafx.fxml;
    exports com.example.pirmasnamudarbas;
    exports com.example.pirmasnamudarbas.controllers;
    opens com.example.pirmasnamudarbas.controllers to javafx.fxml;
}