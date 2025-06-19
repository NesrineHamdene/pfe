package com.project.ModuleUser.dto;

public class KeycloakUserInfoDTO {
    private String id; // correspond à "sub" dans le token

    private String preferredUsername; // "preferred_username"
    private String email;             // "email"
    private String firstName;         // "given_name"
    private String lastName;          // "family_name"
    private String fullName;          // "name"
    private String role;              // "realm_access" > "roles" → ex: TECHNICIEN MAINTENANCE

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreferredUsername() {
        return preferredUsername;
    }

    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
