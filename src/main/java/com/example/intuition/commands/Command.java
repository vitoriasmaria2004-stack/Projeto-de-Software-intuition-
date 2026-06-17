package com.example.intuition.commands;

public interface Command {
    void executar();
    void desfazer();
    String getDescricao();
}

//Esse padrão transforma operações em objetos.
//Normalmente fazemos:
//criarUsuario();
//Com Command fazemos:
//CriarUserCommand.executar();
//A ação passa a ser representada por um objeto.
