package com.project.ModuleUser.entities;

import jakarta.persistence.Entity;

@Entity
public class OperateurMachine extends User{
    private String machineType; // Type de machine manipulée
}
