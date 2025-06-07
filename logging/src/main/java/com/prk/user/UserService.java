package com.prk.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository repository = new UserRepository();

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public List<User> getAllUsersByUserStatus(UserStatus status) {
        if (!status.equals(UserStatus.DELETED)) {
            return repository.findByUserStatus(status);
        } else {
            try {
                throw new Exception("users with DELETED status cannot be fetched, since formally they don't exist");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    public boolean addUser(User user) {
        if (user.getDateCreated().isAfter(LocalDateTime.now())) {
            try {
                throw new Exception("cannot create date in the future");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return repository.save(user);
    }

    public boolean deleteUser(User user) {
        if (user.getUserStatus().equals(UserStatus.DELETED)) {
            try {
                throw new Exception("cannot delete a DELETED user");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return repository.remove(user);
    }
}
