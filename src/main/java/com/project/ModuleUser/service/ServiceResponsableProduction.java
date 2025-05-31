package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.ResponsableProductionDTO;
import com.project.ModuleUser.entities.ResponsableProduction;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceResponsableProduction implements IServiceResponsableProduction {
    private final UserRepository userRepository;

    public ServiceResponsableProduction(UserRepository userRepository) {this.userRepository = userRepository;}//construtor avec parametre



    @Override
    public ResponsableProductionDTO updateResponsableQualite(Long id, ResponsableProductionDTO dto) {
        ResponsableProduction responsableProduction = (ResponsableProduction) userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable Qualité introuvable"));

        responsableProduction.setUsername(dto.getUsername());
        responsableProduction.setPassword(dto.getPassword());
        responsableProduction.setEmail(dto.getEmail());
        responsableProduction.setDepartement(dto.getDepartement());

        ResponsableProduction updatedResponsableProduction = userRepository.save(responsableProduction);
        return mapToDTO(updatedResponsableProduction);
    }

    @Override
    public ResponsableProductionDTO getResponsableQualiteById(Long id) {
        ResponsableProduction responsableProduction = (ResponsableProduction) userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsable Qualité introuvable"));

        return mapToDTO(responsableProduction);
    }

    @Override
    public List<ResponsableProductionDTO> getAllResponsablesQualite() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof ResponsableProduction)
                .map(user -> mapToDTO((ResponsableProduction) user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteResponsableQualite(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public ResponsableProductionDTO createResponsableQualite(ResponsableProductionDTO dto) {
        ResponsableProduction responsableProduction = new ResponsableProduction();
        responsableProduction.setUsername(dto.getUsername());
        responsableProduction.setPassword(dto.getPassword());
        responsableProduction.setEmail(dto.getEmail());
        responsableProduction.setRole(Role.RESPONSABLE_PRODUCTION);

        responsableProduction.setDepartement(dto.getDepartement());

        ResponsableProduction savedResponsable = userRepository.save(responsableProduction);
        return mapToDTO(savedResponsable);
    }

    private ResponsableProductionDTO mapToDTO(ResponsableProduction responsableProduction) {
        ResponsableProductionDTO dto = new ResponsableProductionDTO();
        dto.setId(responsableProduction.getId());
        dto.setUsername(responsableProduction.getUsername());
        dto.setPassword(responsableProduction.getPassword());
        dto.setEmail(responsableProduction.getEmail());
        dto.setDepartement(responsableProduction.getDepartement());
        dto.setRole(responsableProduction.getRole());
        return dto;
    }
}


