package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.OperateurMachineDTO;
import com.project.ModuleUser.entities.OperateurMachine;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOperateurMachine implements IServiceOperateurMachine {
    @Autowired
    private final UserRepository userRepository;

    public ServiceOperateurMachine(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private OperateurMachineDTO convertToDTO(OperateurMachine operateur) {

        OperateurMachineDTO op = new OperateurMachineDTO();
      /*  return new OperateurMachineDTO(
                operateur.getId(),
                operateur.getUsername(),
                operateur.getEmail(),
                operateur.getPassword(),
                operateur.getMachineType(),
                operateur.getStatus()
        );*/

        op.setEmail(operateur.getEmail());
        op.setPassword(operateur.getPassword());
        op.setMachineType(operateur.getMachineType());
        op.setStatus(operateur.getStatus());
        op.setId(operateur.getId());
        op.setUsername(operateur.getUsername());
        op.setDepartement(operateur.getDepartement()); // Ajouté ici !
        op.setRole(operateur.getRole());  // Correction ici, on utilise directement getRole()
        return op;

    }


    @Override
    public OperateurMachineDTO getOperateurByUsername(String username) {
        OperateurMachine operateur = (OperateurMachine) userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Opérateur non trouvé"));
        return convertToDTO(operateur);
    }

    @Override
    public OperateurMachineDTO updateOperateurByUsername(String username, OperateurMachineDTO operateurDTO) {
        OperateurMachine operateur = (OperateurMachine) userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Opérateur non trouvé"));

        // Mettre à jour uniquement les champs nécessaires
        operateur.setEmail(operateurDTO.getEmail());
        operateur.setPassword(operateurDTO.getPassword());
        operateur.setMachineType(operateurDTO.getMachineType());
        operateur.setStatus(operateurDTO.getStatus());
        operateur.setDepartement(operateurDTO.getDepartement());
        // Sauvegarder les modifications
        userRepository.save(operateur);
        return convertToDTO(operateur);
    }
    @Override
    public OperateurMachineDTO createOperateurMachine(OperateurMachineDTO operateurDTO) {
        OperateurMachine operateur = new OperateurMachine(
                operateurDTO.getUsername(),
                operateurDTO.getPassword(),
                operateurDTO.getEmail(),
                operateurDTO.getStatus(),
                operateurDTO.getMachineType(),
                operateurDTO.getDepartement()
        );
        operateur.setRole(Role.OPERATEUR_MACHINE);
        userRepository.save(operateur);
        return convertToDTO(operateur);
    }
}
