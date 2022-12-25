package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.OrderDAO;
import org.example.DTO.MessageResponse;
import org.example.model.Order;
import org.example.model.User;
import org.example.types.ProjectStatus;
import org.example.types.ResponseType;

public class OrderAction {

    OrderDAO orderDAO = new OrderDAO();
    public String getAll(User obj) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(orderDAO.findAll(obj)));
        return new Gson().toJson(messageResponse);
    }

    public String getAll() {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(orderDAO.findAll()));
        return new Gson().toJson(messageResponse);
    }


    public String createOrder(Order obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = orderDAO.write(obj);
            if (resultExecute == 2) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            }else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception e) {
            messageResponse.setResponseType(ResponseType.ERROR);
        }
        return new Gson().toJson(messageResponse);
    }

    public String deleteOrder(Order obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = orderDAO.delete(obj);
            if (resultExecute == 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            }else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception e) {
            messageResponse.setResponseType(ResponseType.ERROR);
        }
        return new Gson().toJson(messageResponse);
    }


    public String getStatisticOrder(){
        MessageResponse message = new MessageResponse();
        message.setResponseType(ResponseType.SUCSESSFULL);
        String t = new Gson().toJson(orderDAO.getChart());
        message.setPayload(t);
        return new Gson().toJson(message);
    }

    public String edit(Order obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (obj.getProjectStatus().equals(ProjectStatus.DEVELOP)){
                var resultExecute = orderDAO.editAndCreateProject(obj);
                System.out.println(resultExecute);
                if(resultExecute ==2){
                    messageResponse.setResponseType(ResponseType.SUCSESSFULL);
                }else{
                    messageResponse.setResponseType(ResponseType.ERROR);
                }
            }else if(obj.getProjectStatus().equals(ProjectStatus.REJECTED)){
                var resultExecute = orderDAO.update(obj);
                if (resultExecute == 1){
                    messageResponse.setResponseType(ResponseType.SUCSESSFULL);
                }else {
                    messageResponse.setResponseType(ResponseType.ERROR);
                }
            }
           else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageResponse.setResponseType(ResponseType.ERROR);
        }
        return new Gson().toJson(messageResponse);
    }
}
