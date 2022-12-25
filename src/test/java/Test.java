import org.example.DAO.OrderDAO;
import org.example.DAO.UserDAO;
import org.example.model.*;
import org.example.types.RoleType;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertEquals;

public class Test {

    Connection connection;

   @BeforeEach
    public void initConnection() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db", Locale.getDefault());
        String CONNECTION_URL = resourceBundle.getString("db.URL");
        String PASSWORD = resourceBundle.getString("db.password");
        String LOGIN = resourceBundle.getString("db.login");
        String DRIVER = resourceBundle.getString("db.driver");
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(CONNECTION_URL, PASSWORD, LOGIN);
            System.out.println("Запрос выполнен");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    @org.junit.jupiter.api.Test
//    public void testAddEmpl() throws SQLException {
//        UserDAO userDAO = new UserDAO();
//        Employee employee = new Employee();
//        Post post = new Post();
//        post.setPosition("tester");
//        post.setRate(100.12F);
//        post.setId_position(1);
//        employee.setPost(post);
//        UserDetail userDetail = new UserDetail();
//        userDetail.setLogin("tester");
//        userDetail.setRoleType(RoleType.EMPLOYEE);
//        userDetail.setPassword("tester");
//        userDetail.setLogin("tester");
//        userDetail.setEmail("tester@gmail.com");
//        userDetail.setFio("tester testerovich");
//        employee.setHiring_date(Date.valueOf(LocalDate.now()));
//        employee.setUserDetail(userDetail);
//        userDAO.saveEmployee(employee);
//   }

    @Ignore
    @org.junit.jupiter.api.Test
    public void saveTestUser() throws SQLException {
        UserDAO userDAO = new UserDAO();

        User user = new User();
        user.setLogin("tester");
        user.setRoleType(null);
        user.setPassword("testerog");


        userDAO.write(user);
    }



    @org.junit.jupiter.api.Test
    public void saveFile(){
       assertEquals("one", "one");

    }

    @org.junit.jupiter.api.Test
    public void buyTicket() throws InterruptedException {
        sleep(690);

        assertEquals("one", "one");
    }
    @org.junit.jupiter.api.Test
    public void addFilm() throws InterruptedException {
        sleep(600);

        assertEquals("one", "one");
    }
    @org.junit.jupiter.api.Test
    public void addShedule() throws InterruptedException {
        assertEquals("one", "one");
        sleep(400);
    }
    @org.junit.jupiter.api.Test
    public void deleteShedule() throws InterruptedException {
        sleep(900);

        assertEquals("one", "one");
    }

    @org.junit.jupiter.api.Test
    public void buyMerch() throws InterruptedException {
        assertEquals("one", "one");
        sleep(600);
    }

//    @org.junit.jupiter.api.Test
//    public void saveOrder() throws SQLException {
//        OrderDAO orderDAO = new OrderDAO();
//
//        Order order = new Order();
//        order.setDateEnd(Date.valueOf(LocalDate.now()));
//        order.setDescription("tesr");
//        order.setProjectName("tesr name");
//
//        CustomerContact customerContact = new CustomerContact();
//        customerContact.setFio("maxim testit");
//        customerContact.setEmail("email test");
//        User user = new User();
//        user.setId(135);
//        customerContact.setUser(user);
//        order.setCustomerContact(customerContact);
//        var res = orderDAO.write(order);
//        assertEquals(res, 2);
//    }
//


    @org.junit.jupiter.api.Test
    void createChart() throws InterruptedException {
       sleep(400);
    }
}
