package org.example.DAO;

import org.example.model.Employee;
import org.example.model.Post;
import org.example.model.User;
import org.example.model.UserDetail;
import org.example.types.RoleType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {

    public User findOne(User user) {
        User resultUser = null;
        try (Connection connnection = ConnectionFactory.getConnection()) {
            String sqlCommand = "select * from user where login=?";
            PreparedStatement ps = connnection.prepareStatement(sqlCommand);
            ps.setString(1, user.getLogin());
            ResultSet resultSet = ps.executeQuery();
            resultUser = getResult(resultSet, new User());
            System.out.println(resultUser);
        } catch (SQLException e) {
            resultUser = null;
            throw new RuntimeException(e);
        }

        return resultUser;
    }


    @Override
    public int write(User user) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "INSERT INTO `user` (`login`, `password`, role) VALUES (?,? , ?);";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            if(user.getRoleType() !=null){
                ps.setString(3, user.getRoleType().toString());
            }else {
                ps.setString(3, RoleType.USER.toString());
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int delete(User user){
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM `user` WHERE `user`.`id` = ?";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, user.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(Employee employee){
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "DELETE FROM `user` WHERE `user`.`id` = ?";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setInt(1, employee.getUserDetail().getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int saveEmployee(Employee object) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        Savepoint savepointOne = con.setSavepoint("SavepointOne");
        System.out.println(object.getUserDetail());
        System.out.println(object.getPost());

        try {
            con.setAutoCommit(false);
            String sql = "INSERT INTO `user` (`login`, `password`, `role`) VALUES (?, ?, ?);\n";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, object.getUserDetail().getLogin());
            ps.setString(2, object.getUserDetail().getPassword());
            ps.setString(3, object.getUserDetail().getRoleType().toString());
            ps.executeUpdate();
            sql = "INSERT INTO `user_detail` (`tabel`, `fio`, `email`) SELECT LAST_INSERT_ID() as last_insert_id, ?, ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, object.getUserDetail().getFio());
            ps.setString(2, object.getUserDetail().getEmail());
            ps.executeUpdate();

            sql = "INSERT INTO `employee` (`id_user`, `id_post`, `hiring_date`) SELECT LAST_INSERT_ID(), ?, ?;";
            ps = con.prepareStatement(sql);
            ps.setInt(1, object.getPost().getId_position());
            ps.setDate(2, object.getHiring_date());
            ps.executeUpdate();
            con.commit();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback(savepointOne);
            return -1;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT * FROM `user`";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            list = getResult(rs, list);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employee> findAllEmpl() {
        List<Employee> list = new ArrayList<Employee>();
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "SELECT user_detail.fio,\n"+
                    "user.id,"+
                    "\tuser_detail.email,\n" +
                    "    user.login,\n" +
                    "    employee.hiring_date,\n" +
                    "    post.name_position,\n" +
                    "    post.rate\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "FROM `employee` LEFT JOIN post ON employee.id_post = post.id_post LEFT JOIN user_detail ON employee.id_user = user_detail.tabel LEFT JOIN user ON user.id = user_detail.tabel;\n";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Employee object = new Employee();
                UserDetail userDetail = new UserDetail();
                Post post = new Post();
                userDetail.setLogin(resultSet.getString("user_detail.email"));
                userDetail.setEmail(resultSet.getString("email"));
                userDetail.setFio(resultSet.getString("user_detail.fio"));
                post.setPosition(resultSet.getString("name_position"));
                object.setHiring_date(resultSet.getDate("hiring_date"));
                post.setRate(resultSet.getFloat("post.rate"));
                userDetail.setId(resultSet.getInt("user.id"));
                object.setUserDetail(userDetail);
                object.setPost(post);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(User user) {
        try (Connection con = ConnectionFactory.getConnection()) {
            String sqlCommand = "UPDATE `user` SET `password` = ? , login= ? , role = ? WHERE `user`.`id` = ?;";
            PreparedStatement ps = con.prepareStatement(sqlCommand);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getRoleType().toString());
            ps.setInt(4, user.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public User getObject(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRoleType(RoleType.valueOf(resultSet.getString("role")));
        return user;
    }

    public User getResult(ResultSet resultSet, User obj) throws SQLException {
        boolean flag = false;
        if (resultSet.next()) {
            flag = true;
            obj.setId(resultSet.getInt("id"));
            obj.setLogin(resultSet.getString("login"));
            obj.setPassword(resultSet.getString("password"));
            obj.setRoleType(RoleType.valueOf(resultSet.getString("role")));
        }
        if (flag) {
            return obj;
        }
        return null;
    }

    public List<User> getResult(ResultSet resultSet, List<User> obj) throws SQLException {
        boolean flag = false;
        while (resultSet.next()) {
            User user = new User();
            flag = true;
            user.setId(resultSet.getInt("id"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setRoleType(RoleType.valueOf(resultSet.getString("role")));
            obj.add(user);
        }
        if (flag) {
            return obj;
        }
        return null;
    }


    public static UserDetail getResultDetail(ResultSet resultSet) throws SQLException {
        UserDetail userDetail = new UserDetail();
        userDetail.setLogin(resultSet.getString("user_detail.email"));
        userDetail.setEmail(resultSet.getString("email"));
        userDetail.setFio(resultSet.getString("user_detail.fio"));
        userDetail.setId(resultSet.getInt("user_detail.tabel"));
        return userDetail;
    }

    public static Employee getResultEmployee(ResultSet resultSet) throws SQLException {
        Employee object = new Employee();
        object.setHiring_date(resultSet.getDate("employee.hiring_date"));
        return object;
    }




}
