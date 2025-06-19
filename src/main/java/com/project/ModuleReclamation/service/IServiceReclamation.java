package com.project.ModuleReclamation.service;

import com.project.ModuleReclamation.dto.ReclamationDTO;
import com.project.ModuleReclamation.entities.Reclamation;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IServiceReclamation {
    Reclamation addReclamation(Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    Optional<Reclamation> getReclamationById(Long id);
    Reclamation updateReclamation(Long id, Reclamation updatedReclamation);
    void deleteReclamation(Long id);
    List<Reclamation> getReclamationsByStatus(String status);
    //List<Reclamation> getReclamationsByMachineNom(String nom);
    List<Reclamation> getReclamationsByType(String type); // Recherche par type
    List<Reclamation> getReclamationsByPriorite(String priorite);  // Recherche par priorité

    ReclamationDTO convertToDTO(Reclamation reclamation);

    Reclamation findById(Long id);
    List<Object[]> countReclamationsGroupByMonth();
    List<Object[]> countReclamationsByDemandeur();

    List<Reclamation> getAllReclamationsWithFilters(String status, String priorite, LocalDate dateDebut, LocalDate dateFin);
    List<Reclamation> getReclamationsByTechnicien(String idTechnicien);

    void assignerTechnicien(Long idReclamation, String idTechnicien);

    List<Reclamation> findByIdTechnicienAssigne(String keycloakId);

    void updateStatus(Long id, String newStatus);

    List<Reclamation> findAll();

    Reclamation save(Reclamation reclamation);

    List<Reclamation> getReclamationsByOperateur(String idUtilisateur);

    Reclamation getById(Long id);
    //List<Reclamation> getReclamationsByDemandeur(String keycloakId);

}
