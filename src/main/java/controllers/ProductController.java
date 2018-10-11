package controllers;

import model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductController {

    private static DatabaseController dbCon;

    public ProductController(){
        dbCon = new DatabaseController();
    }


    public static Product getProduct(int id){

        if(dbCon == null){
            dbCon = new DatabaseController();
        }

        String sql = "SELECT * FROM product where id=" + id;

        ResultSet rs = dbCon.query(sql);
        Product product = null;

        try {
            if (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("sku"), rs.getFloat("price"), rs.getString("description"));

                return product;
            }else{
                System.out.println("No user found");
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return product;
    }

    /**
     *
     * Get all products in database
     *
     * @return
     */
    public static ArrayList<Product> getProducts(){

        if(dbCon == null){
            dbCon = new DatabaseController();
        }

        String sql = "SELECT * FROM produrctr";

        ResultSet rs = dbCon.query(sql);
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            while (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("sku"), rs.getFloat("price"), rs.getString("description"));

                products.add(product);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return products;
    }
}
