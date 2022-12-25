package org.example.DAO;

import org.example.model.Allowance;
import org.example.model.Aptitude;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AptitudeDAO {

    private final PostDAO postDAO = new PostDAO();

    public List<Aptitude> findAll() {
        List<Aptitude> list = new ArrayList<Aptitude>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `aptitude` LEFT JOIN post on aptitude.id_post = post.id_post;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aptitude aptitude = new Aptitude();
                aptitude.setDate(rs.getDate("date_start"));
                aptitude.setId(rs.getInt("aptitude.id"));
                aptitude.setName_test(rs.getString("aptitude_name"));
                aptitude.setSize_allowance(rs.getInt("allowance"));
                aptitude.setPost(postDAO.getObject(rs));
                list.add(aptitude);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int delete(Aptitude obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM aptitude WHERE `aptitude`.`id` = ?";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int add(Aptitude obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "INSERT INTO `aptitude` ( `aptitude_name`, `id_post`, `allowance`, `date_start`) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setString(1, obj.getName_test());
            ps.setInt(2, obj.getPost().getId_position());
            ps.setInt(3, obj.getSize_allowance());
            ps.setDate(4, obj.getDate());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int edit(Aptitude obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "UPDATE `aptitude` SET `allowance` = ? WHERE `aptitude`.`id` = ?;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getSize_allowance());
            ps.setInt(2, obj.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
