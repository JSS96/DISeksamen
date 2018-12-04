package cache;

import controllers.UserController;
import model.User;
import utils.Config;

import java.util.ArrayList;

//TODO: Build this cache and use it. FIXED 1
public class UserCache {

    // List of users
    private ArrayList<User> users;

    // Time cache should live
    private long ttl;

    // Sets when the cache has been created
    private long created;

    // Gets ttl
    public UserCache() {
        this.ttl = Config.getUserTtl();
    }

    public ArrayList<User> getUsers(Boolean forceUpdate) {

        // If we whis to clear cache, we can set force update.
        // Otherwise we look at the age of the cache and figure out if we should update.
        // If the list is empty we also check for new products
        // have changed > to this < so i, so it updates when the created TTL is less than the current time
        if (forceUpdate
                || ((this.created + this.ttl) <= (System.currentTimeMillis() / 1000L))
                || this.users == null) {

            // Get users from controller, since we wish to update.
            ArrayList<User> users = UserController.getUsers();

            // Set products for the instance and set created timestamp
            this.users = users;
            this.created = System.currentTimeMillis() / 1000L;

        }
        // Return the users
        return this.users;
    }
}
