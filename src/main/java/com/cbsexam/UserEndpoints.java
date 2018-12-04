package com.cbsexam;

import cache.UserCache;
import com.google.gson.Gson;
import controllers.UserController;

import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;
import utils.Encryption;
import utils.Log;

@Path("user")
public class UserEndpoints {

    //    Opretter et objekt af userCache
    static UserCache userCache = new UserCache();


    /**
     * @param idUser
     * @return Responses
     */
    @GET
    @Path("/{idUser}")
    public Response getUser(@PathParam("idUser") int idUser) {

        // Use the ID to get the user from the controller.
        User user = UserController.getUser(idUser);

        // TODO: Add Encryption to JSON Fixed 1
        // Convert the user object to json in order to return the object
        String json = new Gson().toJson(user);

        // Here the JSON object gets encrypted
        json = Encryption.encryptDecryptXOR(json);

        // TODO: What should happen if something breaks down? Fixed 1

        // Here if it gets the user successfully return the JSON object and status code 200(is not null)
        // else return code 400 and and a custom text
        if (user != null) {
            // Return a response with status 200 and JSON as type
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
        } else {
            return Response.status(400).entity("Could not find user").build();
        }
    }

    /**
     * @return Responses
     */
    @GET
    @Path("/")
    public Response getUsers() {

        // Write to log that we are here
        Log.writeLog(this.getClass().getName(), this, "Get all users", 0);

        // Get a list of users
        ArrayList<User> users = userCache.getUsers(false);

        // TODO: Add Encryption to JSON Fixed 1
        // Transfer users to json in order to return it to the user
        String json = new Gson().toJson(users);

        // Here the JSON object gets encrypted
        json = Encryption.encryptDecryptXOR(json);

        // Return the users with the status code 200
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(json).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String body) {

        // Read the json from body and transfer it to a user class
        User newUser = new Gson().fromJson(body, User.class);

        // Use the controller to add the user
        User createUser = UserController.createUser(newUser);

        // Get the user back with the added ID and return it to the user
        String json = new Gson().toJson(createUser);

        // Return the data to the user
        if (createUser != null) {
            // Return a response with status 200 and JSON as type
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
        } else {
            return Response.status(400).entity("Could not create user").build();
        }
    }

    // TODO: Make the system able to login users and assign them a token to use throughout the system. Fixed 1
    /**
     * This is a endpoint which uses the path user/login, to make a user able to login, and create a token
     * @param body takes a email and password, if thats true make a token, and return that
     * to the user, also made so the system is able to handle if an error occurs so if anything breaks down or dont work the
     * the user gets a error 400 and a custom text
     * @return
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(String body) {

        // Gets the input from JSON format and make it to GSON, so java can work with it
        User loginUser1 = new Gson().fromJson(body, User.class);

        // Takes the users input and call the login method
        User loginUser = UserController.login(loginUser1);

        if (loginUser != null) {
            // Return a response with status 200 and the users token
            String json = new Gson().toJson("Here is your token " + loginUser.getToken());
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
        } else {
            return Response.status(400).entity("Try again, it seems that username and password dosent match").build();
        }
    }

    // TODO: Make the system able to delete users Fixed 1
    /**
     * This is a endpoint which uses the path user/delete to delete a user, to delete a user you need type in an email
     * and the token received when loging in.
     * @param body
     * @return
     */
    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(String body) {

        // Gets the input from JSON format and make it to GSON, so java can work with it
        User user = new Gson().fromJson(body, User.class);

        // Takes the users input and call the deleteUser method
        Boolean validate = UserController.deleteUser(user);

        // if its true the it says the you have deleted the user and return staus 200.
        if (validate) {
            String outPut = new Gson().toJson("You have now deleted " + user.getEmail());
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(outPut).build();
        } else {
            // else retuens status code 400
            return Response.status(400).entity("Something went wrong, maybe the tokens isn't right :)").build();
        }
    }

    // TODO: Make the system able to update users FIXED 1

    /**
     * This is a endpoint which uses the path user/update to update a user, to update a user you need type in an email
     * and the token received when loging in.
     * @param body
     * @return
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String body) {

        // Gets the input from JSON format and make it to GSON, so java can work with it
        User user = new Gson().fromJson(body, User.class);

        // Takes the users input and call the updateUser method
        Boolean updateUser = UserController.updateUser(user);

        // If true returns a response with status 200 and text.
        if (updateUser) {
            String outPut = new Gson().toJson("You have now updated " + user.getEmail());
            return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(outPut).build();
        } else {
            // else return a status 400 if something goes wrong
            return Response.status(400).entity("Something went wrong, maybe the tokens isn't right :)").build();
        }
    }
}
