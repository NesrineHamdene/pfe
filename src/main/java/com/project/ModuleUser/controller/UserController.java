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
import java.util.Optional;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired
    private IServiceUser serviceUser;
    @GetMapping("/techniciens")
    public List<UserDTO> getTechniciens() {
        return serviceUser.getUsersByRole(Role.TECHNICIEN_MAINTENANCE)
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), null, user.getFileName()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = serviceUser.createUser(convertToEntity(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = serviceUser.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return Optional.ofNullable(serviceUser.findByUsername(username));
    }

    @GetMapping("/email/{email}")
    public HttpEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        Optional<User> user = serviceUser.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        User updatedUser = serviceUser.updateUser(id, updatedUserDTO);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        serviceUser.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(value = "role", required = false) Role role) {
        if (role == null) {
            return serviceUser.getAllUsers();
        } else {
            return serviceUser.getUsersByRole(role);
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUserProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = principal.getName(); // Récupérer le nom d'utilisateur de l'utilisateur authentifié
        User user = serviceUser.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Méthodes de conversion
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User user = serviceUser.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide");
        }

        return ResponseEntity.ok("Connexion réussie");
    }


//    @PostMapping("/upload")
//    public ResponseEntity<?> addUserWithPhoto(
//            @RequestParam("username") String username, // Changement de userName à username
//            @RequestParam("email") String email,
//            @RequestParam("role") Role role,         // Changement de type à role
//            @RequestParam("password") String password,
//            @RequestParam("confirmPassword") String confirmPassword,
//            @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
//
//        if (!password.equals(confirmPassword)) {
//            return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas");
//        }
//
//        User user = new User();
//        user.setUsername(username);
//        user.setEmail(email);
//        user.setRole(role);
//        user.setPassword(password);
//
//        serviceUser.saveUserWithPhoto(user, photo);
//
//        return ResponseEntity.ok("Utilisateur enregistré avec succès avec image.");
//    }
//
}



//    // Vérifier si un utilisateur existe par son username
//    @GetMapping("/exists/username/{username}")
//    public boolean existsByUsername(@PathVariable String username) {
//        return serviceUser.existsByUsername(username);
//    }
//
//    // Vérifier si un utilisateur existe par son email
//    @GetMapping("/exists/email/{email}")
//    public boolean existsByEmail(@PathVariable String email) {
//        return serviceUser.existsByEmail(email);
//    }


