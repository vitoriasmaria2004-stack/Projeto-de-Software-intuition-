package com.example.intuition.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.intuition.commands.*;
import com.example.intuition.entities.Folder;
import com.example.intuition.entities.User;
import com.example.intuition.repositories.FileRepository;
import com.example.intuition.repositories.GraphRepository;
import com.example.intuition.repositories.UserRepository;
import com.example.intuition.repositories.UserRepositoryProxy;

@RestController
public class UserController {

    private UserRepository userRepository;
    private FileRepository fileRepository;
    private GraphRepository graphRepository;
    
    //  NOVOS ATRIBUTOS PARA OS PADRÕES
    private UserRepositoryProxy proxy;
    private CommandHistory historico;

    UserController(UserRepository userRepository, 
                   FileRepository fileRepository, 
                   GraphRepository graphRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.graphRepository = graphRepository;
        
        // INICIALIZA OS NOVOS COMPONENTES
        this.proxy = new UserRepositoryProxy();
        this.historico = new CommandHistory();
    }

    
    
    @PostMapping("/user/signup")
    @CrossOrigin(origins = "*")
    User createUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        User newUser = userRepository.createUser(username, email, password);
        fileRepository.createRoot(newUser.getId());
        graphRepository.createGraph(newUser.getId());
        return newUser;
    }

    @PostMapping("/user/login")
    @CrossOrigin(origins = "*")
    User login(@RequestBody Map<String, String> body) {
        String usernameOrEmail = body.get("username");
        String password = body.get("password");
        User user = userRepository.getByUsernameOrEmail(usernameOrEmail);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong Password");
        }
        return user;
    }

    @GetMapping("/user/root")
    @CrossOrigin(origins = "*")
    Folder getUserRoot(@RequestParam("userId") int userId) {
        return fileRepository.getUserRoot(userId);
    }

    // ==========================================
    //  NOVOS MÉTODOS COM OS PADRÕES
    
    /**
     * Criar usuário usando o Proxy e com suporte a Undo/Redo
     */
    @PostMapping("/user/signup-with-undo")
    @CrossOrigin(origins = "*")
    public User criarUsuarioComUndo(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        
        // Cria o usuário usando o Proxy
        User novoUsuario = proxy.createUser(username, email, password);
        
        // Registra o comando no histórico para permitir Undo
        Command comando = new CriarUserCommand(proxy, novoUsuario);
        historico.executarComando(comando);
        
        return novoUsuario;
    }
    
    /**
     * Desfazer última ação (Ctrl+Z)
     */
    @PostMapping("/user/undo")
    @CrossOrigin(origins = "*")
    public String desfazer() {
        historico.desfazer();
        return "Última ação desfeita com sucesso!";
    }
    
    /**
     * Refazer ação desfeita (Ctrl+Y)
     */
    @PostMapping("/user/redo")
    @CrossOrigin(origins = "*")
    public String refazer() {
        historico.refazer();
        return "Ação refeita com sucesso!";
    }
    
    /**
     * Verificar o estado do histórico
     */
    @GetMapping("/user/history")
    @CrossOrigin(origins = "*")
    public String verHistorico() {
        return "Pode desfazer: " + historico.podeDesfazer() + 
               " | Pode refazer: " + historico.podeRefazer() +
               " | Próximo desfazer: " + historico.getProximoDesfazer();
    }
}
