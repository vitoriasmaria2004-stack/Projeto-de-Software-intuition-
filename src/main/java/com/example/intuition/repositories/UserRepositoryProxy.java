package com.example.intuition.repositories;

import com.example.intuition.entities.User;

public class UserRepositoryProxy {

    private UserRepository repository;

    public UserRepositoryProxy() {
        repository = UserRepository.getInstance();
    }

    public User createUser(
            String username,
            String email,
            String password) {

        System.out.println(
            "Proxy: verificando criação de usuário");

        return repository.createUser(
                username,
                email,
                password);
    }

    public User getByUsernameOrEmail(
            String usernameOrEmail) {

        System.out.println(
            "Proxy: verificando consulta");

        return repository.getByUsernameOrEmail(
                usernameOrEmail);
    }
}
