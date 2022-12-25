package com.example.client.controller.interfaces;

import com.example.client.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Modal<T> {

    protected boolean modalResult = false;

    public Boolean getModalResult() {
        return modalResult;
    }

    public abstract T getInstanse();

    public void onSaveClick(ActionEvent actionEvent) {
        try {
            getInstanse();
            this.modalResult = true; // ставим результат модального окна на true
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setContentText("Вы ввели неверный тип данных в поле!!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    public void onCancelClick(ActionEvent actionEvent) {
        this.modalResult = false; // ставим результат модального окна на false
        // закрываем окно к которому привязана кнопка
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public abstract void setFieilds(T itemSelect);

}
