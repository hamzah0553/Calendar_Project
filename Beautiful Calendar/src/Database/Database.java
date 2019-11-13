package Database;

import java.sql.*;

/**
 * Created by Johnny on 09-05-2017.
 */
public class Database
{
    private static Database database = new Database();
    private Connection connection = null;

    private Database()
    {
        this.connection = getConnection();
    }

    /** creates new connection and return */
    public Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://77.66.47.183:3306/tandbud_project";
            String user = "tandbud_project";
            String password = "hamzah12";
            return DriverManager.getConnection(url, user, password);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());
        }
        return null;
    }

    public static Database getDatabase()
    {
        return database;
    }

    /** Method to return ResultSet using prep. statement */
    public ResultSet returnResultSetDB(PreparedStatement preparedStatement)
    {
        try
        {
            return preparedStatement.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** Method to execute prep. statement */
    public void executeUpdateDB(PreparedStatement preparedStatement)
    {
        try
        {
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
