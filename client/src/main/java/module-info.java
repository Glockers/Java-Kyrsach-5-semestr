open module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.google.gson;
    requires java.sql;
    requires java.base;


    exports com.example.client;
    exports com.example.client.controller;
}