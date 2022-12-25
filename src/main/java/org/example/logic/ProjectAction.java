package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.ProjectDAO;
import org.example.DTO.MessageResponse;
import org.example.model.Project;
import org.example.types.ProjectStatus;
import org.example.types.ResponseType;

public class ProjectAction {
    ProjectDAO projectDAO = new ProjectDAO();

    public String findAll() {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        messageResponse.setPayload(new Gson().toJson(projectDAO.findAll()));
        return new Gson().toJson(messageResponse);
    }

    public String changeStatusProject(Project obj) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (obj.getOrder().getProjectStatus().equals(ProjectStatus.DONE)) {
                if (projectDAO.update(obj) == 2) {
                    messageResponse.setResponseType(ResponseType.SUCSESSFULL);
                } else {
                    messageResponse.setResponseType(ResponseType.ERROR);
                }
            } else if (obj.getOrder().getProjectStatus().equals(ProjectStatus.REJECTED)) {
                if (projectDAO.rejectProject(obj) == 2) {
                    messageResponse.setResponseType(ResponseType.SUCSESSFULL);
                } else {
                    messageResponse.setResponseType(ResponseType.ERROR);
                }
            } else {
                messageResponse.setResponseType(ResponseType.ERROR_CLIENT);
            }
        } catch (Exception exception) {
            messageResponse.setResponseType(ResponseType.ERROR);

        }

        return new Gson().toJson(messageResponse);
    }
}
