package com.project.ModuleUser.controller;

import com.project.ModuleShared.dto.KeycloakUserInfoDTO;
import com.project.ModuleUser.entities.KeycloakUserInfo;
import com.project.ModuleUser.service.IServiceKeycloakUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("JWT reçu: " + (jwt != null));
        if (jwt != null) {
            System.out.println("Subject: " + jwt.getSubject());
            System.out.println("Claims: " + jwt.getClaims());
        }
        serviceKeycloakUserInfo.registerUserFromJwt(jwt);
        return "Utilisateur enregistré avec succès depuis le token Keycloak.";
    }

    @GetMapping("/all")
    public List<KeycloakUserInfoDTO> getAllUsers() {
        return serviceKeycloakUserInfo.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    @GetMapping("/techniciens")
    public List<KeycloakUserInfoDTO> getTechniciens() {
        System.out.println("🔄 [ModuleUser] Endpoint /techniciens appelé");

        List<KeycloakUserInfo> techniciens = serviceKeycloakUserInfo.getTechniciens();
        System.out.println("📊 [ModuleUser] Nombre de techniciens trouvés: " + techniciens.size());

        List<KeycloakUserInfoDTO> result = techniciens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        System.out.println("✅ [ModuleUser] Retour de " + result.size() + " techniciens en DTO");
        return result;
    }

    @GetMapping("/{id}")
    public KeycloakUserInfoDTO getUserById(@PathVariable String id) {
        KeycloakUserInfo user = serviceKeycloakUserInfo.getUserById(id);
        return convertToDTO(user);
    }

    // Méthode de conversion
    private KeycloakUserInfoDTO convertToDTO(KeycloakUserInfo user) {
        KeycloakUserInfoDTO dto = new KeycloakUserInfoDTO();
        dto.setId(user.getId());
        dto.setPreferredUsername(user.getPreferredUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole());
        return dto;
    }
}