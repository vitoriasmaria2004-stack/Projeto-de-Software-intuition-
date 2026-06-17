package com.example.intuition.commands;

public interface Command {
    void executar();
    void desfazer();
    String getDescricao();
}
