package com.example.intuition.commands;

public interface Command {
    void executar();//O método executar() é responsável por realizar a ação principal do comando. Por exemplo, criar um usuário, editar um usuário ou remover um usuário.
    void desfazer();//O método desfazer() permite reverter uma ação executada anteriormente. Essa funcionalidade é importante porque possibilita implementar operações de desfazer, conhecidas como Undo.
    String getDescricao();//O método getDescricao() retorna uma descrição textual do comando executado. Isso facilita o registro em históricos e o acompanhamento das operações realizadas pelo sistema
}

//Esse padrão transforma operações em objetos.
//Normalmente fazemos:
//criarUsuario();
//Com Command fazemos:
//CriarUserCommand.executar();
//A ação passa a ser representada por um objeto.
