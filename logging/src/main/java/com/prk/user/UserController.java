package com.prk.user;

import java.util.List;
import java.util.logging.*;

public class UserController {
    private final UserService userService = new UserService();
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    // get all endpoint
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // get by status
    public List<User> getAllUsersByUserStatus(UserStatus status) {
        return userService.getAllUsersByUserStatus(status);
    }

    // post endpoint
    public boolean addUser(User user) {
        LOGGER.log(Level.FINE, "in Post endpoint for user: " + user);
        return userService.addUser(user);
    }

    // delete
    public boolean deleteUser(User user) {
        return userService.deleteUser(user);
    }
}
