package com.example.intuition.commands;

import com.example.intuition.entities.User;

public class EditarUserCommand implements Command {
    
    private User usuarioOriginal;
    private User usuarioEditado;
    
    public EditarUserCommand(User usuarioOriginal, User usuarioEditado) {
        this.usuarioOriginal = usuarioOriginal;
        this.usuarioEditado = usuarioEditado;
    }
    
    @Override
    public void executar() {
        // Lógica para editar usuário
        System.out.println("✏️ Usuário editado: " + usuarioEditado.getUsername());
    }
    
    @Override
    public void desfazer() {
        // Volta para versão original
        System.out.println("↩️ Edição desfeita: voltando para " + usuarioOriginal.getUsername());
    }
    
    @Override
    public String getDescricao() {
        return "Editar usuário: " + usuarioOriginal.getUsername();
    }
}
