package com.example.intuition.commands;

import com.example.intuition.entities.User;
import com.example.intuition.repositories.UserRepositoryProxy;

public class CriarUserCommand implements Command {
    
    private UserRepositoryProxy proxy;
    private User usuario;
    
    public CriarUserCommand(UserRepositoryProxy proxy, User usuario) {
        this.proxy = proxy;
        this.usuario = usuario;
    }
    
    @Override
    public void executar() {
        proxy.createUser(
            usuario.getUsername(), 
            usuario.getEmail(), 
            usuario.getPassword()
        );
        System.out.println("✅ Usuário criado: " + usuario.getUsername());
    }
    
    @Override
    public void desfazer() {
        System.out.println("↩️ Desfazendo criação do usuário: " + usuario.getUsername());
        // Aqui você pode implementar a lógica de remover o usuário
    }
    
    @Override
    public String getDescricao() {
        return "Criar usuário: " + usuario.getUsername();
    }
}
