package com.project.ModuleReclamation.controller;

import com.project.ModuleReclamation.dto.ReclamationDTO;
import com.project.ModuleReclamation.entities.Reclamation;
import com.project.ModuleReclamation.service.IServiceReclamation;
import com.project.ModuleShared.feign.MachineClient;
import com.project.ModuleShared.dto.MachineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/Reclamation")
public class ReclamationController {

    @Autowired
    private IServiceReclamation reclamationService;

    @Autowired
    private MachineClient machineClient; // ← AJOUT DU CLIENT MACHINE

    @PostMapping
    public Reclamation addReclamation(@RequestBody Reclamation reclamation, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId = jwt.getSubject(); // l'UUID du user
        String firstName = jwt.getClaim("given_name");
        String lastName = jwt.getClaim("family_name");

        String fullName = firstName + " " + lastName;

        reclamation.setIdUtilisateurCreateur(userId);
        reclamation.setDemandeur(fullName);

        // ✅ CONVERSION RÉFÉRENCE → ID MACHINE AVANT SAUVEGARDE
        if (reclamation.getReference() != null && !reclamation.getReference().isEmpty()) {
            try {
                System.out.println("🔍 Recherche de la machine avec référence: " + reclamation.getReference());

                // Récupérer toutes les machines
                List<MachineDTO> machines = machineClient.getAllMachines();
                System.out.println("📋 Nombre de machines récupérées: " + machines.size());

                // Trouver la machine avec cette référence
                Optional<MachineDTO> machineOptional = machines.stream()
                        .filter(machine -> machine.getReference().equals(reclamation.getReference()))
                        .findFirst();

                if (machineOptional.isPresent()) {
                    MachineDTO machine = machineOptional.get();

                    // ✅ REMPLIR LE CHAMP IDMACHINE AVANT SAUVEGARDE
                    reclamation.setIdmachine(machine.getId());

                    System.out.println("✅ Machine trouvée !");
                    System.out.println("   - ID: " + machine.getId());
                    System.out.println("   - Référence: " + machine.getReference());
                    System.out.println("   - Nom: " + machine.getName());
                    System.out.println("📝 Réclamation.idmachine défini à: " + reclamation.getIdmachine());

                } else {
                    System.out.println("❌ Aucune machine trouvée avec la référence: " + reclamation.getReference());
                    System.out.println("📋 Références disponibles:");
                    machines.forEach(m -> System.out.println("   - " + m.getReference()));

                    // Optionnel: lancer une exception
                    // throw new RuntimeException("Machine introuvable avec la référence: " + reclamation.getReference());
                }
            } catch (Exception e) {
                System.out.println("❌ Erreur lors de la récupération des machines: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ Référence machine vide ou null");
        }

        // Debug avant sauvegarde
        System.out.println("💾 AVANT SAUVEGARDE:");
        System.out.println("   - Référence: " + reclamation.getReference());
        System.out.println("   - ID Machine: " + reclamation.getIdmachine());
        System.out.println("   - Demandeur: " + reclamation.getDemandeur());

        // Sauvegarder avec l'ID machine déjà défini
        Reclamation savedReclamation = reclamationService.addReclamation(reclamation);

        // Debug après sauvegarde pour vérifier
        System.out.println("💾 APRÈS SAUVEGARDE:");
        System.out.println("   - ID Réclamation: " + savedReclamation.getId());
        System.out.println("   - Référence: " + savedReclamation.getReference());
        System.out.println("   - ID Machine: " + savedReclamation.getIdmachine());

        return savedReclamation;
    }

    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "priorite", required = false) String priorite,
            @RequestParam(value = "dateDebut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(value = "dateFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        if (status == null && priorite == null && dateDebut == null && dateFin == null) {
            return ResponseEntity.ok(reclamationService.findAll());
        }

        List<Reclamation> reclamations = reclamationService.getAllReclamationsWithFilters(status, priorite, dateDebut, dateFin);
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @GetMapping("/reclamation/{id}")
    public ResponseEntity<ReclamationDTO> fetchReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.findById(id);
        return ResponseEntity.ok(reclamationService.convertToDTO(reclamation));
    }

    @PutMapping("/{id}")
    public Reclamation updateReclamation(@PathVariable Long id, @RequestBody Reclamation updatedReclamation) {
        return reclamationService.updateReclamation(id, updatedReclamation);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
    }

    @GetMapping("/status/{status}")
    public List<Reclamation> getReclamationsByStatus(@PathVariable String status) {
        return reclamationService.getReclamationsByStatus(status);
    }

    @GetMapping("/type/{type}")
    public List<Reclamation> getReclamationsByType(@PathVariable String type) {
        return reclamationService.getReclamationsByType(type);
    }

    @GetMapping("/priorite/{priorite}")
    public List<Reclamation> getReclamationsByPriorite(@PathVariable String priorite) {
        return reclamationService.getReclamationsByPriorite(priorite);
    }

    @GetMapping("/stats/monthly")
    public List<Map<String, Object>> getReclamationsPerMonth() {
        List<Object[]> results = reclamationService.countReclamationsGroupByMonth();
        List<Map<String, Object>> data = new ArrayList<>();

        for (Object[] row : results) {
            String month = (String) row[0];
            Long count = ((Number) row[1]).longValue();
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

    @GetMapping("/technicien/{keycloakId}")
    public ResponseEntity<List<Reclamation>> getReclamationsByTechnicien(@PathVariable String keycloakId) {
        try {
            List<Reclamation> reclamations = reclamationService.findByIdTechnicienAssigne(keycloakId);
            return ResponseEntity.ok(reclamations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/assigner-technicien/{technicienId}")
    public ResponseEntity<String> assignerTechnicien(
            @PathVariable("id") Long idReclamation,
            @PathVariable("technicienId") String idTechnicien) {

        if (idReclamation == null || idReclamation <= 0) {
            return ResponseEntity.badRequest().body("ID de réclamation invalide");
        }

        if (idTechnicien == null || idTechnicien.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("ID du technicien manquant");
        }

        try {
            reclamationService.assignerTechnicien(idReclamation, idTechnicien);
            return ResponseEntity.ok("Réclamation assignée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur métier : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur système lors de l'assignation : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        try {
            Reclamation reclamation = reclamationService.findById(id);
            return ResponseEntity.ok(reclamation);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        System.out.println("🔥🔥🔥 CONTRÔLEUR RÉCLAMATION APPELÉ !!! ID: " + id);

        String newStatus = statusUpdate.get("status");

        if (newStatus == null || newStatus.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Le statut est manquant.");
            return ResponseEntity.badRequest().body(error);
        }

        try {
            Reclamation reclamation = reclamationService.getById(id);
            if (reclamation == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Réclamation introuvable.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            // ✅ LOGIQUE DE MISE À JOUR DU STATUT MACHINE
            if (reclamation.getIdmachine() != null) {
                try {
                    Map<String, String> machineStatusPayload = new HashMap<>();

                    // Définir le statut machine selon le statut réclamation
                    if ("En cours".equals(newStatus)) {
                        machineStatusPayload.put("status", "En panne");
                    } else if ("Résolue".equals(newStatus)) {
                        machineStatusPayload.put("status", "Disponible");
                    }

                    // Mettre à jour le statut de la machine
                    if (!machineStatusPayload.isEmpty()) {
                        machineClient.updateMachineStatus(reclamation.getIdmachine(), machineStatusPayload);
                        System.out.println("✅ Statut machine mis à jour !");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Erreur mise à jour machine: " + e.getMessage());
                }
            }

            // Mettre à jour le statut de la réclamation
            reclamationService.updateStatus(id, newStatus);

            Map<String, String> success = new HashMap<>();
            success.put("message", "Statut mis à jour avec succès.");
            success.put("newStatus", newStatus);
            return ResponseEntity.ok(success);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la mise à jour : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/by-operateur/{idUtilisateur}")
    public List<Reclamation> getReclamationsByOperateur(@PathVariable String idUtilisateur) {
        return reclamationService.getReclamationsByOperateur(idUtilisateur);
    }
}