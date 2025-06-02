package com.project.ModuleUser.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.project.ModuleUser.enums.Role;
import lombok.EqualsAndHashCode;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN"),
//        @JsonSubTypes.Type(value = TechnicienMaintenanceDTO.class, name = "TECHNICIEN_MAINTENANCE"),
//        @JsonSubTypes.Type(value = OperateurMachineDTO.class, name = "OPERATEUR_MACHINE"),
//
//})
public class UserDTO {
    private Long id;
    private String keycloakId;

    private String username;
    private String email;
    private String password;
    private Role role;
    private String fileName;

    // Constructeurs
    public UserDTO() {}

    public UserDTO(Long id, String username, String email, Role role,String password, String fileName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.fileName = fileName;

    }

    public String getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }

    public UserDTO(Long id, String username, String email, Role role, String fileName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.fileName = fileName;
    }

    // Getters et setters
    public Long getId() {return id;}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setId(Long id) {this.id = id;}


    public String getUsername() {return username;}


    public void setUsername(String username) {this.username = username;}


    public String getEmail() {return email;}


    public void setEmail(String email) {this.email = email;}


    public Role getRole() {return role;}


    public void setRole(Role role) { this.role = role;}


    public String getPassword() {  return password;}
    public void setPassword(String password) {this.password = password;}
}
