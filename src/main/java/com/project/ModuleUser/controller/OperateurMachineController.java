package com.project.ModuleUser.controller;

import com.project.ModuleUser.dto.OperateurMachineDTO;
import com.project.ModuleUser.service.IServiceOperateurMachine;
import com.project.ModuleUser.service.ServiceOperateurMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operateur-machine")
public class OperateurMachineController {
    private final IServiceOperateurMachine serviceOperateurMachine;

 @Autowired
    public OperateurMachineController(ServiceOperateurMachine serviceOperateurMachine) {
        this.serviceOperateurMachine = serviceOperateurMachine;
    }

    // Récupérer un opérateur par son username
    @GetMapping("/profil/{username}")
    public ResponseEntity<OperateurMachineDTO> getProfil(@PathVariable String username) {
        OperateurMachineDTO operateurDTO = serviceOperateurMachine.getOperateurByUsername(username);
        return ResponseEntity.ok(operateurDTO);
    }
    @PostMapping("/operateur-machine")
    public ResponseEntity<OperateurMachineDTO> createOperateurMachine(@RequestBody OperateurMachineDTO operateurDTO) {
        // L'admin crée un opérateur machine en envoyant un DTO
        OperateurMachineDTO newOperateur = serviceOperateurMachine.createOperateurMachine(operateurDTO);
        return ResponseEntity.status(201).body(newOperateur);
    }

    // Modifier le profil d'un opérateur par son username
    @PutMapping("/profil/{username}")
    public ResponseEntity<OperateurMachineDTO> updateProfil(@PathVariable String username, @RequestBody OperateurMachineDTO operateurDTO) {
        OperateurMachineDTO updatedOperateur = serviceOperateurMachine.updateOperateurByUsername(username, operateurDTO);
        return ResponseEntity.ok(updatedOperateur);
    }

}
