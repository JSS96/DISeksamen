package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Address;
import utils.Log;

public class AddressController {

  private static DatabaseController dbCon;

  public AddressController() {
    dbCon = new DatabaseController();
  }

  public static Address getAddress(int id) {

    if (dbCon == null) {
      dbCon = new DatabaseController();
    }

    String sql = "SELECT * FROM address where id=" + id;

    ResultSet rs = dbCon.query(sql);
    Address address = null;

    try {
      if (rs.next()) {
        address =
            new Address(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("street_address"),
                rs.getString("city"),
                rs.getString("zipcode")
                );

        return address;
      } else {
        System.out.println("No address found");
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return address;
  }

  public static Address createAddress(Address address) {

    // Write in log that we've reach this step
    Log.writeLog(ProductController.class.getName(), address, "Actually creating a line item in DB", 0);

    // Check for DB Connection
    if (dbCon == null) {
      dbCon = new DatabaseController();
    }

    // Insert the product in the DB
    int addressID = dbCon.insert(
        "INSERT INTO address(name, city, zipcode, street_address) VALUES('"
            + address.getName()
            + "', '"
            + address.getCity()
            + "', '"
            + address.getZipCode()
            + "', '"
            + address.getStreetAddress()
            + "')");

    if (addressID != 0) {
      //Update the productid of the product before returning
      address.setId(addressID);
    } else{
      // Return null if product has not been inserted into database
      return null;
    }

    // Return product
    return address;
  }
  
}
