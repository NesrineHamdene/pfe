package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.UserDTO;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceUser implements IServiceUser {

    @Autowired
    private UserRepository userRepository;

    public ServiceUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Lie un utilisateur local à son ID Keycloak
     */
    public User saveKeycloakId(String keycloakId, String email) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé via l'email"));
                    user.setKeycloakId(keycloakId);
                    return userRepository.save(user);
                });
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDTO updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setPassword(updatedUser.getPassword());
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<UserDTO> getTechniciens() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(Role.TECHNICIEN_MAINTENANCE))
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getFileName()
                ))
                .collect(Collectors.toList());
    }
    public User getUserBykeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

}