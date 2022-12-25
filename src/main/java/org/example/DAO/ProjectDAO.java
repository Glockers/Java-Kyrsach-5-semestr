package org.example.DAO;

import org.example.model.Post;
import org.example.model.Project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements DAO<Project>{
    private final OrderDAO orderDAO= new OrderDAO();

    @Override
    public List<Project> findAll() {
        List<Project> list = new ArrayList<Project>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `project` LEFT JOIN order_project ON project.order_id = order_project.id LEFT JOIN customer_contact on customer_contact.id_user = order_project.contact_id LEFT JOIN user on user.id = customer_contact.id_user;\n";
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
    public int write(Project obj) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Project obj) {
        return 0;
    }



    @Override
    public Project getObject(ResultSet resultSet) throws SQLException {
        Project obj = new Project();
        obj.setId(resultSet.getInt("project.id"));
        obj.setFactDate(resultSet.getDate("project.fact_date"));
        obj.setDateStart(resultSet.getDate("project.date_start"));
        obj.setOrder(orderDAO.getObjectSubquery(resultSet));
        return obj;
    }

    @Override
    public int update(Project object) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        int counter = 0;

        try {
            con.setAutoCommit(false);

            String sql2 = "UPDATE `order_project` SET `status` = 'DONE' WHERE `order_project`.`id` = ?;\n";
            PreparedStatement  ps = con.prepareStatement(sql2);
            System.out.println(object.getId());
            ps.setInt(1, object.getOrder().getId());
            ps.executeUpdate();
            counter += ps.getUpdateCount();

            System.out.println(counter);

            String sql = "UPDATE `project` SET `fact_date` = ? WHERE `project`.`id` = ?;\n";
            ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, object.getId());
            ps.executeUpdate();
            counter += ps.getUpdateCount();

            if(counter == 2){
                con.commit();
            }else {
                con.rollback();
            }
            System.out.println(counter);
            return counter;
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
            return -1;
        }
    }

    public int rejectProject(Project object) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        int counter = 0;

        try {
            con.setAutoCommit(false);
            String sql = "DELETE FROM project WHERE `project`.`id` = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, object.getId());
            counter += ps.executeUpdate();

            sql = "UPDATE `order_project` SET `status` = 'REJECTED' WHERE `order_project`.`id` = ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, object.getOrder().getId());
            counter += ps.executeUpdate();
            if(counter == 2){
                con.commit();
            }else {
                con.rollback();
            }
            return counter;
        }catch (Exception e){
            con.rollback();
            e.printStackTrace();
            return -1;
        }
    }
}
