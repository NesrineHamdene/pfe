package com.project.ModuleUser.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.ModuleUser.enums.Role;
import lombok.EqualsAndHashCode;

//@JsonTypeName("ADMIN")
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = AdminDTO.class, name = "ADMIN"),
//        @JsonSubTypes.Type(value = OperateurMachineDTO.class, name = "OPERATEUR_MACHINE"),
//        @JsonSubTypes.Type(value = TechnicienMaintenanceDTO.class, name = "TECHNICIEN_MAINTENANCE"),
//        @JsonSubTypes.Type(value = ResponsableQualiteDTO.class, name = "RESPONSABLE_QUALITE")
//
//})
//@EqualsAndHashCode(callSuper = true)

public class AdminDTO extends  UserDTO {

    private String adminRights;
    private String department;
    private String password;
    // Constructeurs
    public AdminDTO() {
    }

    public AdminDTO(Long id, String username, String email, Role role, String adminRights, String department, String password, String fileName) {
        super(id, username, email, role, password,fileName );
        this.adminRights = adminRights;
        this.department = department;


    }

    // Getters et setters
    public String getAdminRights() {return adminRights;}

    public void setAdminRights(String adminRights) {this.adminRights = adminRights;}

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}


    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

}