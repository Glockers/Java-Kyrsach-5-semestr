package com.example.client.controller.cabinet;

import com.example.client.helper.ChangerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerCabinetController extends Cabinet implements Initializable {


    @FXML
    private Button allowanceBtn;
    @FXML
    private Pane pnAllowance;
    @FXML
    private Button btnMagerUser;

    @FXML
    private Button btnManagerOrder;

    @FXML
    private Button btnManagerPost;

    @FXML
    private Button btnMangerProject;

    @FXML
    private Button exitBtn;

    @FXML
    private Pane pnOrder;

    @FXML
    private Pane pnPost;

    @FXML
    private Pane pnProject;

    @FXML
    private Pane pnUser;

    @FXML
    private Button btnNews;

    @FXML
    private Pane pnNews;

    @FXML
    private Pane pnChart;
    @FXML
    private Button btnChart;

    @FXML
    private Button testBtn;

    @FXML
    private Pane pnTest;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pnUser = loadPane(pnUser, "manager/user.fxml");
        pnUser.toFront();
    }


    @Override
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnMagerUser) {
            pnUser = loadPane(pnUser, "manager/user.fxml");
            pnUser.toFront();
        } else if (actionEvent.getSource() == btnMangerProject) {
            pnProject.toFront();
            pnProject = loadPane(pnProject, "manager/project.fxml");
        } else if (actionEvent.getSource() == btnManagerPost) {
            pnPost = loadPane(pnPost, "manager/post.fxml");
            pnPost.toFront();
        } else if (actionEvent.getSource() == btnNews) {
            pnNews = loadPane(pnNews, "manager/news.fxml");
            pnNews.toFront();
        } else if (actionEvent.getSource() == btnManagerOrder) {
            pnOrder = loadPane(pnOrder, "manager/order.fxml");
            pnOrder.toFront();
        } else if (actionEvent.getSource() == btnChart) {
            pnChart = loadPane(pnChart, "manager/chart.fxml");
            pnChart.toFront();
        } else if (actionEvent.getSource() == allowanceBtn) {
            pnAllowance = loadPane(pnAllowance, "manager/allowance.fxml");
            pnAllowance.toFront();
        }
        else if (actionEvent.getSource() == testBtn) {
            pnTest = loadPane(pnTest, "manager/aptitude.fxml");
            pnTest.toFront();
        }
        else if (actionEvent.getSource() == exitBtn) {
            ChangerScene.changeScene(actionEvent, "authorization.fxml");
        }
    }
}
