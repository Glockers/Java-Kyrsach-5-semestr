package com.example.client.helper;


import com.example.client.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangerScene {

    public static Stage changeScene(ActionEvent event, String nameResource, String title) {
        try {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(nameResource));
            Parent root = (Parent) loader.load();
            var scene = new Scene(root);
            thisStage.setScene(scene);
            thisStage.setTitle(title);
            return thisStage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static Stage changeScene(ActionEvent event, String nameResource) {
        try {
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(nameResource));
            Parent root = (Parent) loader.load();
            thisStage.setScene(new Scene(root));
            return thisStage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Stage changeScene(Stage thisStage, String nameResource, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(nameResource));
            Parent root = (Parent) loader.load();
            thisStage.setScene(new Scene(root));
            thisStage.setTitle(title);
            return thisStage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
