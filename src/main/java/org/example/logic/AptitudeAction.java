package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.AptitudeDAO;
import org.example.DTO.MessageResponse;
import org.example.model.Aptitude;
import org.example.types.ResponseType;

public class AptitudeAction {
    AptitudeDAO aptitudeDAO = new AptitudeDAO();
    public String getAll(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(aptitudeDAO.findAll()));
        return new Gson().toJson(messageResponse);
    }

    public String add(Aptitude obj) {

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        if(aptitudeDAO.add(obj)== 1){
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        }else {
            messageResponse.setResponseType(ResponseType.BAD_REQUEST);
        }
        return new Gson().toJson(messageResponse);
    }

    public String delete(Aptitude obj) {
        MessageResponse messageResponse = new MessageResponse();
        if(aptitudeDAO.delete(obj)== 1){
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        }else {
            messageResponse.setResponseType(ResponseType.BAD_REQUEST);
        }
        return new Gson().toJson(messageResponse);

    }

    public String edit(Aptitude obj) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        if(aptitudeDAO.edit(obj)== 1){
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        }else {
            messageResponse.setResponseType(ResponseType.BAD_REQUEST);
        }
        return new Gson().toJson(messageResponse);
    }
}
