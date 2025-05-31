package com.project.ModuleUser.controller;

import com.project.ModuleUser.dto.*;
import com.project.ModuleUser.entities.Admin;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import com.project.ModuleUser.service.IServiceAdmin;
import com.project.ModuleUser.service.IServiceOperateurMachine;
import com.project.ModuleUser.service.IServiceResponsableProduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IServiceAdmin serviceAdmin;
    private final IServiceOperateurMachine serviceOperateurMachine;
    private final IServiceResponsableProduction serviceResponsableQualite;
    private final UserRepository userRepository;

    public AdminController(IServiceOperateurMachine serviceOperateurMachine, IServiceResponsableProduction serviceResponsableQualite, UserRepository userRepository) {
        this.serviceOperateurMachine = serviceOperateurMachine;
        this.serviceResponsableQualite = serviceResponsableQualite;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminDTO adminDTO) {
        // Vérifie que le mot de passe est présent dans adminDTO
        if (adminDTO.getPassword() == null || adminDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Retourne une erreur si le mot de passe est manquant
        }

        Admin createdAdmin = serviceAdmin.createAdmin(adminDTO);  // Passe adminDTO avec mot de passe
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }


    @GetMapping("/users/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> users = serviceAdmin.getUsersByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody AdminDTO adminDTO) {
        // Utiliser le constructeur d'AdminDTO pour envoyer l'objet complet avec le mot de passe
        User updatedUser = serviceAdmin.updateUser(userId, convertToEntity(adminDTO));

        // Vérifier le type et renvoyer le bon DTO
        if (updatedUser instanceof Admin) {
            return ResponseEntity.ok(convertToDTO((Admin) updatedUser)); // Retourne l'AdminDTO avec le mot de passe
        } else {
            return ResponseEntity.ok(convertToDTO((Admin) updatedUser)); // Retourne un UserDTO sans admin spécifiques
        }
    }



    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        serviceAdmin.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Méthodes de conversion
    private AdminDTO convertToEntity(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        admin.setRole(adminDTO.getRole());
        admin.setAdminRights(adminDTO.getAdminRights());
        admin.setDepartment(adminDTO.getDepartment());
        admin.setPassword(adminDTO.getPassword());
        return adminDTO;
    }

    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setRole(admin.getRole());
        adminDTO.setAdminRights(admin.getAdminRights());
        adminDTO.setDepartment(admin.getDepartment());
        adminDTO.setPassword(adminDTO.getPassword());
        return adminDTO;
    }




}