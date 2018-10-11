package controllers;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SolrController {

  private static Connection connection;

  public SolrController() {
    connection = getConnection();
  }

  /**
   * Get database connection
   *
   * @return a Connection object
   */
  public static Connection getConnection() {

    String urlString = "http://localhost:8983/solr/bigboxstore";

    HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();

    solr.setParser(new XMLResponseParser());

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
