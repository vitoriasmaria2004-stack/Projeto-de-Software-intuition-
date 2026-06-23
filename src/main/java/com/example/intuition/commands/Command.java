package com.example.intuition.commands;

public interface Command {
    void executar();//O método executar() é responsável por realizar a ação principal do comando.
    void desfazer();//O método desfazer() permite reverter uma ação executada anteriormente. 
    String getDescricao();//O método getDescricao() retorna uma descrição textual do comando executado. 
}
//Esse padrão transforma operações em objetos.
//Normalmente fazemos:
//criarUsuario();
//Com Command fazemos:
//CriarUserCommand.executar();
//A ação passa a ser representada por um objeto.
