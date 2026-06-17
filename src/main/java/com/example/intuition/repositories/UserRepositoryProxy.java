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

// O padrão Proxy foi implementado através da classe UserRepositoryProxy, criada como intermediária entre
//os controladores e o UserRepository. Dessa forma todas as operações de cadastro e consulta passam primeiro pelo Proxy,
// que pode realizar verificações, registros  de aceeso ou validações antes de encaminhar a requisição para o repositório real.


// Problema:
// Acesso direto ao repositório.
// Solução:
// Criar uma camada intermediária responsável
// por validar e controlar o acesso antes de
// encaminhar a requisição ao UserRepository.
