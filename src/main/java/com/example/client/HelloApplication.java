package com.example.client;

import com.example.client.helper.ChangerScene;
import com.example.client.util.SocketDevice;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SocketDevice.getInstance();
        stage = ChangerScene.changeScene(stage, "authorization.fxml", "Авторизация");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}