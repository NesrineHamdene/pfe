package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.ResponsableProductionDTO;

import java.util.List;

public interface IServiceResponsableProduction {
    ResponsableProductionDTO updateResponsableQualite(Long id, ResponsableProductionDTO responsableProductionDTO);
    ResponsableProductionDTO getResponsableQualiteById(Long id);
    List<ResponsableProductionDTO> getAllResponsablesQualite();
    ResponsableProductionDTO createResponsableQualite(ResponsableProductionDTO dto);

    void deleteResponsableQualite(Long id);
}
