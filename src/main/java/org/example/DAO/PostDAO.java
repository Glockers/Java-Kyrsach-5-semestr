package org.example.DAO;

import org.example.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO implements DAO<Post> {

    @Override
    public List<Post> findAll() {
        List<Post> list = new ArrayList<Post>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `post`";
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
    public Post getObject(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setPosition(resultSet.getString("name_position"));
        post.setId_position(resultSet.getInt("id_post"));
        post.setRate(resultSet.getFloat("rate"));
        return post;
    }

    @Override
    public int write(Post obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "INSERT INTO `post` (`name_position`, `rate`) VALUES ( ?, ?);";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setString(1, obj.getPosition());
            ps.setFloat(2, obj.getRate());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int update(Post obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "UPDATE `post` SET `rate` = ?, name_position = ? WHERE `post`.`id_post` = ?;";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setFloat(1, obj.getRate());
            ps.setString(2, obj.getPosition());
            ps.setInt(3, obj.getId_position());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(Post obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM `post` WHERE `post`.`id_post` = ?\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId_position());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
