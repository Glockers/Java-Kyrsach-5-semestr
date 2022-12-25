package org.example.controller;

import com.google.gson.Gson;
import org.example.DTO.AllowanceDTO;
import org.example.DTO.MessageRequest;
import org.example.DTO.MessageResponse;
import org.example.logic.*;
import org.example.model.*;
import org.example.types.RequestType;
import org.example.types.ResponseType;

public class RequestController {

    UserAction userAction = new UserAction();

    PostAction postAction = new PostAction();
    NewsAction newsAction = new NewsAction();

    ProjectAction projectAction = new ProjectAction();
    OrderAction orderAction = new OrderAction();

    AllowanceLogic allowanceLogic = new AllowanceLogic();

    CalculateLogic calculateLogic = new CalculateLogic();

    AptitudeAction aptitudeAction = new AptitudeAction();
    Gson gson = new Gson();


    CustomerContactAction customerContactAction = new CustomerContactAction();

    public RequestController() {
    }

    public String getResponse(String msg) {
        String response = null;
        MessageRequest messageRequest = (MessageRequest) gson.fromJson(msg, MessageRequest.class);
//        var object = hasPaload(messageRequest);
        System.out.println("Пришло сообщение: " + messageRequest);

        RequestType requestType = messageRequest.getRequestType();
        if (requestType == RequestType.AUTH) {
            var user = gson.fromJson(messageRequest.getPayload(), User.class);
            response = userAction.authorization(user);
        } else if (requestType == RequestType.REG) {
            var user = gson.fromJson(messageRequest.getPayload(), User.class);
            response = userAction.registration(user);
        } else if (requestType == RequestType.ALL_USER) {
            response = userAction.getAllUser();
        } else if (requestType == RequestType.ALL_USER_INFO_EMPLOYEE) {
            response = userAction.getAllEmpl();
        } else if (requestType == RequestType.ALL_POSTS) {
            response = new PostAction().getAllPosts();
        } else if (requestType == RequestType.ADD_EMPLOYEE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Employee.class);
            response = userAction.saveEmployee(obj);
        } else if (requestType == RequestType.ADD_USER) {
            var obj = gson.fromJson(messageRequest.getPayload(), User.class);
            response = userAction.registration(obj);
        } else if (requestType == RequestType.DELETE_USER) {
            var obj = gson.fromJson(messageRequest.getPayload(), User.class);
            response = userAction.deleteUser(obj);
        } else if (requestType == RequestType.DELETE_EMPLOYEE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Employee.class);
            response = userAction.deleteUser(obj);
        } else if (requestType == RequestType.EDIT_USER) {
            var obj = gson.fromJson(messageRequest.getPayload(), User.class);
            response = userAction.editUser(obj);
        } else if (requestType == RequestType.ADD_POST) {
            var obj = gson.fromJson(messageRequest.getPayload(), Post.class);
            response = postAction.savePost(obj);
        } else if (requestType == RequestType.EDIT_POST) {
            var obj = gson.fromJson(messageRequest.getPayload(), Post.class);
            response = postAction.edit(obj);
        } else if (requestType == RequestType.DELETE_POST) {
            var obj = gson.fromJson(messageRequest.getPayload(), Post.class);
            response = postAction.delete(obj);
        } else if (requestType == RequestType.ALL_NEWS) {
            response = newsAction.showAll();
        } else if (requestType == RequestType.ADD_NEWS) {
            var obj = gson.fromJson(messageRequest.getPayload(), News.class);
            response = newsAction.addNews(obj);
        } else if (requestType == RequestType.EDIT_NEWS) {
            var obj = gson.fromJson(messageRequest.getPayload(), News.class);
            response = newsAction.editNews(obj);
        } else if (requestType == RequestType.DELETE_NEWS) {
            var obj = gson.fromJson(messageRequest.getPayload(), News.class);
            response = newsAction.deleteNews(obj);
        } else if (requestType == RequestType.MY_ALL_ORDER) {
            var obj = gson.fromJson(messageRequest.getPayload(), User.class);
            response = orderAction.getAll(obj);
        } else if (requestType == RequestType.GET_MY_CONTACT) {
            var obj = gson.fromJson(messageRequest.getPayload(), User.class);
            response = customerContactAction.findMyContact(obj);
        } else if (requestType == RequestType.CREATE_ORDER) {
            var obj = gson.fromJson(messageRequest.getPayload(), Order.class);
            response = orderAction.createOrder(obj);
        } else if (requestType == RequestType.ALL_ORDER_CHART) {
            response = orderAction.getStatisticOrder();
        } else if (requestType == RequestType.DELETE_ORDER) {
            var obj = gson.fromJson(messageRequest.getPayload(), Order.class);
            response = orderAction.deleteOrder(obj);
        } else if (requestType == RequestType.ALL_ORDER) {
            response = orderAction.getAll();
        } else if (requestType == RequestType.CHANGE_ORDER_STATUS) {
            var obj = gson.fromJson(messageRequest.getPayload(), Order.class);
            response = orderAction.edit(obj);
        } else if (requestType == RequestType.ALL_PROJECT) {
            response = projectAction.findAll();
        } else if (requestType == RequestType.PROJECT_STATUS) {
            var obj = gson.fromJson(messageRequest.getPayload(), Project.class);
            response = projectAction.changeStatusProject(obj);
        } else if (requestType == RequestType.ALL_ALLOWANCE) {
            response = allowanceLogic.getAll();
        } else if (requestType == RequestType.DELETE_ALLOWANCE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Allowance.class);
            response = allowanceLogic.delete(obj);
        } else if (requestType == RequestType.EDIT_ALLOWANCE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Allowance.class);
            response = allowanceLogic.edit(obj);
        } else if (requestType == RequestType.CALCULATE_ONE) {
            var obj = gson.fromJson(messageRequest.getPayload(), AllowanceDTO.class);
            response = calculateLogic.calculateOne(obj);
        } else if (requestType == RequestType.CALCULATE_THREE) {
            var obj = gson.fromJson(messageRequest.getPayload(), User.class);
            response = calculateLogic.calculateThree(obj);
        } else if (requestType == RequestType.CALCULATE_TWO) {
            response = calculateLogic.calculateTwo();
        } else if (requestType == RequestType.ALL_APTITUDE) {
            response = aptitudeAction.getAll();
        } else if (requestType == RequestType.ADD_APTITUDE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Aptitude.class);

            response = aptitudeAction.add(obj);
        } else if (requestType == RequestType.DELETE_APTITUDE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Aptitude.class);
            response = aptitudeAction.delete(obj);
        } else if (requestType == RequestType.EDIT_APTITUDE) {
            var obj = gson.fromJson(messageRequest.getPayload(), Aptitude.class);

            response = aptitudeAction.edit(obj);
        } else {
            System.out.println("Пришло неизвестный запрос от клиента: " + requestType.toString());
            MessageResponse badMessage = new MessageResponse();
            badMessage.setResponseType(ResponseType.ERROR);
            response = gson.toJson(badMessage);
        }
        System.out.println("Отправка сообщения: " + response);
        return response;
    }


}