package com.example.client.controller.manager;

import com.example.client.DTO.MessageResponse;
import com.example.client.HelloApplication;
import com.example.client.controller.user.ItemNewsController;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.News;
import com.example.client.type.RequestType;
import com.example.client.type.ResponseType;
import com.example.client.util.SocketService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewsController implements Initializable {

    @FXML
    private VBox containerNews;

    @FXML
    private TextField headerField;

    @FXML
    private Pane pnUser;

    @FXML
    private TextArea textField;

    List<News> listNews = new ArrayList<News>();

    SocketService socketService = new SocketService();


    @FXML
    private TextField editField;

    public void load() {
        onReload();
        for (News news : listNews) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("user/itemNews.fxml"));

            try {
                VBox vBox = fxmlLoader.load();

                ItemNewsController itc = fxmlLoader.getController();
                itc.setData(news);
                containerNews.getChildren().add(vBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }

    @FXML
    public void onAdd() {
        News news = new News();
        try {
            news.setHeader(headerField.getText());
            news.setMessage(textField.getText());

            if (news.getHeader() != null && news.getMessage() != null) {
                MessageResponse messageResponse = socketService.doUpdate(RequestType.ADD_NEWS, news);
                if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Новость добавлена");
                    alert.setHeaderText(null);
                    alert.show();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Ошибка при добавлении новости!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                headerField.clear();
                textField.clear();
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
            alert.setContentText("Ошибка при добавлении новости!");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void onEdit() {
        News news = new News();

        try {

            var itemSelect = editField.getText();
            if (itemSelect == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Вы не выбрали запись, которую хотите изменить!ы");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            if(editField.getText().equals("") && textField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Модальное окно");
                alert.setContentText("Вы же ничего не меняете!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            else {
                news.setIdPost(Integer.valueOf(editField.getText()));
                news.setHeader(headerField.getText());
                news.setMessage(textField.getText());

                MessageResponse messageResponse = socketService.doUpdate(RequestType.EDIT_NEWS, news);
                System.out.println(messageResponse);
                if (messageResponse.getResponseType().equals(ResponseType.SUCSESSFULL)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Информацию о cтатье была изменена!");
                    alert.setHeaderText(null);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Модальное окно");
                    alert.setContentText("Такая должность уже существует. Будьте внимательны!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                editField.clear();
                textField.clear();
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
    public void onDelete() {
        var selectedItem = editField.getText();
        if (selectedItem.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Окно ошибки");
            alert.setContentText("Вы не выбрали номер новости, которую хотите удалить!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Вы собираетесь удалить новость. Подтвердите свое действие.");
        alert.setContentText(null);

        Optional<ButtonType> option = alert.showAndWait();
        News news = new News();
        news.setIdPost(Integer.valueOf(selectedItem));
        if (option.get() == ButtonType.OK && socketService.doUpdate(RequestType.DELETE_NEWS, news).getResponseType().equals(ResponseType.SUCSESSFULL)) {
            onReload();
        }else {
            System.out.println("Произошла ошибка при удалении!");
        }
    }


    void onReload() {
        listNews.clear();
        MessageResponse messageResponse = socketService.doGet(RequestType.ALL_NEWS);
        var listResult = new ParserMessageFactory<News>().parsePayload(News.class, messageResponse);
        if (listResult != null) listNews.addAll(listResult);
    }
}
