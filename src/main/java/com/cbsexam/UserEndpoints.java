package com.cbsexam;


import com.google.gson.Gson;
import controllers.UserController;
import model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("user")
public class UserEndpoints {

    /**
     *
     * @param idUser
     * @return Responses
     */
    @GET
    @Path("/{idUser}")
    public Response getUser(@PathParam("idUser") int idUser) {

        User user = UserController.getUser(idUser);

        //TODO: Add Encryption to JSON
        String json = new Gson().toJson(user);

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
    public Response getUsers() {

        ArrayList<User> users = UserController.getUsers();

        //TODO: Add Encryption to JSON
        String json = new Gson().toJson(users);

        return Response
                .status(200)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(json)
                .build();

    }
}