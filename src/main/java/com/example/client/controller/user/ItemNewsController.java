package com.example.client.controller.user;

import com.example.client.model.News;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemNewsController implements Initializable {

    @FXML
    private Label headerNewsItem;

    @FXML
    private Label messageNewsItem;

    @FXML
    private Label numberNewsItem;

    public void setData(News news) {
        headerNewsItem.setText(news.getHeader());
        messageNewsItem.setText(news.getMessage());
        numberNewsItem.setText(news.getIdPost().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
