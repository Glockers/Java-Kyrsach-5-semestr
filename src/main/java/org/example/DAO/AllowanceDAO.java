package org.example.DAO;

import lombok.Data;
import org.example.model.Allowance;
import org.example.model.UserDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class AllowanceDAO implements DAO<Allowance>{
    UserDetail user;
    Float percent;

    public List<Allowance> findAll() {
        List<Allowance> list = new ArrayList<Allowance>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `allowance` LEFT JOIN user_detail on allowance.user = user_detail.tabel";
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


    public Allowance getObject(ResultSet resultSet) throws SQLException {
        Allowance object = new Allowance();
        object.setPercent(resultSet.getInt("allowance.percent"));
        object.setId(resultSet.getInt("allowance.id"));
        object.setUser(UserDAO.getResultDetail(resultSet));
        return object;
    }

    public int delete(Allowance obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM allowance WHERE `allowance`.`id` = ?";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public int update(Allowance obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "UPDATE `allowance` SET `percent` = ? WHERE `allowance`.`id` = ?;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getPercent());
            ps.setInt(2, obj.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int write(Allowance obj){
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "INSERT INTO `allowance` ( `user`, `percent`) VALUES (?, ?);\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getUser().getId());
            ps.setInt(2, obj.getPercent());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
