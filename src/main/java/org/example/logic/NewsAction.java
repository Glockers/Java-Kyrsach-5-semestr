package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.NewsDAO;
import org.example.DTO.MessageResponse;
import org.example.model.News;
import org.example.types.ResponseType;

public class NewsAction {

    NewsDAO newsDAO = new NewsDAO();
    public String showAll(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(newsDAO.findAll()));
        return new Gson().toJson(messageResponse);
    }

    public String addNews(News obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = newsDAO.write(obj);
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

    public String editNews(News obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = newsDAO.update(obj);
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

    public String deleteNews(News obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = newsDAO.delete(obj);
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
}
