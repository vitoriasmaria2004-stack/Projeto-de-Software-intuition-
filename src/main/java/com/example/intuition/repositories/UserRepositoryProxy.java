package com.example.intuition.repositories;

import com.example.intuition.entities.User;

public class UserRepositoryProxy {  //Foi criada uma nova classe: public class UserRepositoryProxy
                                    //Essa classe possui uma referência para o repositório verdadeiro:private UserRepository repository;

    private UserRepository repository;

    public UserRepositoryProxy() {
        repository = UserRepository.getInstance(); //No construtor foi utilizado: repository = UserRepository.getInstance();
                                                    //Assim o Proxy utiliza a instância única criada pelo Singleton.
    }
//Foram criados métodos equivalentes aos do repositório real:
    public User createUser(  //Exemplo:
            String username,  
            String email,
            String password) {

        System.out.println(                //Exemplo:
            "Proxy: verificando criação de usuário");

        return repository.createUser(
                username,
                email,
                password);
    } //O que acontece:
        //O Proxy recebe a solicitação;
        //Realiza verificações;
        //Encaminha para o UserRepository.
            //Fluxo:
            //Controller
                //↓
            //UserRepositoryProxy
                //↓
            //UserRepository
        //Dessa forma o acesso ao repositório fica controlado

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

//As vantagens obtidas:
//Controle de acesso;
//Camada adicional de segurança;
//Maior desacoplamento;
//Facilidade para futuras validações.
//O cliente não acessa diretamente o repositório.
//Tudo passa pelo Proxy.

