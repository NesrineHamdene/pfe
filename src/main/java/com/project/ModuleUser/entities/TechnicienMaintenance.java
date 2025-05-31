package com.project.ModuleUser.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
//@NoArgsConstructor
@DiscriminatorValue("TECHNICIEN_MAINTENANCE")  // Valeur spécifique pour le technicien

public class TechnicienMaintenance extends User{
    private String departement;
    private String status;
    private String rapport;
    public TechnicienMaintenance() {}

    // Constructeur avec paramètres
    public TechnicienMaintenance(String departement, String status, String rapport) {
        this.departement = departement;
        this.status = status;
        this.rapport = rapport;
    }
    public void setDepartement(String departement) {this.departement = departement;}

    public String getDepartement() {return departement;}


    public void setStatus(String status) {this.status = status;}
    public String getStatus() {return status;}



    public String getRapport() { return rapport;}
    public void setRapport(String rapport) {this.rapport = rapport;}
}
