package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.AllowanceDAO;
import org.example.DTO.MessageResponse;
import org.example.model.Allowance;
import org.example.types.ResponseType;

public class AllowanceLogic {

    final AllowanceDAO allowanceDAO = new AllowanceDAO();
    public String getAll() {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(allowanceDAO.findAll()));
        return new Gson().toJson(messageResponse);
    }

    public String delete(Allowance obj) {
        MessageResponse messageResponse = new MessageResponse();
//        if (obj.getPercent()> 100 || obj.getPercent()<0 ){
//            messageResponse.setResponseType(ResponseType.BAD_REQUEST);
//            return new Gson().toJson(messageResponse);
//        }
        if (allowanceDAO.delete(obj) == 1){
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        }else {
            messageResponse.setResponseType(ResponseType.ERROR);
        }
        return new Gson().toJson(messageResponse);
    }

    public String edit(Allowance obj) {
        MessageResponse messageResponse = new MessageResponse();
        if (allowanceDAO.update(obj) == 1){
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        }else {
            messageResponse.setResponseType(ResponseType.ERROR);
        }
        return new Gson().toJson(messageResponse);
    }


}
