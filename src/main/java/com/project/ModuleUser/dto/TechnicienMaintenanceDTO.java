package com.project.ModuleUser.dto;

import com.project.ModuleUser.enums.Role;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) // Utilise callSuper pour inclure les champs de la classe parent

public class TechnicienMaintenanceDTO extends UserDTO {
    private Long id;
    private String username;
    private String email;
    private String status;
    private String rapport;
    private String department;
    private String password;

    public TechnicienMaintenanceDTO() {}
    public TechnicienMaintenanceDTO(Long id, String username, String email, String status, String department) {
        this.email = email;
        this.username = username;
        this.status = status;
        this.id = id;
        this.department = department;

    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getRapport() {return rapport;}

    public void setRapport(String rapport) {this.rapport = rapport;}

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}


    public void setRole(Role role) {

    }
}

