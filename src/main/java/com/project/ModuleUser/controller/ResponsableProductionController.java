package com.project.ModuleUser.controller;

import com.project.ModuleUser.dto.ResponsableProductionDTO;
import com.project.ModuleUser.service.IServiceResponsableProduction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/responsablesQualite")
public class ResponsableProductionController {
    private final IServiceResponsableProduction serviceResponsableQualite;

    public ResponsableProductionController(IServiceResponsableProduction serviceResponsableQualite) {
        this.serviceResponsableQualite = serviceResponsableQualite;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsableProductionDTO createResponsableQualite(@RequestBody ResponsableProductionDTO dto) {
        return serviceResponsableQualite.createResponsableQualite(dto);
    }


    @PutMapping("/{id}")
    public ResponsableProductionDTO updateResponsableQualite(@PathVariable Long id, @RequestBody ResponsableProductionDTO dto) {
        return serviceResponsableQualite.updateResponsableQualite(id, dto);
    }

    @GetMapping("/{id}")
    public ResponsableProductionDTO getResponsableQualiteById(@PathVariable Long id) {
        return serviceResponsableQualite.getResponsableQualiteById(id);
    }

    @GetMapping
    public List<ResponsableProductionDTO> getAllResponsablesQualite() {
        return serviceResponsableQualite.getAllResponsablesQualite();
    }

    @DeleteMapping("/{id}")
    public void deleteResponsableQualite(@PathVariable Long id) {
        serviceResponsableQualite.deleteResponsableQualite(id);
    }
}
