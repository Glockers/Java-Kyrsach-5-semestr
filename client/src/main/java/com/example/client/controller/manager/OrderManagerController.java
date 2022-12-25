package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.controller.access_point.AuthorizationController;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.CustomerContact;
import com.example.client.model.Order;
import com.example.client.model.Post;
import com.example.client.model.User;
import com.example.client.type.ProjectStatus;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.util.SocketService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderManagerController implements Initializable {
    @FXML
    private TableColumn<Order, Date> dateColumn;

    @FXML
    private TableColumn<Order, String> descriptionColumn;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> projectNameColumn;

    @FXML
    private TableColumn<Order, ProjectStatus> statusColumn;

    @FXML
    private TableView<Order> table;

    ObservableList<Order> list = FXCollections.observableArrayList();

    SocketService socketService = new SocketService();

    private void initTable() {
        dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDateEnd()));
        descriptionColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDescription()));
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        statusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getProjectStatus()));
        projectNameColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getProjectName()));
        table.setItems(list);
    }

    @FXML
    public void reload() {
        list.clear();
        MessageResponse response = socketService.doGet(RequestType.ALL_ORDER, AuthorizationController.SESSION_USER);
        list.addAll(new ParserMessageFactory<Order>().parsePayload(Order.class, response));
    }

    @FXML
    public void onAdd() {
        Order selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали никакую заявку!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("После одобрения заявки - начнется разработка проекта. Подтвердите свое действие.");
        alert.setContentText(null);
        Optional<ButtonType> option = alert.showAndWait();
        selectedItem.setProjectStatus(ProjectStatus.DEVELOP);
        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.CHANGE_ORDER_STATUS, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }

    @FXML
    public void onReject() {
        Order selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали никакую заявку!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Вы собираетесь отменить заявку. Подтвердите свое действие.");
        alert.setContentText(null);
        Optional<ButtonType> option = alert.showAndWait();
        selectedItem.setProjectStatus(ProjectStatus.REJECTED);
        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.CHANGE_ORDER_STATUS, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload();
        initTable();
    }
}
