package com.project.ModuleUser.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)

public class OperateurMachineDTO extends UserDTO{
    private String departement;
    private String status;
    private String machineType;
    public OperateurMachineDTO(String status, String machineType) {

        this.status = status;
        this.machineType = machineType;
    }

    public OperateurMachineDTO() {}


    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {this.machineType = machineType;}

    public String getDepartement() {
        return departement;
    }
    public void setDepartement(String departement) {this.departement = departement;}


}
