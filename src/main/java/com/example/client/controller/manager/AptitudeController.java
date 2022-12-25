package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Allowance;
import com.example.client.model.Aptitude;
import com.example.client.model.Post;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AptitudeController implements Initializable {


    @FXML
    private TableColumn<Aptitude, Date> dateStartColumn;

    @FXML
    private TableColumn<Aptitude, Integer> idColumn;

    @FXML
    private TableColumn<Aptitude, String> namePostColumn;

    @FXML
    private TableColumn<Aptitude, String> nameTestColumn;

    @FXML
    private TableColumn<Aptitude, Integer> sizeColumn;

    @FXML
    private TableView<Aptitude> table;


    ObservableList<Aptitude> list = FXCollections.observableArrayList();

    SocketService socketService = new SocketService();

    @FXML
    public void reload() {
        list.clear();
        MessageResponse response = socketService.doGet(RequestType.ALL_APTITUDE);
        list.addAll(new ParserMessageFactory<Aptitude>().parsePayload(Aptitude.class, response));
    }

    private void initTable() {
        sizeColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getSize_allowance()));
        namePostColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPost().getPosition()));
        idColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        dateStartColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
        nameTestColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getName_test()));
        table.setItems(list);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        spinnerField.setValueFactory(valueFactory);
        reload();
        initTable();
    }


    @FXML
    private Spinner<Integer> spinnerField;
    @FXML
    private TextField nameAllowanceField;

    @FXML
    private TextField postIdField;

    @FXML
    public void add() {
        try {
            Aptitude aptitude = new Aptitude();
            var name = nameAllowanceField.getText();
            Integer postId = Integer.valueOf(postIdField.getText());
            var size = spinnerField.getValue();
            aptitude.setDate(Date.valueOf(LocalDate.now()));
            aptitude.setName_test(name);
            Post post = new Post();
            post.setId_position(postId);
            aptitude.setPost(post);
            aptitude.setSize_allowance(size);
            if (socketService.doUpdate(RequestType.ADD_APTITUDE, aptitude).getResponseType().equals(ResponseType.SUCSESSFULL)) {
                reload();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setContentText("Вы ввели неверный тип данных в одно из поле!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }


    }

    @FXML
    public void edit() {
        Aptitude selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали никакую заявку!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        selectedItem.setSize_allowance(spinnerField.getValue());
        if (socketService.doUpdate(RequestType.EDIT_APTITUDE, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }

    @FXML
    public void del() {
        Aptitude selectedItem = table.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали никакую заявку!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        if (socketService.doUpdate(RequestType.DELETE_APTITUDE, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            reload();
        }
    }
}
