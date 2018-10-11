package controllers;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import utils.Config;

public final class SolrController {

  private static HttpSolrClient connection;

  public SolrController() {
    connection = getConnection();
  }

  /**
   * Get database connection
   *
   * @return a Connection object
   */
  public static HttpSolrClient getConnection() {

    String urlString =
        "http://"
            + Config.getSolrHost()
            + ":"
            + Config.getSolrPort()
            + "/"
            + Config.getSolrPath()
            + "/"
            + Config.getSolrCore();

    HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();

    solr.setParser(new XMLResponseParser());

    return solr;
  }

  /**
   * Do a query in SolR
   *
   * @return a ResultSet or Null if Empty
   */
  public static SolrDocumentList search(String field, String value) {

    if(connection == null)
      connection = getConnection();

    // Search in Solr base on Field and Value
    SolrQuery query = new SolrQuery();
    query.set("q", field + ":" + value);

    // Create an empty document list
    SolrDocumentList docList = new SolrDocumentList();

    try {
      // Search in Solr
      QueryResponse response = connection.query(query);

      // Get the results
      docList = response.getResults();

    } catch (SolrServerException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return docList;
  }
}
