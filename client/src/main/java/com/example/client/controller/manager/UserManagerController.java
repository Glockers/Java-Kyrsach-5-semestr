package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ModalLoader;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Employee;
import com.example.client.model.User;
import com.example.client.model.UserDetail;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.type.RoleType;
import com.example.client.util.SocketService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagerController implements Initializable {
    @FXML
    private TableColumn<Employee, String> emailColumnEmployee;

    @FXML
    private TableColumn<Employee, String> fioColumnEmployee;

    @FXML
    private TableColumn<User, Integer> idColumnUser;

    @FXML
    private TableColumn<Employee, String> loginColumnEmployee;

    @FXML
    private TableColumn<User, String> loginColumnUser;

    @FXML
    private TableColumn<User, String> passwordColumnUser;

    @FXML
    private Pane pnUser;

    @FXML
    private TableColumn<Employee, String> postColumnEmployee;

    @FXML
    private TableColumn<User, String> roleColumnUser;

    @FXML
    TableColumn<Employee, Float> salaryColumn;

    @FXML
    TableColumn<Employee, Date> dateInvateColumn;

    @FXML
    private TableView<Employee> tableEmployee;

    @FXML
    private TableView<User> tableUser;
    ObservableList<User> listUser = FXCollections.observableArrayList();
    ObservableList<Employee> listEmpl = FXCollections.observableArrayList();
    SocketService socketService = new SocketService();

    public void reload() {
        listUser.clear();
        listEmpl.clear();
        MessageResponse response = socketService.doGet(RequestType.ALL_USER);
        listUser.addAll(new ParserMessageFactory<User>().parsePayload(User.class, response));
        MessageResponse response1 = socketService.doGet(RequestType.ALL_USER_INFO_EMPLOYEE);
        listEmpl.addAll(new ParserMessageFactory<Employee>().parsePayload(Employee.class, response1));
    }

    private void initializeUserTable() {
        idColumnUser.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        loginColumnUser.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLogin()));
        roleColumnUser.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRoleType().toString()));
        passwordColumnUser.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPassword()));
        dateInvateColumn.setCellValueFactory(date -> new SimpleObjectProperty<>(date.getValue().getHiring_date()));
        salaryColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPost().getRate()));
        tableUser.setItems(listUser);
    }


    private void initializeEmployeeTable() {
        emailColumnEmployee.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUserDetail().getEmail()));
        fioColumnEmployee.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUserDetail().getFio()));
        postColumnEmployee.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPost().getPosition()));
        loginColumnEmployee.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUserDetail().getLogin()));
        tableEmployee.setItems(listEmpl);
    }


    @FXML
    public void onClickAddEmployee() {
        var modal = new ModalLoader<Employee>().loadAddModal("manager/modal/modalAddEmployee.fxml", tableEmployee);
        if (modal != null) {
            var instance = modal.getInstanse();
            MessageResponse messageResponse = socketService.doUpdate(RequestType.ADD_EMPLOYEE, instance);
            if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                reload();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Сотрудник не был добавлен!");
                alert.setHeaderText("При регисрации сотрудника возникла ошибка!");
                alert.showAndWait();
            }
        }
    }


    @FXML
    public void onClickAddUser(ActionEvent actionEvent) {

        var modal = new ModalLoader<User>().loadAddModal("manager/modal/modalAddUser.fxml", tableUser);
        if (modal != null) {
            var instance = modal.getInstanse();
            MessageResponse messageResponse = socketService.doUpdate(RequestType.ADD_USER, instance);
            if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                reload();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Сотрудник не был изменен!");
                alert.setHeaderText("При регисрации сотрудника возникла ошибка!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onClickEditUser(ActionEvent actionEvent) {
        User itemSelect = tableUser.getSelectionModel().getSelectedItem();

        var modal = new ModalLoader<User>().loadPreparentValueModal("manager/modal/modalAddUser.fxml", tableUser, itemSelect);
        if (modal != null) {
            var instance = (User) modal.getInstanse();
            instance.setId(itemSelect.getId());
            MessageResponse messageResponse = socketService.doUpdate(RequestType.EDIT_USER, instance);
            if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                reload();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Сотрудник не был добавлен!");
                alert.setHeaderText("При изменении сотрудника возникла ошибка!");
                alert.showAndWait();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUserTable();
        initializeEmployeeTable();
        reload();
    }


    @FXML
    public void onClickDellUser(ActionEvent actionEvent) {
        User selectedItem = tableUser.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали пользователя, которого хотите удалить!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Вы собираетесь удалить пользователя?");
        alert.setContentText(null);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.DELETE_USER, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            tableUser.getItems().remove(selectedItem);
        }
    }

    @FXML
    public void onClickDeleteEmployee(ActionEvent actionEvent) {
        Employee selectedItem = tableEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали сотрудника, которого хотите удалить!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Вы собираетесь уволить сотрудника. Подтвердите свое действие.");
        alert.setContentText(null);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.DELETE_EMPLOYEE, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            tableUser.getItems().remove(selectedItem);
        }
    }
}
