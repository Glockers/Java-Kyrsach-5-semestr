package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.PostDAO;
import org.example.DTO.MessageResponse;
import org.example.model.Post;
import org.example.types.ResponseType;

public class PostAction {

    PostDAO postDAO = new PostDAO();

    public String getAllPosts() {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(postDAO.findAll()));
        return new Gson().toJson(messageResponse);
    }

    public String savePost(Post obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = postDAO.write(obj);
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

    public String edit(Post obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = postDAO.update(obj);
            if (resultExecute == 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            }else {
                messageResponse.setResponseType(ResponseType.BAD_REQUEST);
            }
        } catch (Exception e) {
            messageResponse.setResponseType(ResponseType.ERROR);
            e.printStackTrace();
        }
        return new Gson().toJson(messageResponse);
    }

    public String delete(Post obj) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            var resultExecute = postDAO.delete(obj);
            if (resultExecute == 1) {
                messageResponse.setResponseType(ResponseType.SUCSESSFULL);
            }else {
                messageResponse.setResponseType(ResponseType.ERROR);
            }
        } catch (Exception e) {
            messageResponse.setResponseType(ResponseType.ERROR);
        }
        return new Gson().toJson(messageResponse);
    }
}
