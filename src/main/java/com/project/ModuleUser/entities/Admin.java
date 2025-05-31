package com.project.ModuleUser.entities;
import jakarta.persistence.Entity;

@Entity
//@NoArgsConstructor

public class Admin extends User{
    private String adminRights;
    private String department;
    public Admin() {
    }

    public Admin(String username, String password, String email, String adminRights) {
        super(username, password, email);
        this.adminRights = adminRights;
    }



    public String getAdminRights() {
        return adminRights;
    }

    public void setAdminRights(String adminRights) {
        this.adminRights = adminRights;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
