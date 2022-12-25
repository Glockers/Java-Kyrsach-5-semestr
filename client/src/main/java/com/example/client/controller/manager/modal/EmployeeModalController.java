package com.example.client.controller.manager.modal;

import com.example.client.DTO.MessageResponse;
import com.example.client.controller.interfaces.Modal;
import com.example.client.helper.ParserMessageFactory;
import com.example.client.model.Employee;
import com.example.client.model.Post;
import com.example.client.model.UserDetail;
import com.example.client.type.RequestType;
import com.example.client.util.SocketService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EmployeeModalController extends Modal<Employee> implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Post> comboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fieldLogin;

    @FXML
    private TextField fioField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button saveBtn;

    ObservableList<Post> posts = FXCollections.observableArrayList();

    Post post = new Post();

    SocketService socketService = new SocketService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MessageResponse messageResponse = socketService.doGet(RequestType.ALL_POSTS);
        posts.addAll(new ParserMessageFactory<Post>().parsePayload(Post.class, messageResponse));
        comboBox.setItems(posts);

        comboBox.getSelectionModel().select(post);

        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Post> call(ListView<Post> p) {

                final ListCell<Post> cell = new ListCell<Post>() {
                    @Override
                    protected void updateItem(Post t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getPosition());
                        } else {
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });


    }

    @Override
    public Employee getInstanse() {
        Employee employee = new Employee();
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail(emailField.getText());
        userDetail.setFio(fioField.getText());
        userDetail.setPassword(passwordField.getText());
        userDetail.setLogin(fieldLogin.getText());
        employee.setHiring_date(Date.valueOf(datePicker.getValue()));
        employee.setPost(comboBox.getSelectionModel().getSelectedItem());
        employee.setUserDetail(userDetail);
        return employee;
    }

    @Override
    public void setFieilds(Employee itemSelect) {
//        emailField.setText(itemSelect.getUserDetail().getEmail());
//        fioField.setText(itemSelect.getUserDetail().getFio());
//        passwordField.setText(itemSelect.getUserDetail().getPassword());
//        fieldLogin.setText(itemSelect.getUserDetail().getLogin());
//        datePicker.setValue(itemSelect.getHiring_date().toLocalDate());
//        post = itemSelect.getPost();
    }
}
