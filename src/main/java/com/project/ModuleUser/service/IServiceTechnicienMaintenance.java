package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.TechnicienMaintenanceDTO;

public interface IServiceTechnicienMaintenance {
    TechnicienMaintenanceDTO getTechnicienByUsername(String username);
    TechnicienMaintenanceDTO updateTechnicienByUsername(String username, TechnicienMaintenanceDTO technicienDTO);
    // Nouvelle méthode pour le signin
//    List<Reclamation> getAssignedReclamations(Long technicienId);
//    void updateReclamationStatus(Long reclamationId, String newStatus);
//    void addMaintenanceReport(Long reclamationId, String report);
//    List<Reclamation> filterReclamationsByPriority(String priority);
}
