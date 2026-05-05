package com.example.intuition.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.intuition.entities.Folder;
import com.example.intuition.entities.User;
import com.example.intuition.repositories.FileRepository;
import com.example.intuition.repositories.GraphRepository;
import com.example.intuition.repositories.UserRepository;

@RestController
public class UserController {

    private UserRepository userRepository;
    private FileRepository fileRepository;
    private GraphRepository graphRepository;

    UserController(UserRepository userRepository, FileRepository fileRepository, GraphRepository graphRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.graphRepository = graphRepository;
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
}
