package com.example.client.controller.access_point;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ChangerScene;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.User;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.type.RoleType;
import com.example.client.util.SocketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {


    public static User SESSION_USER = null;
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    SocketService socketService = new SocketService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SESSION_USER = new User();
    }

    @FXML
    public void onClickRegistration(ActionEvent actionEvent) {
        ChangerScene.changeScene(actionEvent, "registration.fxml", "Регистрация" ).show();
    }


    @FXML
    public void authHandler(ActionEvent actionEvent) {
        User user = new User();
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        loginField.clear();
        passwordField.clear();

        MessageResponse messageResponse= socketService.doUpdate(RequestType.AUTH, user);

        ResponseType responseType = messageResponse.getResponseType();
        if(responseType.equals(ResponseType.SUCSESSFULL)){
            user = new ParserMessageFactory<User>().parsePayloadOne(User.class, messageResponse);
            Alert alert =  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Авторизация");
            alert.setContentText("Вы успешно авторизовались!");
            alert.setHeaderText(null);
            alert.show();

            chooseCabinet(user, actionEvent);


        }
        else if (responseType.equals(ResponseType.BAD_REQUEST)){
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Авторизация");
            alert.setContentText("Вы ввели неверный логин или пароль!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Авторизация");
            alert.setContentText("Произошла ощибка на сервере!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


    private void chooseCabinet(User user, ActionEvent actionEvent){

        RoleType roleType = user.getRoleType();
        System.out.println(roleType);
        if (roleType.equals(RoleType.USER)){
            ChangerScene.changeScene(actionEvent, "cabinet/UserCabinet.fxml");
        }
        else if (roleType.equals(RoleType.MANAGER)){
            ChangerScene.changeScene(actionEvent, "cabinet/ManagerCabinet.fxml");
        }

        else if (roleType.equals(RoleType.EMPLOYEE)){
//            ChangerScene.changeScene(actionEvent, "adminCabinet.fxml");
        }
        else {
            Alert alert =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Авторизация");
            alert.setContentText("У вас неизвестная роль!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        SESSION_USER.setLogin(user.getLogin());
        SESSION_USER.setId(user.getId());
        SESSION_USER.setRoleType(user.getRoleType());
        SESSION_USER.setPassword(user.getPassword());
    }
}
