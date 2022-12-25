package com.example.client.controller.manager.modal;

import com.example.client.controller.interfaces.Modal;
import com.example.client.model.Post;
import com.example.client.model.User;
import com.example.client.type.RoleType;
import com.example.client.util.SocketService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserModalController extends Modal<User> implements Initializable {
    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<RoleType> comboBox;
    ObservableList<RoleType> roles = FXCollections.observableArrayList(RoleType.USER, RoleType.EMPLOYEE, RoleType.MANAGER);

    RoleType roleType = RoleType.USER;

    @Override
    public User getInstanse() {
        User user = new User();
        user.setLogin(fieldLogin.getText());
        user.setPassword(passwordField.getText());
        user.setRoleType(comboBox.getSelectionModel().getSelectedItem());
        return user;
    }



    @Override
    public void setFieilds(User itemSelect) {
        fieldLogin.setText(itemSelect.getLogin());
        passwordField.setText(itemSelect.getPassword());
        roleType = itemSelect.getRoleType();
        comboBox.getSelectionModel().select(roleType);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(roles);
        comboBox.getSelectionModel().select(roleType);

    }
}
