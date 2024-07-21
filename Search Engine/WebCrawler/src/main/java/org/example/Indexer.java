package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Indexer {
    static Connection connection = null;
    // select important elements of documents
    Indexer(Document document,String url){
        String title = document.title();
        String link = url;
        String text = document.text();
        //save these elements to database
        try{
            connection = Databaseconnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into pages values(?,?,?)");
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,link);
            preparedStatement.setString(3,text);
            preparedStatement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
