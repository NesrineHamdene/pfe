package com.project.ModuleUser.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
//@NoArgsConstructor
@Table(name = "responsable_production")

public class ResponsableProduction extends User{
    private String departement;
    public ResponsableProduction() {}
    public ResponsableProduction(String departement) {this.departement = departement;}
    // Exemple : "Qualité", "Contrôle", "Amélioration continue"


    public String getDepartement() {return departement;}

    public void setDepartement(String departement) {this.departement = departement;}

}
