package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Config;

public class DatabaseController {

  private static Connection connection;

  public DatabaseController() {
    connection = getConnection();
  }

  /**
   * Get database connection
   *
   * @return a Connection object
   */
  public static Connection getConnection() {
    try {
      // Set the dataabase connect with the data from the config
      String url =
          "jdbc:mysql://"
              + Config.getDatabaseHost()
              + ":"
              + Config.getDatabasePort()
              + "/"
              + Config.getDatabaseName()
              + "?serverTimezone=CET";
      String user = Config.getDatabaseUsername();
      String password = Config.getDatabasePassword();

      // Register the driver in order to use it
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());

      // create a connection to the database
      connection = DriverManager.getConnection(url, user, password);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return connection;
  }

  /**
   * Do a query in the database
   *
   * @return a ResultSet or Null if Empty
   */
  public ResultSet query(String sql) {

    if (connection == null) {
      connection = getConnection();
    }

    ResultSet rs = null;

    try {
      PreparedStatement stmt = connection.prepareStatement(sql);
      rs = stmt.executeQuery();

      return rs;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return rs;
  }
}
