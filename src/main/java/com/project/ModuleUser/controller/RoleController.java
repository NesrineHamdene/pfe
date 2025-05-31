package com.project.ModuleUser.controller;

import com.project.ModuleUser.enums.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @GetMapping
    public List<String> listRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    /**
     * Renvoie un mapping rôle → nombre d'utilisateurs.
     * Pour l'instant, on renvoie 0 pour chaque rôle.
     * Tu pourras injecter ici ton service UserService pour calculer de vrais totaux.
     */
    @GetMapping("/stats")
    public Map<String, Long> statsByRole() {
        // on reprend listRoles() en appel d'instance
        return listRoles().stream()
                .collect(Collectors.toMap(roleName -> roleName, roleName -> 0L));
    }
}

