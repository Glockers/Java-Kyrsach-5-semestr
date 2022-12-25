package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.CustomerContactDAO;
import org.example.DTO.MessageResponse;
import org.example.model.CustomerContact;
import org.example.model.User;
import org.example.types.ResponseType;

public class CustomerContactAction {

    Gson gson = new Gson();
    CustomerContactDAO customerContactDAO = new CustomerContactDAO();
    public String findMyContact(User user) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            CustomerContact resultuser = customerContactDAO.findOne(user);
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
}
