package com.accio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databaseconnection {
    static Connection connection= null;

    public static Connection getConnection() {
        if(connection != null)
        {
            return connection;
        }
        String user = "root";
        String pwd = "Avinash@243";
        String db = "searchengine";
        return getConnection(user, pwd, db);
    }
    private static Connection getConnection(String user, String pwd, String db){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        }
        catch (SQLException | ClassNotFoundException sqlException)
        {
            sqlException.printStackTrace();
        }
        return connection;
    }
}
