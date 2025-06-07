package com.prk.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository {
    private static List<User> userList = getDummyDataList();

    public List<User> getUserList() {
        return userList;
    }

    public List<User> findAll() {
        return userList;
    }

    public List<User> findByUserStatus(UserStatus status) {
        return userList.stream()
                .filter(u -> u.getUserStatus().equals(status))
                .toList();
    }

    public boolean save(User user) {
        user.setId(userList.get(userList.size() - 1).getId() + 1);
        return userList.add(user);
    }

    public boolean remove(User user) {
        if (userList.contains(user)) {
            user.setUserStatus(UserStatus.DELETED);
            return true;
        } else {
            return false;
        }
    }

    public static List<User> getDummyDataList() {
        User kate1 = new User(1, "Catherine", "catherine.aragon@royal.uk", LocalDateTime.now(), UserStatus.ACTIVE);
        User anne1 = new User(2, "Anne", "anne.boleyn@royal.uk", LocalDateTime.now(), UserStatus.ACTIVE);
        User jane = new User(3, "Jane", "jane.seymour@royal.uk", LocalDateTime.now(), UserStatus.ACTIVE);
        User[] users = { kate1, anne1, jane };
        return new ArrayList<>(Arrays.asList(users));
    }
}
