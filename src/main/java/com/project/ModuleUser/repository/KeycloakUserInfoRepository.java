package com.project.ModuleUser.repository;

import com.project.ModuleUser.entities.KeycloakUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeycloakUserInfoRepository extends JpaRepository<KeycloakUserInfo, String> {
    Optional<KeycloakUserInfo> findById(String id);
    boolean existsByEmail(String email); // pour éviter les doublons


}
