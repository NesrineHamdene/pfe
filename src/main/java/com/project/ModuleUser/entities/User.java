package com.project.ModuleUser.entities;

import com.project.ModuleUser.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Inheritance(strategy = InheritanceType.JOINED)
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true, nullable = false)

        private String username;
        @Column(nullable = false)
        private String password;
        @Column(unique = true, nullable = false)
        private String email;
        @Enumerated(EnumType.STRING)
        private Role role;
}
