package com.realestate.service;

import com.realestate.model.User;
import java.sql.*;

public class UserService {

    private static final String URL = "jdbc:mysql://localhost:3306/realestate_db";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static boolean register(User user) throws Exception {
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps =
                con.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
        con.close();
        return true;
    }

    public static boolean login(String username, String password) throws Exception {
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement ps =
                con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");

        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();
        con.close();
        return exists;
    }
}