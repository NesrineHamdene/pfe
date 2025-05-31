package com.project.ModuleUser.controller;

import com.project.ModuleUser.dto.TechnicienMaintenanceDTO;
import com.project.ModuleUser.entities.TechnicienMaintenance;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import com.project.ModuleUser.service.IServiceTechnicienMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technicien")

public class TechnicienMaintenanceController {
   @Autowired
    private IServiceTechnicienMaintenance serviceTechnicienMaintenance;
    private final UserRepository userRepository;

    public TechnicienMaintenanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profil/{username}")
         public ResponseEntity<TechnicienMaintenanceDTO> getProfil(@PathVariable String username) {
             TechnicienMaintenanceDTO technicienDTO = serviceTechnicienMaintenance.getTechnicienByUsername(username);
                 return ResponseEntity.ok(technicienDTO);
}
    // ✏️ 2. Modifier son propre profil (par username)
    @PutMapping("/profil/{username}")
    public ResponseEntity<TechnicienMaintenanceDTO> updateProfil(@PathVariable String username, @RequestBody TechnicienMaintenanceDTO technicienDTO) {
        TechnicienMaintenanceDTO updatedTechnicien = serviceTechnicienMaintenance.updateTechnicienByUsername(username, technicienDTO);
        return ResponseEntity.ok(updatedTechnicien);
    }
    @PostMapping("/ajouter-technicien")
    public ResponseEntity<User> createTechnicien(@RequestBody TechnicienMaintenanceDTO technicienDTO) {
        // Créer un technicien qui est une instance de TechnicienMaintenance (hérite de User)
        TechnicienMaintenance technicien = new TechnicienMaintenance();

        // Mapper les informations du DTO vers l'entité TechnicienMaintenance (et User)
        technicien.setUsername(technicienDTO.getUsername());
        technicien.setEmail(technicienDTO.getEmail());
        technicien.setPassword(technicienDTO.getPassword());
        technicien.setRole(Role.TECHNICIEN_MAINTENANCE); // Assurer que c'est un technicien
        technicien.setDepartement(technicienDTO.getDepartment()); // Assigner le département
        technicien.setStatus(technicienDTO.getStatus());
        technicien.setRapport(technicienDTO.getRapport());


        // Sauvegarder le technicien (User + infos spécifiques à TechnicienMaintenance) dans la table `user`
        User savedTechnicien = userRepository.save(technicien);  // Utiliser userRepository au lieu de technicienMaintenanceRepository

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTechnicien);
    }

//    // Récupérer les réclamations assignées au technicien
//    @GetMapping("/{id}/reclamations")
//    public List<Reclamation> getAssignedReclamations(@PathVariable Long id) {
//        return technicienMaintenanceService.getAssignedReclamations(id);
//    }
//    // Mettre à jour le statut d'une réclamation
//    @PutMapping("/reclamation/{id}/status")
//    public void updateReclamationStatus(@PathVariable Long id, @RequestParam String status) {
//        technicienMaintenanceService.updateReclamationStatus(id, status);
//    }
//
//    // Ajouter un rapport de maintenance
//    @PostMapping("/reclamation/{id}/report")
//    public void addMaintenanceReport(@PathVariable Long id, @RequestParam String report) {
//        technicienMaintenanceService.addMaintenanceReport(id, report);
//    }
//
//    // Filtrer les réclamations par priorité
//    @GetMapping("/reclamations/priorite/{priorite}")
//    public List<Reclamation> filterReclamationsByPriority(@PathVariable String priorite) {
//        return technicienMaintenanceService.filterReclamationsByPriority(priorite);
//    }


}
