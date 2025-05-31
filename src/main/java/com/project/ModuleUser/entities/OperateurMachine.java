package com.project.ModuleUser.entities;

import com.project.ModuleUser.enums.Role;
import jakarta.persistence.Entity;

@Entity
//@NoArgsConstructor
public class OperateurMachine extends User{
    private String departement;
    private String status;
    private String machineType;
    public OperateurMachine() {}
    public OperateurMachine(String username, String password, String email, String status, String machineType, String departement  ) {
        super(username, password, email);
        this.status = status;
        this.machineType = machineType;
        this.departement = departement;
        this.setRole(Role.OPERATEUR_MACHINE);
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {return status;}


    public String getMachineType() {return machineType;}

    public void setMachineType(String machineType) {this.machineType = machineType;}


    public String getDepartement() {return departement;}

    public void setDepartement(String department) {this.departement = departement;}

}

