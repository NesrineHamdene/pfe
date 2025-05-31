package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.TechnicienMaintenanceDTO;
import com.project.ModuleUser.entities.TechnicienMaintenance;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTechnicienMaintenance implements IServiceTechnicienMaintenance{
    @Autowired
    private UserRepository userRepository;
    @Override
    public TechnicienMaintenanceDTO getTechnicienByUsername(String username) {
        TechnicienMaintenance technicien = (TechnicienMaintenance) userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Technicien non trouvé"));

        // Mapper l'entité vers le DTO
        TechnicienMaintenanceDTO technicienDTO = new TechnicienMaintenanceDTO();
        technicienDTO.setId(technicien.getId());
        technicienDTO.setUsername(technicien.getUsername());
        technicienDTO.setEmail(technicien.getEmail());
        technicienDTO.setPassword(technicien.getPassword());  // Si tu veux retourner le mot de passe (mais attention, c'est risqué)
        technicienDTO.setDepartment(technicien.getDepartement());
        technicienDTO.setRapport(technicien.getRapport());
        technicienDTO.setStatus(technicien.getStatus());


        return technicienDTO;
    }

    @Override
    public TechnicienMaintenanceDTO updateTechnicienByUsername(String username, TechnicienMaintenanceDTO technicienDTO) {
        TechnicienMaintenance technicien = (TechnicienMaintenance) userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Technicien non trouvé : " + username));

        // Mise à jour des champs
        if (technicienDTO.getEmail() != null) technicien.setEmail(technicienDTO.getEmail());
        if (technicienDTO.getPassword() != null) technicien.setPassword(technicienDTO.getPassword());
        if (technicienDTO.getDepartment() != null) technicien.setDepartement(technicienDTO.getDepartment());
        if (technicienDTO.getStatus() != null) technicien.setStatus(technicienDTO.getStatus());
        if (technicienDTO.getRapport() != null) technicien.setRapport(technicienDTO.getRapport());

        // Sauvegarde des modifications
        userRepository.save(technicien);

        // Mapper l'entité mise à jour vers le DTO de réponse
        TechnicienMaintenanceDTO updatedTechnicienDTO = new TechnicienMaintenanceDTO();
        updatedTechnicienDTO.setId(technicien.getId());
        updatedTechnicienDTO.setUsername(technicien.getUsername());
        updatedTechnicienDTO.setEmail(technicien.getEmail());
        updatedTechnicienDTO.setDepartment(technicien.getDepartement());
        updatedTechnicienDTO.setStatus(technicien.getStatus());
        updatedTechnicienDTO.setRapport(technicien.getRapport());

        return updatedTechnicienDTO;
    }
    }



