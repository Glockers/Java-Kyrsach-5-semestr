package org.example.DAO;

import org.example.model.*;
import org.example.types.ProjectStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DAO<Order> {
    private final CustomerContactDAO customerContactDAO = new CustomerContactDAO();

    public List<Order> findAll(User obj) {
        List<Order> list = new ArrayList<Order>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `order_project` WHERE contact_id = ?";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId());
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
    public List<Order> findAll() {
        List<Order> list = new ArrayList<Order>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `order_project`";
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
    public Order getObject(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("order_project.id"));
        order.setProjectName(resultSet.getString("project_name"));
        order.setDescription(resultSet.getString("description"));
        order.setDateEnd(resultSet.getDate("date_end"));
        order.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("status")));
        return order;
    }

    public Order getObjectSubquery(ResultSet resultSet) throws SQLException {
        Order order = getObject(resultSet);
        order.setCustomerContact(customerContactDAO.getObjectSubquery(resultSet));
        return order;
    }


    @Override
    public int write(Order object) throws SQLException {
        Connection con = ConnectionFactory.getConnection();

        try {
            int counter = 0;
            con.setAutoCommit(false);
            String sql = "INSERT INTO customer_contact (id_user, fio, email) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE `fio`=?, `email`=?;\n";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, object.getCustomerContact().getUser().getId());
            ps.setString(2, object.getCustomerContact().getFio());
            ps.setString(3, object.getCustomerContact().getEmail());
            ps.setString(4, object.getCustomerContact().getFio());
            ps.setString(5, object.getCustomerContact().getEmail());
            counter += ps.executeUpdate();
            sql = "INSERT INTO `order_project` (`date_start`, `project_name`, `description`, `contact_id`, `date_end`) SELECT DATE_FORMAT(NOW(), '%Y-%m-%d'),?, ?, ?, ?;\n";
            ps = con.prepareStatement(sql);
            ps.setString(1, object.getProjectName());
            ps.setString(2, object.getDescription());
            ps.setInt(3, object.getCustomerContact().getUser().getId());
            ps.setDate(4, object.getDateEnd());

            counter += ps.executeUpdate();

            if (counter == 2) {
                con.commit();

            } else {
                con.rollback();
            }
            System.out.println(counter);
            return counter;
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            return -1;
        }
    }

    public int delete(Order obj) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM `order_project` WHERE `order_project`.`id` = ?\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Chart> getChart() {
        List<Chart> list = new ArrayList<Chart>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String SQLcommand = "SELECT order_project.date_start AS `date`, COUNT(*) AS `count` FROM `order_project` GROUP BY DATE_FORMAT(`order_project`.`date_start`, '%d.%m.%Y');\n";
            PreparedStatement ps = con.prepareStatement(SQLcommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chart chart = new Chart();
                chart.setCount(rs.getInt("count"));
                chart.setDate(rs.getDate("date"));
                list.add(chart);
            }
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }


    public int editAndCreateProject(Order obj) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        try {
            int counter = 0;
            con.setAutoCommit(false);
            String sqlCommand = "UPDATE `order_project` SET `status` = 'DEVELOP' WHERE `order_project`.`id` = ?;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId());
            counter += ps.executeUpdate();

            String sqlCommand2 = "INSERT INTO `project` ( `date_start`, `order_id`) VALUES (?, ?);";
            ps = con.prepareStatement(sqlCommand2);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, obj.getId());
            counter += ps.executeUpdate();

            if (counter == 2) {
                con.commit();
            } else {
                con.rollback();
            }
            return counter;
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            return -1;
        }

    }

    @Override
    public int update(Order obj) {
        Connection con = ConnectionFactory.getConnection();
        try {
            String sqlCommand = "UPDATE `order_project` SET `status` = 'REJECTED' WHERE `order_project`.`id` = ?;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
