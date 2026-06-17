package com.example.intuition.repositories;

import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.example.intuition.entities.User;
//O PADRÃO SINGLETON foi aplicado à classe UserRepository para garantir que exista
//apenas uma única instância responsável pelo gerenciamento dos usuários da aplicação.
//Dessa forma, todas as operações de cadastro e consulta utilizam a mesma fonte de dados,
//evitando inconsistências causadas pela criação de multiplos repositórios 
//e centralizando o controle dos usuários do sistema.

@Repository
public class UserRepository {

    private static UserRepository instance;//Essa variável armazena a única instância da classe

    private Map<Integer, User> users = new HashMap<>(); //CENTRALIZAÇÃO DOS USUÁRIOS : Todos os usuários ficam armazenados e um único local
    private int incrementalId = 0; //Controle de IDS : Podem surgir IDs duplicados 

    private UserRepository() { //foi criado um construtor
    }                          //O construtor privado impede que outras classes utilizem:new UserRepository();Ou seja, ninguém pode criar novos objetos diretamente.

    public static UserRepository getInstance() { //Esse método verifica:
                                                //se a instância ainda não existe, ela é criada;
                                                //se já existe, a mesma instância é retornada.
                                               // Assim garantimos que todo o sistema utilize exatamente o mesmo repositório.
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User createUser(String username, String email, String password) { //Fonte única de dados : Todas as operações => createUser() e getByUsernameOrEmail() devem consultar o mesmo conjunto de usuários  

        for (User u : users.values()) {
            if (u.getUsername().equals(username)
                    || u.getEmail().equals(email)) {
                throw new RuntimeException(
                        "Username or email already in use");
            }
        }

        incrementalId++;

        User newUser = new User(
                incrementalId,
                username,
                email,
                password);

        users.put(incrementalId, newUser);

        return newUser;
    }

    public User getByUsernameOrEmail(String usernameOrEmail) {

        for (User u : users.values()) {

            if (u.getUsername().equals(usernameOrEmail)
                    || u.getEmail().equals(usernameOrEmail)) {
                return u;
            }
        }

        throw new RuntimeException(
                "user " + usernameOrEmail + " was not found");
    }
}

//Benefícios do Singleton
//A utilização do Singleton trouxe alguns benefícios:
//Centralização do gerenciamento dos usuários;
//Evita múltiplos repositórios;
//Mantém os dados consistentes;
//Facilita o controle do sistema.
//Fluxo:
//Sistema
//↓
//UserRepository único
//↓
//Dados dos usuários

