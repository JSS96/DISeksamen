package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.User;
import utils.Hashing;
import utils.Log;

public class UserController {

    private static DatabaseController dbCon;

    public UserController() {
        dbCon = new DatabaseController();
    }

    public static User getUser(int id) {

        // Check for connection
        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        // Build the query for DB
        String sql = "SELECT * FROM user where id=" + id;

        // Actually do the query
        ResultSet rs = dbCon.query(sql);
        User user = null;

        try {
            // Get first object, since we only have one
            if (rs.next()) {
                user =
                        new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("email"));

                // return the create object
                return user;
            } else {
                System.out.println("No user found");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // Return null
        return user;
    }

    /**
     * Get all users in database
     *
     * @return
     */
    public static ArrayList<User> getUsers() {

        // Check for DB connection
        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        // Build SQL
        String sql = "SELECT * FROM user";

        // Do the query and initialyze an empty list for use if we don't get results
        ResultSet rs = dbCon.query(sql);
        ArrayList<User> users = new ArrayList<User>();

        try {
            // Loop through DB Data
            while (rs.next()) {
                User user =
                        new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("email"));

                // Add element to list
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // Return the list of users
        return users;
    }

    public static User createUser(User user) {

        // Write in log that we've reach this step
        Log.writeLog(UserController.class.getName(), user, "Actually creating a user in DB", 0);

        // Set creation time for user.
        user.setCreatedTime(System.currentTimeMillis() / 1000L);

        // Check for DB Connection
        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        // Insert the user in the DB
        // TODO: Hash the user password before saving it. FIXED

        // Creates a object of Hashing class
        Hashing H = new Hashing();

        // Hashes the user password with the method md5WithSalt, uses the created time password
        // and a extra salt.
        int userID = dbCon.insert("INSERT INTO user(first_name, last_name, password, email, created_at) VALUES('"
                + user.getFirstname()
                + "', '"
                + user.getLastname()
                + "', '"
                + H.md5WithSalt(user.getCreatedTime(), user.getPassword())
                + "', '"
                + user.getEmail()
                + "', "
                + user.getCreatedTime()
                + ")");

        if (userID != 0) {
            //Update the userid of the user before returning
            user.setId(userID);
        } else {
            // Return null if user has not been inserted into database
            return null;
        }
        // Return user
        return user;
    }

    /**
     * This method makes a user able to delete a user by email
     * @param user takes a user with the params email and token to validate against the user in the database.
     * The user in the databasen is found by a SQL query seltcing a user by a email
     * if user is found and macthing tokens and email, the method wil delete the user.
     * @return
     */
    public static Boolean deleteUser(User user) {

        // Checks if there is connection with database
        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        // writes a log
        Log.writeLog(UserController.class.getName(), user, "Deleting a user ", 0);

        // Creats a object of Hasing class
        Hashing H = new Hashing();

        // gets the user token
        String token = user.getToken();

        // creates a temp user from user
        User tokenUser = user;

        // sets the token for the tokenuser to null
        tokenUser.setToken(null);

        // SQL statement to Select all information from a user
        String sql = "SELECT * FROM user WHERE email = \'" + user.getEmail()+"\'";

        // Makes the resultset to get the user
        ResultSet rs = dbCon.query(sql);

        // try catch for getting the user information, from database.
        try {
            // Get first object, since we only have one
            if (rs.next()) {
                tokenUser =
                        new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("email"));
                // Makes the token, since the token is not saved in database
                tokenUser.setToken(H.sha5WithSalt(tokenUser.getId(),tokenUser.getEmail(),tokenUser.getPassword()));
            } else {
                System.out.println("No user found");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // If the tokens matches then make SQL statement to delete the user from database
        // and return true, else return false
        if (token.equals(tokenUser.getToken())) {
            String sqlDelete = "DELETE FROM user WHERE email = \'" + user.getEmail()+"\'";
            dbCon.insert(sqlDelete);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * The method makes a user able to login in, and receives a token.
     * @param user takes a user with the params email and password to validate against the user in the database,
     * the user in the databasen is found by a SQL query seltcing a user by a email
     * if user is found and macthing, the method will retrun a token.
     * @return
     */
    public static User login(User user) {

        // Checks if there is connection with database
        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        // Creats a object of Hasing class
        Hashing H = new Hashing();

        // Retrives the users password, for later valdiation
        String password = user.getPassword();

        // writes a log
        Log.writeLog(UserController.class.getName(), user, "User Login", 0);

        // SQL statement for selcting a user by email
        String sql = "SELECT * FROM user where email= \'" + user.getEmail() + "\'";

        // Makes the resultset to get the user
        ResultSet rs = dbCon.query(sql);

        // try catch for getting the user information, from database.
        try {
            // Get first object, since we only have one
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getLong("created_at"));
            } else {
                System.out.println("User cant be found");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // validation of users password where it hashes the writen password from user and matches it with the hashed
        // one in database, if this is true it returns the whole user with a token
        if (user.getPassword().equals(H.md5WithSalt(user.getCreatedTime(), password))) {
            String createdToken = H.sha5WithSalt(user.getId(), user.getEmail(),user.getPassword());
            user.setToken(createdToken);
            return user;
        } else {
            return null;
        }
    }

    /**
     * This method that makes the user able to update a user.
     * @param user this param takes the users writen attributes firstname, lastname, password, email and token
     * if the email and token matches the one in database, then make SQL statement to update user with the new attributes,
     * this also gives him a new token. as it is based on password.
     * @return
     */
    public static Boolean updateUser(User user) {

        // Checks if there is connection with database
        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        // writes log in console
        Log.writeLog(UserController.class.getName(), user, "Deleting a user ", 0);

        // Creats a object of Hasing class
        Hashing H = new Hashing();

        // gets the user token
        String token = user.getToken();

        // creates a temp user from user
        User tokenUser = user;

        // sets the token for the tokenuser to null
        tokenUser.setToken(null);

        // SQL statement to Select all information from a user
        String sql = "SELECT * FROM user WHERE email = \'" + user.getEmail()+"\'";

        // Makes the resultset to get the user
        ResultSet rs = dbCon.query(sql);

        // try catch for getting the user information, from database.
        try {
            // Get first object, since we only have one
            if (rs.next()) {
                tokenUser =
                        new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getLong("created_at"));
                // sets the token for the temp user
                tokenUser.setToken(H.sha5WithSalt(tokenUser.getId(),tokenUser.getEmail(),tokenUser.getPassword()));
            } else {
                System.out.println("No user found");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // If the tokens matches update the user,return true, and make a update sql statement, else return false
        if (token.equals(tokenUser.getToken())) {
            String updateSql = "UPDATE user SET first_name= \'" + user.getFirstname() + "\', " + "last_name= \'"
                    + user.getLastname() + "\', " + "password= \'"
                    + H.md5WithSalt(tokenUser.getCreatedTime(), user.getPassword()) + "\' "
                    + "WHERE email= \'" + user.getEmail() + "\'";
            dbCon.insert(updateSql);
            return true;
        }
        else {
            return false;
        }
    }
}