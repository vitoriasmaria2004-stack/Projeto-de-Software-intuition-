package com.example.intuition.commands;

import java.util.Stack;

public class CommandHistory {
    
    private Stack<Command> pilhaDesfazer = new Stack<>();
    private Stack<Command> pilhaRefazer = new Stack<>();
    
    public void executarComando(Command comando) {
        comando.executar();
        pilhaDesfazer.push(comando);
        pilhaRefazer.clear();
        System.out.println("📝 Ações no histórico: " + pilhaDesfazer.size());
    }
    
    public void desfazer() {
        if (pilhaDesfazer.isEmpty()) {
            System.out.println("❌ Nada para desfazer!");
            return;
        }
        
        Command comando = pilhaDesfazer.pop();
        comando.desfazer();
        pilhaRefazer.push(comando);
        System.out.println("↩️ Desfeito: " + comando.getDescricao());
    }
    
    public void refazer() {
        if (pilhaRefazer.isEmpty()) {
            System.out.println("❌ Nada para refazer!");
            return;
        }
        
        Command comando = pilhaRefazer.pop();
        comando.executar();
        pilhaDesfazer.push(comando);
        System.out.println("↪️ Refeito: " + comando.getDescricao());
    }
    
    public boolean podeDesfazer() {
        return !pilhaDesfazer.isEmpty();
    }
    
    public boolean podeRefazer() {
        return !pilhaRefazer.isEmpty();
    }
    
    public String getProximoDesfazer() {
        if (pilhaDesfazer.isEmpty()) return "Nada";
        return pilhaDesfazer.peek().getDescricao();
    }
    
    public String getProximoRefazer() {
        if (pilhaRefazer.isEmpty()) return "Nada";
        return pilhaRefazer.peek().getDescricao();
    }
}
