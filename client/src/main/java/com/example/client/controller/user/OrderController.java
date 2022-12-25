package com.example.client.controller.user;

import com.example.client.DTO.MessageResponse;
import com.example.client.controller.access_point.AuthorizationController;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.CustomerContact;
import com.example.client.model.Order;
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

public class OrderController implements Initializable {

    @FXML
    private TableColumn<Order, Date> dateColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Order, String> descriptionColumn;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fioField;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> projectNameColumn;

    @FXML
    private TextField projectNameField;

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
    public void onAdd() {
        try {
            Order order = new Order();
            CustomerContact customerContact = new CustomerContact();
            order.setDescription(descriptionField.getText());
            order.setDateEnd(Date.valueOf(datePicker.getValue()));
            order.setProjectName(projectNameField.getText());

            if (fioField.getText().equals("") &&
                    emailField.getText().equals("") &&
                    order.getDescription().equals("") &&
                    order.getProjectName().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Вы оставили незаполненные строки");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                customerContact.setFio(fioField.getText());
                customerContact.setEmail(emailField.getText());
                customerContact.setUser(AuthorizationController.SESSION_USER);
                order.setCustomerContact(customerContact);
                MessageResponse messageResponse = socketService.doUpdate(RequestType.CREATE_ORDER, order);
                if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Вы успешно подали заявку! Ожидайте пока ее проверят менеджеры.");
                    alert.setHeaderText(null);
                    alert.show();
                    reload();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Произошла ошибка!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Модальное окно");
            alert.setContentText("Вы забыли указать дату дедлайна!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void onDelete() {
        Order selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали завяку, которую хотите удалить!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Вы собираетесь отменить завяку. Подтвердите свое действие.");
        alert.setContentText(null);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.DELETE_ORDER, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            table.getItems().remove(selectedItem);
        }
    }


    @FXML
    public void reload() {
        MessageResponse messageResponse = socketService.doGet(RequestType.GET_MY_CONTACT, AuthorizationController.SESSION_USER);

        if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
            CustomerContact customerContact = new ParserMessageFactory<CustomerContact>().parsePayloadOne(CustomerContact.class, messageResponse);
            fioField.setText(customerContact.getFio());
            emailField.setText(customerContact.getEmail());
        }

        list.clear();
        MessageResponse response = socketService.doGet(RequestType.MY_ALL_ORDER, AuthorizationController.SESSION_USER);
        list.addAll(new ParserMessageFactory<Order>().parsePayload(Order.class, response));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reload();
        initTable();
    }
}
