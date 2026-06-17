package com.example.intuition.commands;

import com.example.intuition.entities.User;

public class DeletarUserCommand implements Command {
    
    private User usuario;
    
    public DeletarUserCommand(User usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public void executar() {
        // Lógica para deletar usuário
        System.out.println("🗑️ Usuário deletado: " + usuario.getUsername());
    }
    
    @Override
    public void desfazer() {
        // Recria o usuário
        System.out.println("↩️ Deleção desfeita: " + usuario.getUsername() + " recriado");
    }
    
    @Override
    public String getDescricao() {
        return "Deletar usuário: " + usuario.getUsername();
    }
}
