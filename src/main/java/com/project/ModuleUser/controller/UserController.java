package com.project.ModuleUser.controller;

import com.project.ModuleUser.dto.LoginRequest;
import com.project.ModuleUser.dto.UserDTO;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired
    private IServiceUser serviceUser;

    // Retourne tous les techniciens
    @GetMapping("/techniciens")
    public List<UserDTO> getTechniciens() {
        return serviceUser.getUsersByRole(Role.TECHNICIEN_MAINTENANCE)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), null, user.getFileName()))
                .toList();
    }

    // Créer un utilisateur
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = serviceUser.createUser(convertToEntity(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = serviceUser.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Récupérer un utilisateur par username
    @GetMapping("/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return Optional.ofNullable(serviceUser.findByUsername(username));
    }

    // Récupérer un utilisateur par email
    @GetMapping("/email/{email}")
    public HttpEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        Optional<User> user = serviceUser.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        User updatedUser = serviceUser.updateUser(id, updatedUserDTO);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedUser);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        serviceUser.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Lister tous les utilisateurs (ou par rôle)
    @GetMapping
    public List<User> getAllUsers(@RequestParam(value = "role", required = false) Role role) {
        if (role == null) {
            return serviceUser.getAllUsers();
        } else {
            return serviceUser.getUsersByRole(role);
        }
    }

    // Récupérer le profil de l'utilisateur connecté
    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUserProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = principal.getName(); // Récupère le nom d’utilisateur authentifié
        User user = serviceUser.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Connexion manuelle (pour test ou fallback)
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User user = serviceUser.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide");
        }

        return ResponseEntity.ok("Connexion réussie");
    }

    // 🔗 Nouvelle route : Lier KeycloakId à un utilisateur local
    @PostMapping("/link-keycloak")
    public ResponseEntity<User> linkKeycloakId(@RequestBody Map<String, String> payload) {
        String keycloakId = payload.get("keycloakId");
        String email = payload.get("email");

        User linkedUser = serviceUser.saveKeycloakId(keycloakId, email);
        return ResponseEntity.ok(linkedUser);
    }

    // Convertir DTO → Entity
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }
    @GetMapping("/by-keycloak-id/{keycloakId}")
    public ResponseEntity<UserDTO> getUserBykeycloakId(@PathVariable String keycloakId) {
        User user = serviceUser.getUserBykeycloakId(keycloakId);
        UserDTO dto = convertToDTO(user);
        return ResponseEntity.ok(dto);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getFileName()
        );    }
    @GetMapping("/by-role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        List<User> users = serviceUser.getUsersByRole(Role.valueOf(role));
        List<UserDTO> userDTOS = users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getFileName()
                ))
                .toList();
        return ResponseEntity.ok(userDTOS);
    }
}