package org.example.DAO;

import org.example.model.CustomerContact;
import org.example.model.Post;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerContactDAO implements DAO<CustomerContact> {

    public CustomerContact findOne(User user) {
        CustomerContact customerContact = null;
        try (Connection connnection = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `customer_contact` WHERE `id_user` = ?;\n";
            PreparedStatement ps = connnection.prepareStatement(sqlCommand);
            ps.setInt(1, user.getId());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                customerContact = getObject(resultSet);
            }
            return customerContact;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerContact getObject(ResultSet resultSet) throws SQLException {
        CustomerContact object = new CustomerContact();
        object.setFio(resultSet.getString("fio"));
        object.setEmail(resultSet.getString("email"));
        return object;
    }

    @Override
    public List<CustomerContact> findAll() {
        return null;
    }

    @Override
    public int write(CustomerContact obj) {
        return 0;
    }

    @Override
    public int delete(CustomerContact obj) {
        return 0;
    }

    @Override
    public int update(CustomerContact obj) {
        return 0;
    }

    public CustomerContact getObjectSubquery(ResultSet resultSet) throws SQLException {
        CustomerContact object = this.getObject(resultSet);
        object.setUser(new UserDAO().getObject(resultSet));
        return object;
    }
}
