package org.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectionFactory {
    static Connection connection = null;

    private static String CONNECTION_URL = null;
    private static String PASSWORD = null;
    private static String LOGIN = null;
    private static String DRIVER = null;

    private ConnectionFactory(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db", Locale.getDefault());
        CONNECTION_URL = resourceBundle.getString("db.URL");
        PASSWORD = resourceBundle.getString("db.password");
        LOGIN = resourceBundle.getString("db.login");
        DRIVER = resourceBundle.getString("db.driver");

    }

    private static void initConnection(){
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

    public static Connection getConnection() {
        if (connection == null){
            new ConnectionFactory();
        }
        initConnection();
        return connection;
    }

}