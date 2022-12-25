package com.example.client.controller.cabinet;

import com.example.client.helper.ChangerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCabinetController extends Cabinet implements Initializable {


    @FXML
    private Button btnNews;

    @FXML
    private Button btnOrder;

    @FXML
    private Button exitBtn;

    @FXML
    private Pane pnNews;

    @FXML
    private Pane pnOrder;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pnNews = loadPane(pnNews, "user/news.fxml");
        pnNews.toFront();
    }

    @Override
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnNews) {
            pnNews = loadPane(pnNews, "user/news.fxml");
            pnNews.toFront();
        } else if (actionEvent.getSource() == btnOrder) {
            pnOrder.toFront();
            pnOrder = loadPane(pnOrder, "user/order.fxml");
        } else if (actionEvent.getSource() == exitBtn) {
            ChangerScene.changeScene(actionEvent, "authorization.fxml");
        }
    }
}
