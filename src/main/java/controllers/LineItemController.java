package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.LineItem;
import model.Product;
import utils.Log;

public class LineItemController {

  private static DatabaseController dbCon;

  public LineItemController() {
    dbCon = new DatabaseController();
  }

  public static ArrayList<LineItem> getLineItemsForOrder(int orderID) {

    if (dbCon == null) {
      dbCon = new DatabaseController();
    }

    String sql = "SELECT * FROM line_item where order_id=" + orderID;

    ResultSet rs = dbCon.query(sql);
    ArrayList<LineItem> items = new ArrayList<>();

    try {
      while (rs.next()) {

        Product product = ProductController.getProduct(rs.getInt("product_id"));

        LineItem lineItem =
            new LineItem(
                rs.getInt("id"),
                product,
                rs.getInt("quantity"),
                rs.getFloat("price"));

        items.add(lineItem);
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return items;
  }

  public static LineItem createLineItem(LineItem lineItem, int orderID) {

    // Write in log that we've reach this step
    Log.writeLog(ProductController.class.getName(), lineItem, "Actually creating a line item in DB", 0);

    // Check for DB Connection
    if (dbCon == null) {
      dbCon = new DatabaseController();
    }

    // Get the ID of the product, since the user will not send it to us.
    lineItem.getProduct().setId(ProductController.getProductBySku(lineItem.getProduct().getSku()).getId());

    // Update the ID of the product

    // Insert the product in the DB
    int lineItemID = dbCon.insert(
        "INSERT INTO line_item(product_id, order_id, price, quantity) VALUES("
            + lineItem.getProduct().getId()
            + ", "
            + orderID
            + ", "
            + lineItem.getPrice()
            + ", "
            + lineItem.getQuantity()
            + ")");

    if (lineItemID != 0) {
      //Update the productid of the product before returning
      lineItem.setId(lineItemID);
    } else{

      // Return null if product has not been inserted into database
      return null;
    }

    // Return product
    return lineItem;
  }
  
}
