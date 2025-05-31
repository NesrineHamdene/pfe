package com.project.ModuleUser.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)


public class ResponsableProductionDTO extends UserDTO {
    private String departement;

    public String getDepartement() {return departement;}

    public void setDepartement(String departement) {this.departement = departement;}
}
