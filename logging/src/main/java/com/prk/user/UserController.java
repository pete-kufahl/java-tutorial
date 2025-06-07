package com.prk.user;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class UserController {
    private final UserService userService = new UserService();
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    static {
        LOGGER.setLevel(Level.FINE);
        FileHandler handler = null;
        try {
            handler = new FileHandler(UserController.class.getSimpleName() + ".log");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        handler.setLevel(Level.FINE);
        handler.setFormatter(new SimpleFormatter());
        // handler.setFilter(s -> false);
        LOGGER.addHandler(handler);
    }

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
