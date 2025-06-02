package com.project.ModuleUser.repository;

import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role); // Nouvelle méthode pour filtrer par rôle
    Optional<User> findByKeycloakId(String keycloakId);
//    boolean existsByUsername(String username);
//
//    boolean existsByEmail(String email);
}