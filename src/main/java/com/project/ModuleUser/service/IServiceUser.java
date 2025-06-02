package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.UserDTO;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;

import java.util.List;
import java.util.Optional;

public interface IServiceUser {
    User findByUsername(String username);
    User createUser(User user);
    User updateUser(Long id, UserDTO updatedUser);
    void deleteUser(Long id);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    Optional<User> findById(Long id);
    List<User> getUsersByRole(Role role); // Nouvelle méthode
    List<UserDTO> getTechniciens();

    User saveKeycloakId(String keycloakId, String email);

    User getUserBykeycloakId(String keycloakId);
    //User saveUserWithPhoto(User user, MultipartFile photo) throws IOException;

//    boolean existsByUsername(String username); // Vérifier si un utilisateur existe par son username
//    boolean existsByEmail(String email); // Vérifier si un utilisateur existe par son email

}
