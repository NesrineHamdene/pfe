package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.AdminDTO;
import com.project.ModuleUser.dto.ResponsableProductionDTO;
import com.project.ModuleUser.entities.Admin;
import com.project.ModuleUser.entities.ResponsableProduction;
import com.project.ModuleUser.entities.TechnicienMaintenance;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class ServiceAdmin implements IServiceAdmin {

    @Autowired
    private UserRepository userRepository;

    public Admin createAdmin(AdminDTO adminDTO) {
        // Conversion de AdminDTO en Admin
        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());  // Utilise le mot de passe transmis dans le DTO
        admin.setRole(adminDTO.getRole());
        admin.setAdminRights(adminDTO.getAdminRights());
        admin.setDepartment(adminDTO.getDepartment());

        // Enregistrement de l'admin dans la base de données
        return userRepository.save(admin);  // On enregistre l'entité Admin dans la base de données
    }


    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User updateUser(Long userId, AdminDTO userDTO) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec l'ID : " + userId);
        }

        // Récupérer l'utilisateur existant
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser != null && !existingUser.getUsername().equals(userDTO.getUsername())) {
            Optional<User> existingUserWithUsername = userRepository.findByUsername(userDTO.getUsername());
            if (existingUserWithUsername.isPresent() && !existingUserWithUsername.get().getId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Le nom d'utilisateur est déjà utilisé.");
            }
        }

        // Mettre à jour les données de l'entité User
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());

        // Si l'utilisateur est un Admin, mettre à jour les champs spécifiques à Admin
        if (existingUser instanceof Admin) {
            Admin admin = (Admin) existingUser;
            admin.setAdminRights(userDTO.getAdminRights());
            admin.setDepartment(userDTO.getDepartment());
        }
        // Si l'utilisateur est un TechnicienMaintenance, mettre à jour les champs spécifiques à TechnicienMaintenance
        else if (existingUser instanceof TechnicienMaintenance) {
            TechnicienMaintenance technicien = (TechnicienMaintenance) existingUser;
            technicien.setDepartement(userDTO.getDepartment());
            // Mettre à jour d'autres champs si nécessaire pour TechnicienMaintenance
        }
        // Ajouter d'autres conditions pour d'autres types d'utilisateurs si nécessaire

        // Sauvegarder l'entité mise à jour
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void assignRole(User user, String role) {
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
    }

    @Override
    public void removeRole(User user, String role) {
        user.setRole(null);
        userRepository.save(user);
    }

    @Override
    public void resetUserPassword(User user) {
        user.setPassword("password");
        userRepository.save(user);
    }

    @Override
    public void lockUserAccount(User user) {
        user.setUsername(user.getUsername() + "_locked");
        userRepository.save(user);
    }

    @Override
    public void unlockUserAccount(User user) {
        user.setUsername(user.getUsername().replace("_locked", ""));
        userRepository.save(user);
    }

    @Override
    public User updateUserFromDTO(Long userId, AdminDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec l'ID : " + userId));

        if (!existingUser.getUsername().equals(userDTO.getUsername())) {
            Optional<User> existingUserWithUsername = userRepository.findByUsername(userDTO.getUsername());
            if (existingUserWithUsername.isPresent() && !existingUserWithUsername.get().getId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Le nom d'utilisateur est déjà utilisé.");
            }
        }

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(userDTO.getRole());
        if (existingUser instanceof Admin) {
            ((Admin) existingUser).setAdminRights(userDTO.getAdminRights());
            ((Admin) existingUser).setDepartment(userDTO.getDepartment());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(Role.valueOf(role));
    }





    private ResponsableProductionDTO mapToDTO(ResponsableProduction responsableProduction) {
        ResponsableProductionDTO dto = new ResponsableProductionDTO();
        dto.setUsername(responsableProduction.getUsername());
        dto.setPassword(responsableProduction.getPassword());
        dto.setEmail(responsableProduction.getEmail());
        dto.setRole(responsableProduction.getRole());
        dto.setDepartement(responsableProduction.getDepartement());
        return dto;
    }

}