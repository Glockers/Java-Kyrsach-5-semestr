package com.example.client.helper;

import com.example.client.HelloApplication;
import com.example.client.controller.interfaces.Modal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalLoader<T> {
//    public T loadAddModal(String path, TableView<T> table){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(HelloApplication.class.getResource(path));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(table.getScene().getWindow());
//            stage.showAndWait();
//            Modal controller = loader.getController();
//
//            if (controller.getModalResult()){
//                return (T) controller.getInstanse();
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    };




    public Modal loadAddModal(String path, TableView<T> table){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(table.getScene().getWindow());
            stage.showAndWait();
            Modal controller = loader.getController();

            if (controller.getModalResult()){
                return controller;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    };


    public Modal loadPreparentValueModal(String path, TableView<T> table, T itemSelect){
        try {
            if (itemSelect == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Вы ничего не выбрали");
                alert.setHeaderText(null);
                alert.showAndWait();
                return null;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(table.getScene().getWindow());
            Modal controller = loader.getController();
            controller.setFieilds(itemSelect);
            stage.showAndWait();
            if (controller.getModalResult()){
                return controller;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    };


}
