package com.example.client.controller.cabinet;

import com.example.client.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class Cabinet {

    public abstract void handleClicks(ActionEvent actionEvent);
    public Pane loadPane(Pane stackPane, String path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HelloApplication.class.getResource(path));

        try {
            Pane newPane = fxmlLoader.load();
            stackPane.getChildren().add(newPane);
            return stackPane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stackPane;
    }


}
