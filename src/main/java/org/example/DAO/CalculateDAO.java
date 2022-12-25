package org.example.DAO;

import org.example.DTO.AllowanceDTO;
import org.example.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalculateDAO {
    private final PostDAO postDAO = new PostDAO();
    public List<Employee> findAllOne(AllowanceDTO allowanceDTO) {
        List<Employee> list = new ArrayList<Employee>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM employee LEFT JOIN post ON employee.id_post = post.id_post LEFT JOIN user_detail on user_detail.tabel = employee.id_user WHERE (SELECT DATEDIFF (NOW() , hiring_date)) > ?;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, allowanceDTO.getStazh());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = UserDAO.getResultEmployee(rs);
                employee.setUserDetail(UserDAO.getResultDetail(rs));
                employee.setPost(postDAO.getObject(rs));
                list.add(employee);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Allowance> findAllAllows(User user) {
        List<Allowance> list = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT `percent` FROM `allowance` WHERE `user`=?;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Allowance allowance = new Allowance();
                allowance.setPercent(rs.getInt("percent"));
                list.add(allowance);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    public int findPost(User user) {
        int result = 0;
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT rate FROM `employee` LEFT JOIN post on post.id_post = employee.id_post WHERE `id_user` = ?";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("rate");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return  -1;
        }
    }


}
