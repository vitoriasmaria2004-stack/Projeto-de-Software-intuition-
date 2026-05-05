package com.example.intuition.repositories;

import java.util.Map;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

import com.example.intuition.entities.User;

@Repository
public class UserRepository {
    private static Map<Integer, User> users = new HashMap<>();
    private static int incrementalId = 0;

    public User createUser(String username, String email, String password) {
        for (User u : users.values()) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                throw new RuntimeException("Username or email already in use");
            }
        }
        incrementalId++;
        User newUser = new User(incrementalId, username, email, password);
        users.put(incrementalId, newUser);
        return newUser;
    }

    public User getByUsernameOrEmail(String usernameOrEmail) {
        for (User u : users.values()) {
            if (u.getUsername().equals(usernameOrEmail) || u.getEmail().equals(usernameOrEmail)) {
                return u;
            }
        }
        throw new RuntimeException("user " + usernameOrEmail + " was not found");
    }
}
