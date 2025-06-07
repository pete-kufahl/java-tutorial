package com.prk.user;

import java.time.LocalDateTime;

public class User {
    private long id;
    private String username;
    private String email;
    private LocalDateTime dateCreated;
    private UserStatus userStatus;

    public User() { }

    public User(long id, String username, String email, LocalDateTime created, UserStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dateCreated = created;
        this.userStatus = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return User.class.getName() + " - name: " + username + ", id: " + id + ", email: " + email;
    }
}
