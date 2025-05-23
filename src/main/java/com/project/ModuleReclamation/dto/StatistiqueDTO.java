package com.project.ModuleReclamation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data

public class StatistiqueDTO {

    private int total;
    private int enCours;
    private int resolues;
    private int fermees;

    private double pourcentageEnAttente;
    private double pourcentageResolues;
    private double pourcentageFermees;

    private double variationTotal;
    private double variationEnCours;
    private double variationResolues;
    private double variationFermees;

    private EvolutionMensuelle evolutionMensuelle;

    private List<TypeStat> types;
    private List<DemandeurStat> topDemandeurs;

    @Data

    public static class EvolutionMensuelle {
        private List<Integer> attente;
        private List<Integer> resolues;
        private List<Integer> fermees;
    }

    @Data

    public static class TypeStat {
        private String type;
        private int count;
    }

    @Data

    public static class DemandeurStat {
        private String nom;
        private int count;
    }

    public int getTotal() {return total;}

    public void setTotal(int total) {this.total = total;}

    public int getEnCours() {return enCours;}

    public void setEnCours(int enCours) {this.enCours = enCours;}

    public int getResolues() {return resolues;}

    public void setResolues(int resolues) {this.resolues = resolues;}

    public int getFermees() {return fermees;}

    public void setFermees(int fermees) {this.fermees = fermees;}

    public double getPourcentageEnAttente() {return pourcentageEnAttente;}

    public void setPourcentageEnAttente(double pourcentageEnAttente) {this.pourcentageEnAttente = pourcentageEnAttente;}

    public double getPourcentageResolues() {return pourcentageResolues;}

    public void setPourcentageResolues(double pourcentageResolues) {this.pourcentageResolues = pourcentageResolues;}

    public double getPourcentageFermees() {return pourcentageFermees;}

    public void setPourcentageFermees(double pourcentageFermees) {this.pourcentageFermees = pourcentageFermees;}

    public double getVariationTotal() {return variationTotal;}

    public void setVariationTotal(double variationTotal) {this.variationTotal = variationTotal;}

    public double getVariationEnCours() {return variationEnCours;}

    public void setVariationEnCours(double variationEnCours) {this.variationEnCours = variationEnCours;}

    public EvolutionMensuelle getEvolutionMensuelle() {return evolutionMensuelle;}

    public void setEvolutionMensuelle(EvolutionMensuelle evolutionMensuelle) {this.evolutionMensuelle = evolutionMensuelle;}

    public double getVariationFermees() {return variationFermees;}

    public void setVariationFermees(double variationFermees) {this.variationFermees = variationFermees;}

    public double getVariationResolues() {return variationResolues;}

    public void setVariationResolues(double variationResolues) {this.variationResolues = variationResolues;}

    public List<TypeStat> getTypes() {return types;}

    public void setTypes(List<TypeStat> types) {this.types = types;}

    public List<DemandeurStat> getTopDemandeurs() {return topDemandeurs;}

    public void setTopDemandeurs(List<DemandeurStat> topDemandeurs) {this.topDemandeurs = topDemandeurs;}
}

