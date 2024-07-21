package com.accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        // getting the keyword from frontend
        Connection connection = Databaseconnection.getConnection();

        try {
            //store the query of the user
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?,?);");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
            // getting result after running the ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select PageTitle,PageLink,(length(lower(PageText)) - length(replace(lower(PageText),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "') as countaccurance from pages order by countaccurance desc limit 30;");
            // transferring values from resultSet to results arrayList
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("PageTitle"));
                searchResult.setLink(resultSet.getString("PageLink"));
                results.add(searchResult);

            }
            for(SearchResult result: results)
            {
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }
            request.setAttribute("results",results);

            request.getRequestDispatcher("search.jsp").forward(request,response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();


        }
        catch (SQLException | ServletException sqlException){
            sqlException.printStackTrace();
        }
    }
}
