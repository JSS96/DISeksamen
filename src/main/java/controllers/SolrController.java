package controllers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import utils.Config;



public class SolrController {

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
   * Do a query in the database
   *
   * @return a ResultSet or Null if Empty
   */
  public void query(String sql) {

    SolrQuery query = new SolrQuery();
    query.set("q", "price:599.99");

    try {
      QueryResponse response = connection.query(query);

      SolrDocumentList docList = response.getResults();
      assertEquals(docList.getNumFound(), 1);

      for (SolrDocument doc : docList) {
        assertEquals(doc.getFieldValue("id"), "123456");
        assertEquals(doc.getFieldValue("price"), (Double) 599.99);
      }
    } catch (SolrServerException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
