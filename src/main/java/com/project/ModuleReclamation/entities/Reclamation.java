package com.project.ModuleReclamation.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
    public class Reclamation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String date;
        private String type;
        private String description;
        private String status;
        private String priorite;
        private String demandeur;
        private String ligne;
        private String reference;
        private String impactProduction;



    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getImpactProduction() {
        return impactProduction;
    }

    public void setImpactProduction(String impactProduction) {
        this.impactProduction = impactProduction;
    }

    public String getDate() {return date;}

    public String getLigne() {return ligne;}

    public void setLigne(String ligne) {this.ligne = ligne;}

    public String getReference() {return reference;}

    public void setReference(String reference) {this.reference = reference;}

    public void setDate(String date) {this.date = date;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getPriorite() {return priorite;}

    public void setPriorite(String priorite) {this.priorite = priorite;}

    public String getDemandeur() {return demandeur;}

    public void setDemandeur(String demandeur) {this.demandeur = demandeur;}

}
