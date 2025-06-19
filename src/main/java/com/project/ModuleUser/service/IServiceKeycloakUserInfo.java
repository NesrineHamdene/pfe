package com.project.ModuleUser.service;

import com.project.ModuleUser.entities.KeycloakUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public interface IServiceKeycloakUserInfo {
    void registerUserFromJwt(Jwt jwt);
    List<KeycloakUserInfo> getAllUsers();
    List<KeycloakUserInfo> getTechniciens();

    void deleteUserById(String id);

    KeycloakUserInfo getUserById(String id);

}
