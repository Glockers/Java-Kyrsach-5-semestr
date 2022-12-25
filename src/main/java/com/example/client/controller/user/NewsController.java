package com.example.client.controller.user;

import com.example.client.DTO.MessageResponse;
import com.example.client.HelloApplication;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Employee;
import com.example.client.model.News;
import com.example.client.type.RequestType;
import com.example.client.util.SocketService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewsController implements Initializable {

    @FXML
    private VBox containerNews;


    SocketService socketService = new SocketService();

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

    List<News> listNews = new ArrayList<News>();


    void onReload() {
        listNews.clear();
        MessageResponse messageResponse = socketService.doGet(RequestType.ALL_NEWS);
        var listResult = new ParserMessageFactory<News>().parsePayload(News.class, messageResponse);
        if (listResult != null) listNews.addAll(listResult);
    }
}
