package com.project.ModuleReclamation.controller;

import com.project.ModuleReclamation.dto.ReclamationDTO;
import com.project.ModuleReclamation.entities.Reclamation;
import com.project.ModuleReclamation.service.IServiceReclamation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@RestController
//@AllArgsConstructor
//@NoArgsConstructor
@RequestMapping("/api/Reclamation")
public class ReclamationController {
@Autowired
    private IServiceReclamation reclamationService;
    @GetMapping("/{id}")
    public ResponseEntity<ReclamationDTO> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.findById(id);
        return ResponseEntity.ok(reclamationService.convertToDTO(reclamation));
    }

    // Ajouter une réclamation
    @PostMapping
    public Reclamation addReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.addReclamation(reclamation);
    }
    // Récupérer toutes les réclamations
    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "priorite", required = false) String priorite,
            @RequestParam(value = "dateDebut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(value = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        List<Reclamation> reclamations = reclamationService.getAllReclamationsWithFilters(status, priorite, dateDebut, dateFin);
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }
    // Récupérer une réclamation par son ID
    @GetMapping("/reclamation/{id}")
    public ResponseEntity<ReclamationDTO> fetchReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.findById(id);
        return ResponseEntity.ok(reclamationService.convertToDTO(reclamation));
    }

    // Mettre à jour une réclamation
    @PutMapping("/{id}")
    public Reclamation updateReclamation(@PathVariable Long id, @RequestBody Reclamation updatedReclamation) {
        return reclamationService.updateReclamation( id, updatedReclamation);
    }

    // Supprimer une réclamation
    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
    }
    // Rechercher par statut
    @GetMapping("/status/{status}")
    public List<Reclamation> getReclamationsByStatus(@PathVariable String status) {
        return reclamationService.getReclamationsByStatus(status);
    }

    // Rechercher par type
    @GetMapping("/type/{type}")
    public List<Reclamation> getReclamationsByType(@PathVariable String type) {
        return reclamationService.getReclamationsByType(type);
    }

    // Rechercher par priorité
    @GetMapping("/priorite/{priorite}")
    public List<Reclamation> getReclamationsByPriorite(@PathVariable String priorite) {
        return reclamationService.getReclamationsByPriorite(priorite);
    }

    // Rechercher par nom de machine
    //@GetMapping("/machine/{nom}")
    //public List<Reclamation> getReclamationsByMachineNom(@PathVariable String nom) {
        //return reclamationService.getReclamationsByMachineNom(nom);
    //}
    @GetMapping("/stats/monthly")
    public List<Map<String, Object>> getReclamationsPerMonth() {
        List<Object[]> results = reclamationService.countReclamationsGroupByMonth();

        List<Map<String, Object>> data = new ArrayList<>();
        for (Object[] row : results) {
            String month = (String) row[0]; // 👉 ici c’est bien une String "2024-04"
            Long count = ((Number) row[1]).longValue(); // cast safe

            Map<String, Object> item = new HashMap<>();
            item.put("month", month);
            item.put("reclamationCount", count);
            data.add(item);
        }

        return data;
    }


    @GetMapping("/stats/by-user")
    public List<Map<String, Object>> getReclamationsParUser() {
        List<Object[]> result = reclamationService.countReclamationsByDemandeur();
        List<Map<String, Object>> data = new ArrayList<>();
        for (Object[] row : result) {
            Map<String, Object> item = new HashMap<>();
            item.put("user", row[0]);
            item.put("count", row[1]);
            data.add(item);
        }
        return data;
    }
}
