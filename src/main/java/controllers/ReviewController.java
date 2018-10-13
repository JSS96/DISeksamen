package controllers;

import java.util.ArrayList;
import model.Review;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class ReviewController {

  public static ArrayList<Review> searchByTitle(String title) {

    // We wish to have an empty list for the results
    ArrayList<Review> reviews = new ArrayList<Review>();

    // Do the search in the controller
    SolrDocumentList documents = SolrController.search("title", title);

    for (SolrDocument doc : documents) {

      // Create a new review based on the SolR document
      Review r =
          new Review(
              Integer.parseInt((String) doc.getFirstValue("id")),
              (String) doc.getFirstValue("title"),
              (String) doc.getFirstValue("description"),
              (String) doc.getFirstValue("author"));

      // Add the review to the list
      reviews.add(r);
    }

    // Return the list
    return reviews;
  }


  public static ArrayList<Review> searchByID(int id) {

    // We wish to have an empty list for the results
    ArrayList<Review> reviews = new ArrayList<Review>();

    // Do the search in the controller
    SolrDocumentList documents = SolrController.search("title", Integer.toString(id));

    // Loop through the results, which are documents from SolR
    for (SolrDocument doc : documents) {
      // Create a new review based on the SolR document
      Review r =
          new Review(
              (int) doc.get("id"),
              (String) doc.get("title"),
              (String) doc.get("description"),
              (String) doc.get("author"));

      // Add the review to the list
      reviews.add(r);
    }

    // Return the results
    return reviews;
  }
}
