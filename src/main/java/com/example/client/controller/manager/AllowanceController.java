package com.example.client.controller.manager;

import com.example.client.DTO.AllowanceDTO;
import com.example.client.DTO.MessageResponse;
import com.example.client.controller.access_point.AuthorizationController;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AllowanceController implements Initializable {
    @FXML
    private TableColumn<Allowance, Integer> allowanceColumn;


    @FXML
    private TextField idUserField;


    @FXML
    private Label resultLabel;
    @FXML
    private DatePicker date_end;

    @FXML
    private DatePicker date_start;

    @FXML
    private TableColumn<Allowance, String> emailColumn;

    @FXML
    private TableColumn<Allowance, String> fioColumn;

    @FXML
    private TableColumn<Allowance, Integer> idColumn;

    @FXML
    private TableColumn<Allowance, String> loginColumn;

    @FXML
    private Spinner<Integer> spinerStazh;

    @FXML
    private Spinner<Integer> spinnerAllowance;

    @FXML
    private TableView<Allowance> table;


    @FXML
    private Spinner<Integer> spinnerAllowanceEdited;
    ObservableList<Allowance> list = FXCollections.observableArrayList();
    SocketService socketService = new SocketService();


    public void initSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);

        valueFactory.setValue(1);
        valueFactory2.setValue(1);
        spinerStazh.setValueFactory(valueFactory);
        spinnerAllowance.setValueFactory(valueFactory2);
        spinnerAllowanceEdited.setValueFactory(valueFactory2);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpinner();
        reload();
        initTable();
    }

    private void initTable() {
        emailColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser().getEmail()));
        allowanceColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPercent()));
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        loginColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser().getLogin()));
        fioColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getUser().getFio()));
        table.setItems(list);
    }

    @FXML
    public void reload() {
        list.clear();
        MessageResponse response = socketService.doGet(RequestType.ALL_ALLOWANCE);
        list.addAll(new ParserMessageFactory<Allowance>().parsePayload(Allowance.class, response));
    }

    @FXML
    void delete() {

        Allowance selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали никакую заявку!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        if (socketService.doUpdate(RequestType.DELETE_ALLOWANCE, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }

    @FXML
    void edit() {
        Allowance selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали никакую заявку!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        selectedItem.setPercent(spinnerAllowanceEdited.getValue());
        if (socketService.doUpdate(RequestType.EDIT_ALLOWANCE, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }

    @FXML
    void calculateOne() {
        AllowanceDTO allowanceDTO = new AllowanceDTO();
        allowanceDTO.setSizeAllowance(spinnerAllowance.getValue());
        allowanceDTO.setStazh(spinerStazh.getValue());
        MessageResponse messageResponse = socketService.doUpdate(RequestType.CALCULATE_ONE, allowanceDTO);
        if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }

    @FXML
    void calculateTwo() {
        var t = 1;
        MessageResponse messageResponse = socketService.doUpdate(RequestType.CALCULATE_TWO, t);
        Float res =  new ParserMessageFactory<Float>().parsePayloadOne(Float.class, messageResponse);
        Alert alert =  new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Резульат");
        alert.setContentText("Измение на: "+ res +"%");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    public void calculate3() {
        User user = new User();
        user.setId(Integer.valueOf(idUserField.getText()));
        MessageResponse messageResponse = socketService.doUpdate(RequestType.CALCULATE_THREE, user);
        Float res =  new ParserMessageFactory<Float>().parsePayloadOne(Float.class, messageResponse);
        resultLabel.setText(res.toString());
    }
}
