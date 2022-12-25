package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Order;
import com.example.client.model.Post;
import com.example.client.model.Project;
import com.example.client.type.ProjectStatus;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.util.SocketService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ProjectMangerController implements Initializable {

    @FXML
    private TableColumn<Project, Date> dateEndColumn;

    @FXML
    private TableColumn<Project, Date> dateStartColumn;

    @FXML
    private TableColumn<Project, String> emailColumn;

    @FXML
    private TableColumn<Project, Date> factDateColumn;

    @FXML
    private TableColumn<Project, String> fioColumn;

    @FXML
    private TableColumn<Project, Integer> idProjectColumn;

    @FXML
    private TableColumn<Project, String> projectNameColumn;

    @FXML
    private TableColumn<Project, ProjectStatus> projectStatusColumn;

    @FXML
    private TableView<Project> table;

    ObservableList<Project> list = FXCollections.observableArrayList();

    void initTable(){
        dateEndColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrder().getDateEnd()));
        dateStartColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDateStart()));
        emailColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrder().getCustomerContact().getEmail()));
        idProjectColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrder().getId()));
        factDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFactDate()));
        fioColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrder().getCustomerContact().getFio()));
        projectStatusColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrder().getProjectStatus()));
        projectNameColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getOrder().getProjectName()));
        table.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload();
        initTable();
    }

    public void onClickDelete(ActionEvent actionEvent) {
        Project selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы ничего не выбрали!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Order order = selectedItem.getOrder();
        order.setProjectStatus(ProjectStatus.REJECTED);
        selectedItem.setOrder(order);
        MessageResponse messageResponse = socketService.doUpdate(RequestType.PROJECT_STATUS, selectedItem);
        if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)){
            reload();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы произошла ошибка на сервере!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void onClickDone(){
        Project selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы ничего не выбрали!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Order order = selectedItem.getOrder();
        order.setProjectStatus(ProjectStatus.DONE);
        selectedItem.setOrder(order);
        System.out.println("test: "+selectedItem.getId());
        MessageResponse messageResponse = socketService.doUpdate(RequestType.PROJECT_STATUS, selectedItem);
        if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)){
            reload();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы произошла ошибка на сервере!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    SocketService socketService = new SocketService();
    public void reload() {
        list.clear();
        MessageResponse response = socketService.doGet(RequestType.ALL_PROJECT);
        list.addAll(new ParserMessageFactory<Project>().parsePayload(Project.class, response));
    }
}
