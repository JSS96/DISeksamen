package com.cbsexam;

import cache.ProductCache;
import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.TODO;
import controllers.ProductController;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Product;
import utils.Encryption;


@Path("product")
public class ProductEndpoints {

    //  Her har jeg lavet et globalt variable for cache
    static ProductCache productCache = new ProductCache();

    /**
     * @param idProduct
     * @return Responses
     */
    @GET
    @Path("/{idProduct}")
    public Response getProduct(@PathParam("idProduct") int idProduct) {

        // Call our controller-layer in order to get the order from the DB
        Product product = ProductController.getProduct(idProduct);

        // TODO: Add Encryption to JSON FIXED
        // We convert the java object to json with GSON library imported in Maven
        String json = new Gson().toJson(product);

        // Here the JSON object gets encrypted
        json = Encryption.encryptDecryptXOR(json);

        // Return a response with status 200 and JSON as type
        return Response.status(200).type(MediaType.TEXT_PLAIN_TYPE).entity(json).build();
    }

    /**
     * @return Responses
     */
    @GET
    @Path("/")
    public Response getProducts() {

        //Skrive egen kommentering
        // Call our controller-layer in order to get the order from the DB
        ArrayList<Product> products = productCache.getProducts(false);

        // TODO: Add Encryption to JSON FIXED
        // We convert the java object to json with GSON library imported in Maven
        String json = new Gson().toJson(products);

        // Here the JSON object gets encrypted
        json = Encryption.encryptDecryptXOR(json);

        // Return a response with status 200 and JSON as type
        return Response.status(200).type(MediaType.TEXT_PLAIN_TYPE).entity(json).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(String body) {

        // Read the json from body and transfer it to a product class
        Product newProduct = new Gson().fromJson(body, Product.class);

        // Use the controller to add the user
        Product createdProduct = ProductController.createProduct(newProduct);

        // Get the user back with the added ID and return it to the user
        String json = new Gson().toJson(createdProduct);

        // Return the data to the user
        if (createdProduct != null) {
            // Return a response with status 200 and JSON as type
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
        } else {
            return Response.status(400).entity("Could not create user").build();
        }
    }
}
