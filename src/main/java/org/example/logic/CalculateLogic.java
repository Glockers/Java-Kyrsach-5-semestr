package org.example.logic;

import com.google.gson.Gson;
import org.example.DAO.AllowanceDAO;
import org.example.DAO.CalculateDAO;
import org.example.DAO.UserDAO;
import org.example.DTO.AllowanceDTO;
import org.example.DTO.MessageResponse;
import org.example.model.Allowance;
import org.example.model.Employee;
import org.example.model.User;
import org.example.types.ResponseType;

public class CalculateLogic {
    CalculateDAO calculateDAO = new CalculateDAO();

    AllowanceDAO allowanceDAO = new AllowanceDAO();

    public String calculateOne(AllowanceDTO obj) {
        MessageResponse messageResponse = new MessageResponse();

        obj.setStazh(obj.getStazh() * 364);
        var res = calculateDAO.findAllOne(obj);

        Integer countBefore = res.size();
        Integer countAfter = 0;
        for (Employee user : res) {
            Allowance allowance = new Allowance();
            allowance.setPercent(obj.getSizeAllowance());
            allowance.setUser(user.getUserDetail());
            var resultSave = allowanceDAO.write(allowance);
            if (resultSave != 1) {
                messageResponse.setResponseType(ResponseType.ERROR);
                break;
            }
            countAfter += 1;
        }
        if (countBefore == countAfter) {
            messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        }
        return new Gson().toJson(messageResponse);
    }

    public String calculateThree(User obj) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        var salary = calculateDAO.findPost(obj);
        var list = calculateDAO.findAllAllows(obj);
        Float newSalary = (float) salary;
        System.out.println(newSalary);
        System.out.println(list);

        for (Allowance i : list) {
            newSalary = newSalary + (float)(i.getPercent())/100*newSalary ;
        }
        System.out.println(newSalary);

        messageResponse.setPayload(new Gson().toJson(newSalary));
        return new Gson().toJson(messageResponse);
    }

    UserDAO userDAO = new UserDAO();
    public String calculateTwo() {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setResponseType(ResponseType.SUCSESSFULL);
        var employes = userDAO.findAllEmpl();
        Float sum = 0F;
        Float oldSalary = 0F;
        for (Employee empl: employes) {
            var salary = calculateDAO.findPost(empl.getUserDetail());
            var list = calculateDAO.findAllAllows(empl.getUserDetail());
            Float newSalary = (float) salary;
            oldSalary = (float) salary;
            for (Allowance i : list) {
                newSalary = newSalary + (float)(i.getPercent())/100*newSalary ;
            }
            sum+=newSalary;
        }

        messageResponse.setPayload(new Gson().toJson(oldSalary *sum/100));
        return new Gson().toJson(messageResponse);
    }
}
