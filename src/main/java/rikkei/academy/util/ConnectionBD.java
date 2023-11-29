package rikkei.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String DRIVER_JBDC ="com.mysql.cj.jdbc.Driver";
    private static final String USER_JBDC="root";
    private static final String PASSWORD="hoangutck57";
    private static final String URL="jdbc:mysql://localhost:3306/sell_watch";

    public static Connection openConnection(){
        Connection connection = null;
        try {
            Class.forName(DRIVER_JBDC);
            connection = DriverManager.getConnection(URL, USER_JBDC, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void closeConnection(Connection connection) {
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
