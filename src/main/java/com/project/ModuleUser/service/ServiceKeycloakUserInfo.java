package com.project.ModuleUser.service;

import com.project.ModuleUser.entities.KeycloakUserInfo;
import com.project.ModuleUser.repository.KeycloakUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceKeycloakUserInfo implements IServiceKeycloakUserInfo {

    @Autowired
    private final KeycloakUserInfoRepository userInfoRepository;
    private final KeycloakUserInfoRepository repository;

    public ServiceKeycloakUserInfo(KeycloakUserInfoRepository userInfoRepository, KeycloakUserInfoRepository repository) {
        this.userInfoRepository = userInfoRepository;
        this.repository = repository;
    }

    @Override
    public void registerUserFromJwt(Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        String subject = jwt.getSubject(); // ID unique de l'utilisateur
        String fullName = jwt.getClaimAsString("name");

        // Vérification si déjà enregistré
        if (userInfoRepository.existsById(subject)) {
            System.out.println("Utilisateur déjà enregistré : " + email);
            return;
        }

        // Création de l'objet utilisateur
        KeycloakUserInfo user = new KeycloakUserInfo();
        user.setId(subject);
        user.setPreferredUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setFullName(fullName);

        // 🔥 Récupération du rôle correct
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            if (roles != null && !roles.isEmpty()) {
                for (String role : roles) {
                    if (!role.equals("offline_access") &&
                            !role.equals("uma_authorization") &&
                            !role.startsWith("default-roles")) {
                        user.setRole(role); // 🎯 rôle pertinent
                        break;
                    }
                }

                if (user.getRole() == null) {
                    user.setRole(roles.get(0)); // fallback: on met le premier rôle
                }
            }
        }

        // Sauvegarde
        userInfoRepository.save(user);
        System.out.println("✅ Utilisateur sauvegardé dans la base : " + username);
    }
    @Override
    public List<KeycloakUserInfo> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        Optional<KeycloakUserInfo> user = repository.findById(id);
        if(user.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Utilisateur avec id "+id+" non trouvé");
        }
    }

    @Override
    public KeycloakUserInfo getUserById(String id) {
        return userInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
    }

    @Override
    public List<KeycloakUserInfo> getTechniciens() {
        return repository.findByRole("TECHNICIEN MAINTENANCE");
    }

}
