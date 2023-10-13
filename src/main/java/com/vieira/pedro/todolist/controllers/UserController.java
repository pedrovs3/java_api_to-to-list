package com.vieira.pedro.todolist.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.vieira.pedro.todolist.models.UserModel;
import com.vieira.pedro.todolist.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
     private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }
        var passwordHashed = BCrypt.withDefaults().hashToString(
                12,
                userModel.getPassword().toCharArray()
        );
        userModel.setPassword(passwordHashed);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(userModel));
    }

    @GetMapping("/")
    public List<UserModel> showAll() {
        return this.userRepository.findAll();
    }
}
