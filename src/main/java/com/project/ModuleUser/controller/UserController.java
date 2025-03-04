package com.project.ModuleUser.controller;

import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired // Injection explicite via @Autowired
    private IServiceUser serviceUser;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = serviceUser.createUser(user); // Création de l'utilisateur
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser); // Réponse avec statut 201
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 avec l'exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{username}")
     public Optional<User> getUserByUsername(@PathVariable String username) {
     return Optional.ofNullable(serviceUser.findByUsername(username));
    }
}
