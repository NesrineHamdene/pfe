package com.project.ModuleUser.entities;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
    private String department;
}
