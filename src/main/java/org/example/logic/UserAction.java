package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.UserDAO;
import org.example.DTO.MessageResponse;
import org.example.model.Employee;
import org.example.model.User;
import org.example.model.UserDetail;
import org.example.types.ResponseType;
import org.example.types.RoleType;

public class UserAction {
    Gson gson = new Gson();

    UserDAO userDAO = new UserDAO();



    public String authorization(User user) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            User resultuser = userDAO.findOne(user);
            if (resultuser != null) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
                messageResponse.setPayload(gson.toJson(resultuser));
            } else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }


    public String registration(User user) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            User resultuser = userDAO.findOne(user);
            System.out.println(resultuser);
            if (resultuser == null && userDAO.write(user) >= 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
                messageResponse.setPayload(gson.toJson(resultuser));
            } else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }

        return gson.toJson(messageResponse);
    }

    public String getAllEmpl() {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultList = userDAO.findAllEmpl();
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            messageResponse.setPayload(gson.toJson(resultList));
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }


    public String getAllUser() {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultUser = userDAO.findAll();
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            messageResponse.setPayload(gson.toJson(resultUser));
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }


    public String saveEmployee(Employee obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            UserDetail userDetail = obj.getUserDetail();
            userDetail.setRoleType(RoleType.EMPLOYEE);
            obj.setUserDetail(userDetail);
            userDAO.saveEmployee(obj);
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }

    public String deleteUser(User user) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = userDAO.delete(user);
            if (resultExecute == 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            } else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }

    public String deleteUser(Employee employee) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = userDAO.delete(employee);
            if (resultExecute == 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            } else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }

    public String editUser(User obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = userDAO.update(obj);
            if (resultExecute == 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            } else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);
            exception.printStackTrace();
        }
        return gson.toJson(messageResponse);
    }
}