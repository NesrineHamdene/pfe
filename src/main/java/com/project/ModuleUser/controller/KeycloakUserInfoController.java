package com.project.ModuleUser.controller;

import com.project.ModuleUser.entities.KeycloakUserInfo;
import com.project.ModuleUser.service.IServiceKeycloakUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keycloak-user")
public class KeycloakUserInfoController {
    private final IServiceKeycloakUserInfo serviceKeycloakUserInfo;

    @Autowired
    public KeycloakUserInfoController(IServiceKeycloakUserInfo serviceKeycloakUserInfo) {
        this.serviceKeycloakUserInfo = serviceKeycloakUserInfo;
    }

    @PostMapping("/register")
    public String registerUser(@AuthenticationPrincipal Jwt jwt) {
        // Debug
        System.out.println("JWT reçu: " + (jwt != null));
        if (jwt != null) {
            System.out.println("Subject: " + jwt.getSubject());
            System.out.println("Claims: " + jwt.getClaims());
        }

        serviceKeycloakUserInfo.registerUserFromJwt(jwt);
        return "Utilisateur enregistré avec succès depuis le token Keycloak.";
    }
    @GetMapping("/all")
    public List<KeycloakUserInfo> getAllUsers() {
        return serviceKeycloakUserInfo.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        try {
            serviceKeycloakUserInfo.deleteUserById(id);
            return "Utilisateur supprimé avec succès.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}