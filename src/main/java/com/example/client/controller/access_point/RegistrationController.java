package com.example.client.controller.access_point;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ChangerScene;
import com.example.client.model.User;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.util.SocketService;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML
    private Button btnRegister;

    @FXML
    private Button btnReturn;


    @FXML
    private TextField loginField;


    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    SocketService socketService = new SocketService();

    public void registerHandler(ActionEvent actionEvent) {

        User user = new User();
        user.setLogin(loginField.getText());

        if (passwordField.getText().equals(repeatPasswordField.getText())) {
            user.setPassword(repeatPasswordField.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Регистрация");
            alert.setContentText("Пароли не совпадают");
            alert.setHeaderText(null);
            alert.show();
            return;
        }


        MessageResponse messageResponse = socketService.doUpdate(RequestType.REG, user);
        ResponseType responseType = messageResponse.getResponseType();
        System.out.println(responseType);
        if (responseType.equals(ResponseType.SUCSESSFULL)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setContentText("Вы успешно зарегистрировались!");
            alert.setHeaderText(null);
            alert.showAndWait();
            ChangerScene.changeScene(actionEvent, "authorization.fxml", "Авторизация").show();
        } else if (responseType.equals(ResponseType.BAD_REQUEST)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Регистрация");
            alert.setContentText("Пользователь с таким логином уже существует");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Регистрация");
            alert.setContentText("Вы нашли необработанную ошибку");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void returnHandler(ActionEvent actionEvent) {
        ChangerScene.changeScene(actionEvent, "authorization.fxml", "Авторизация").show();
    }
}
