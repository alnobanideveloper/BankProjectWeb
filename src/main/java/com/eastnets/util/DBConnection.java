package com.eastnets.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private static String url;
    private static String user;
    private static String password;

    private DBConnection() {
        System.out.println("Loaded properties from: " + DBConnection.class.getClassLoader().getResource("application.properties"));
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties");
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =  DriverManager.getConnection(url, user, password);
            if(connection == null){
                System.out.println("Connection Failed! Check output console , connection is null");
                return null;
            }else {
                return connection;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get DB connection", e);
        }
    }
}

