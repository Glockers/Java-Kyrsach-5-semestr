package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Employee;
import com.example.client.model.Post;
import com.example.client.model.User;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.util.SocketService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PostManagerController implements Initializable {
    @FXML
    private TableColumn<Post, Integer> idPostColumn;

    @FXML
    private TableColumn<Post, String> namePostColumn;

    @FXML
    private TextField namePostionField;

    @FXML
    private TableColumn<Post, Float> okladColumn;

    @FXML
    private TextField okladField;

    @FXML
    private Pane pnUser;

    @FXML
    private TableView<Post> table;

    ObservableList<Post> list = FXCollections.observableArrayList();

    SocketService socketService = new SocketService();

    @FXML
    public void reload() {
        list.clear();
        MessageResponse response = socketService.doGet(RequestType.ALL_POSTS);
        list.addAll(new ParserMessageFactory<Post>().parsePayload(Post.class, response));
    }

    private void initializeUserTable() {
        okladColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRate()));
        namePostColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPosition()));
        idPostColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId_position()));
        table.setItems(list);
    }


    @FXML
    public void onClickAdd() {

        Post post = new Post();
        try {
            post.setPosition(namePostionField.getText());
            post.setRate(Float.valueOf(okladField.getText()));

            if (post.getPosition() != null && post.getRate() != null) {
                MessageResponse messageResponse = socketService.doUpdate(RequestType.ADD_POST, post);
                if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Должность добавлена в базу данных!");
                    alert.setHeaderText(null);
                    alert.show();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Такая должность уже существует. Будьте внимательны!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                namePostionField.clear();
                okladField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Поля не могут быть пустыми!!!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Модальное окно");
            alert.setContentText("Такая должность уже существует. Будьте внимательны!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    @FXML
    public void onClickEdit() {
        Post post = new Post();

        try {

            Post itemSelect = table.getSelectionModel().getSelectedItem();
            if (itemSelect == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Вы не выбрали запись, которую хотите изменить!ы");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            if(okladField.getText().equals("") && namePostionField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Вы же ничего не меняете!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            else {
                post.setId_position(itemSelect.getId_position());
                if(!okladField.getText().equals("")){
                    itemSelect.setRate(Float.valueOf(okladField.getText()));
                }
                if(!namePostionField.getText().equals("")){
                    itemSelect.setPosition(namePostionField.getText());
                }

                MessageResponse messageResponse = socketService.doUpdate(RequestType.EDIT_POST, itemSelect);
                System.out.println(messageResponse);
                if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Информацию о должности была изменена!");
                    alert.setHeaderText(null);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Такая должность уже существует. Будьте внимательны!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                namePostionField.clear();
                okladField.clear();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Модальное окно");
            alert.setContentText("Введен неверный тип данных в поля!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    @FXML
    public void onClickDelete() {
        Post selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали должность, которую хотите удалить!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Вы собираетесь уволить сотрудника. Подтвердите свое действие.");
        alert.setContentText(null);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.DELETE_POST, selectedItem).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            table.getItems().remove(selectedItem);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUserTable();
        reload();
    }
}
