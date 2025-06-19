package com.project.ModuleReclamation.controller;


import com.project.ModuleShared.dto.KeycloakUserInfoDTO;
import com.project.ModuleShared.feign.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserServiceClient userServiceClient;

    @GetMapping("/techniciens")
    public List<KeycloakUserInfoDTO> getTechniciens() {
        System.out.println("🔄 [ModuleReclamation] Appel Feign vers ModuleUser");

        try {
            List<KeycloakUserInfoDTO> techniciens = userServiceClient.getTechniciens();
            System.out.println("✅ [ModuleReclamation] Reçu " + techniciens.size() + " techniciens via Feign");
            return techniciens;
        } catch (Exception e) {
            System.err.println("❌ [ModuleReclamation] Erreur Feign: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}")
    public KeycloakUserInfoDTO getUserById(@PathVariable String id) {
        return userServiceClient.getUserById(id);
    }
}
