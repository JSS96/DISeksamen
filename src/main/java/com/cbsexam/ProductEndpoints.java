package com.cbsexam;


import com.google.gson.Gson;
import controllers.ProductController;
import model.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("product")
public class ProductEndpoints {

    /**
     *
     * @param idProduct
     * @return Responses
     */
    @GET
    @Path("/{idProduct}")
    public Response getProduct(@PathParam("idProduct") int idProduct) {

        Product product = ProductController.getProduct(idProduct);

        //TODO: Add Encryption to JSON
        String json = new Gson().toJson(product);

        return Response
                .status(200)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(json)
                .build();

    }

    /**
     *
     * @return Responses
     */
    @GET
    @Path("/")
    public Response getProducts() {

        ArrayList<Product> products = ProductController.getProducts();

        //TODO: Add Encryption to JSON
        String json = new Gson().toJson(products);

        return Response
                .status(200)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(json)
                .build();

    }
}