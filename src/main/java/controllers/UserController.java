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
        // TODO: Hash the user password before saving it. Fixed

        // Opretter et objekt af H
        Hashing H = new Hashing();

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

    // ikke færdig endnu
    public static User updateUser(User user) {

        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        Log.writeLog(UserController.class.getName(), user, "Actually updating a user in DB", 0);



//        UPDATE Customers
//        SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
//        WHERE CustomerID = 1;


        // Opretter et objekt af H
        Hashing H = new Hashing();

        int userID = dbCon.insert("UPDATE user SET (first_name, last_name, password, email) VALUES('"
                + user.getFirstname()
                + "', '"
                + user.getLastname()
                + "', '"
                + H.md5WithSalt(user.getCreatedTime(), user.getPassword())
                + "', '"
                + user.getEmail()
                + "', "
                + user.getCreatedTime()
                + ")WHERE id="+user.getId());

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


    public static Boolean deleteUser(User user) {

        Hashing H = new Hashing();

        String token = user.getToken();
        int id = user.getId();
        String email = user.getEmail();

        String sql = "DELETE FROM user WHERE id =" + user.getId();

        Log.writeLog(UserController.class.getName(), user, "Deleting a user ", 0);

        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        System.out.println(token);
        if (token.equals(H.sha5WithSalt(id, email)))
        {
            dbCon.insert(sql);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static User login(User user) {

        if (dbCon == null) {
            dbCon = new DatabaseController();
        }

        Hashing H = new Hashing();

        String password = user.getPassword();

        Log.writeLog(UserController.class.getName(), user, "Login ", 0);

        String sql = "SELECT * FROM user where email= \'" + user.getEmail() + "\'";

        ResultSet rs = dbCon.query(sql);

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
        if (user.getPassword().equals(H.md5WithSalt(user.getCreatedTime(), password))) {
            String createdToken = H.sha5WithSalt(user.getId(), user.getEmail());
            user.setToken(createdToken);
            return user;
        } else {
            return null;
        }
    }
}