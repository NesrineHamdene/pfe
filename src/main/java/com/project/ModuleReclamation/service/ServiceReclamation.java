package com.project.ModuleReclamation.service;

import com.project.ModuleReclamation.dto.ReclamationDTO;
import com.project.ModuleReclamation.entities.Reclamation;
import com.project.ModuleReclamation.feign.NotificationFeignClient;
import com.project.ModuleReclamation.repository.ReclamationRepository;
import com.project.ModuleShared.dto.KeycloakUserInfoDTO;
import com.project.ModuleShared.dto.NotificationRequest;
import com.project.ModuleShared.enums.NotificationChannel;
import com.project.ModuleShared.enums.NotificationPriority;
import com.project.ModuleShared.enums.NotificationType;
import com.project.ModuleShared.feign.MachineClient;
import com.project.ModuleShared.feign.UserServiceClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceReclamation implements IServiceReclamation {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private NotificationFeignClient notificationFeignClient;
    private final MachineClient machineClient;


    public ServiceReclamation(MachineClient machineClient) {
        this.machineClient = machineClient;
    }

    public ReclamationDTO convertToDTO(Reclamation reclamation) {
        ReclamationDTO dto = new ReclamationDTO();
        dto.setId(reclamation.getId());
        dto.setDate(reclamation.getDate());
        dto.setType(reclamation.getType());
        dto.setDescription(reclamation.getDescription());
        dto.setStatus(reclamation.getStatus());
        dto.setPriorite(reclamation.getPriorite());
        dto.setReference(reclamation.getReference());
        dto.setLigne(reclamation.getLigne());
        dto.setImpactProduction(reclamation.getImpactProduction());
        dto.setIdmachine(reclamation.getIdmachine());
        return dto;
    }

    @Override
    public Reclamation findById(Long id) {
        return reclamationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id " + id));
    }

    @Override
    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Optional<Reclamation> getReclamationById(Long id) {
        return reclamationRepository.findById(id);
    }

    @Override
    public Reclamation updateReclamation(Long id, Reclamation updatedReclamation) {
        Optional<Reclamation> existingReclamationOpt = reclamationRepository.findById(id);
        if (existingReclamationOpt.isPresent()) {
            Reclamation existingReclamation = existingReclamationOpt.get();
            existingReclamation.setDate(updatedReclamation.getDate());
            existingReclamation.setType(updatedReclamation.getType());
            existingReclamation.setDescription(updatedReclamation.getDescription());
            existingReclamation.setStatus(updatedReclamation.getStatus());
            existingReclamation.setPriorite(updatedReclamation.getPriorite());
            existingReclamation.setDemandeur(updatedReclamation.getDemandeur());
            existingReclamation.setReference(updatedReclamation.getReference());
            existingReclamation.setLigne(updatedReclamation.getLigne());
            existingReclamation.setImpactProduction(updatedReclamation.getImpactProduction());
            existingReclamation.setIdTechnicienAssigne(updatedReclamation.getIdTechnicienAssigne());
            existingReclamation.setIdmachine(updatedReclamation.getIdmachine());
            return reclamationRepository.save(existingReclamation);
        } else {
            throw new RuntimeException("Réclamation avec l'ID " + id + " non trouvée.");
        }
    }

    @Override
    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

    @Override
    public List<Reclamation> getReclamationsByStatus(String status) {
        return reclamationRepository.findByStatus(status);
    }

    @Override
    public List<Reclamation> getReclamationsByType(String type) {
        return reclamationRepository.findByType(type);
    }

    @Override
    public List<Reclamation> getReclamationsByPriorite(String priorite) {
        return reclamationRepository.findByPriorite(priorite);
    }

    @Override
    public List<Object[]> countReclamationsGroupByMonth() {
        return reclamationRepository.countReclamationsGroupByMonth();
    }

    @Override
    public List<Object[]> countReclamationsByDemandeur() {
        return reclamationRepository.countReclamationsByDemandeur();
    }

    @Override
    public List<Reclamation> getAllReclamationsWithFilters(String status, String priorite, LocalDate dateDebut, LocalDate dateFin) {
        String dateDebutStr = dateDebut != null ? dateDebut.toString() : null;
        String dateFinStr = dateFin != null ? dateFin.toString() : null;
        return reclamationRepository.findWithFilters(status, priorite, dateDebutStr, dateFinStr);
    }

    @Override
    public List<Reclamation> getReclamationsByTechnicien(String idTechnicien) {
        return reclamationRepository.findByIdTechnicienAssigne(idTechnicien);
    }

    @Override
    public void assignerTechnicien(Long idReclamation, String idTechnicien) {
        try {
            KeycloakUserInfoDTO technicien = userServiceClient.getUserById(idTechnicien);
            Reclamation reclamation = findById(idReclamation);
            reclamation.setIdTechnicienAssigne(idTechnicien);
            reclamationRepository.save(reclamation);
            envoyerNotificationAssignation(reclamation, technicien);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'assignation : " + e.getMessage());
        }
    }

    @Override
    public List<Reclamation> findByIdTechnicienAssigne(String keycloakId) {
        return reclamationRepository.findByIdTechnicienAssigne(keycloakId);
    }

    @Override
    public void updateStatus(Long reclamationId, String newStatus) {
        System.out.println("updateStatus appelé avec newStatus = " + newStatus);
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));

        reclamation.setStatus(newStatus);
        reclamationRepository.save(reclamation);

        // Mise à jour du statut machine en fonction du statut de réclamation
        Map<String, String> payload = new HashMap<>();

        if ("En cours".equalsIgnoreCase(newStatus)) {
            payload.put("status", "EN_MAINTENANCE");
        } else if ("Résolue".equalsIgnoreCase(newStatus)) {
            payload.put("status", "Active");
        }

        if (!payload.isEmpty()) {
            System.out.println("📣 Appel Feign vers updateMachineStatus pour machine ID: " + reclamation.getIdmachine()
                    + " avec le statut: " + payload.get("status"));
            machineClient.updateMachineStatus(reclamation.getIdmachine(), payload);
        }
    }


    @Override
    public List<Reclamation> findAll() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation save(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    private void envoyerNotificationAssignation(Reclamation reclamation, KeycloakUserInfoDTO technicien) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("reclamationId", reclamation.getId().toString());
            metadata.put("technicianName", technicien.getFirstName() + " " + technicien.getLastName());
            metadata.put("machineName", reclamation.getLigne());
            metadata.put("operateurName", reclamation.getDemandeur());
            metadata.put("description", reclamation.getDescription());

            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .userId(technicien.getId())
                    .type(NotificationType.OT_ASSIGNED)
                    .channel(NotificationChannel.EMAIL)
                    .priority(NotificationPriority.HIGH)
                    .reclamationId(reclamation.getId())
                    .metadata(metadata)
                    .build();

            notificationFeignClient.createAndSendNotification(notificationRequest);

            NotificationRequest inAppNotification = NotificationRequest.builder()
                    .userId(technicien.getId())
                    .type(NotificationType.OT_ASSIGNED)
                    .channel(NotificationChannel.IN_APP)
                    .priority(NotificationPriority.HIGH)
                    .reclamationId(reclamation.getId())
                    .metadata(metadata)
                    .build();

            notificationFeignClient.createAndSendNotification(inAppNotification);
        } catch (Exception e) {
            System.err.println("❌ Erreur notification assignation: " + e.getMessage());
        }
    }
//    @Override
//    public List<Reclamation> getReclamationsByDemandeur(String keycloakId) {
//        return reclamationRepository.findByDemandeur(keycloakId);
//    }

    public List<Reclamation> getReclamationsByOperateur(String keycloakId) {
        return reclamationRepository.findByIdUtilisateurCreateur(keycloakId);
    }

    public Reclamation getById(Long id) {
        return reclamationRepository.findById(id).orElse(null);
    }


}