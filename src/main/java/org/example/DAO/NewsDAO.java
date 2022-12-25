package org.example.DAO;

import org.example.model.News;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO implements DAO<News>{
    public List<News> findAll() {
        List<News> list = new ArrayList<News>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `news`";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getObject(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public News getObject(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setHeader(resultSet.getString("header"));
        news.setIdPost(resultSet.getInt("id"));
        news.setMessage(resultSet.getString("text"));
        return news;
    }

    @Override
    public int write(News obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "INSERT INTO `news` (`header`, `text`) VALUES (?, ?);\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setString(1, obj.getHeader());
            ps.setString(2, obj.getMessage());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public int update(News obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "UPDATE `news` SET `text` = ?, header = ? WHERE `news`.`id` = ?;";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setString(1, obj.getMessage());
            ps.setString(2, obj.getHeader());
            ps.setInt(3, obj.getIdPost());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public int delete(News obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM `news` WHERE `news`.`id` = ?\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getIdPost());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
