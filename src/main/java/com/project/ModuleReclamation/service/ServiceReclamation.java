package com.project.ModuleReclamation.service;

import com.project.ModuleReclamation.dto.ReclamationDTO;
import com.project.ModuleReclamation.entities.Reclamation;
import com.project.ModuleReclamation.repository.ReclamationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
//@AllArgsConstructor
//@CrossOrigin(origins = "*")
//@NoArgsConstructor
@Service
public class ServiceReclamation implements IServiceReclamation {
    public ReclamationDTO convertToDTO(Reclamation reclamation) {
        ReclamationDTO dto = new ReclamationDTO();
        dto.setId(reclamation.getId());
        dto.setDate(reclamation.getDate());
        dto.setType(reclamation.getType());
        dto.setDescription(reclamation.getDescription());
        dto.setStatus(reclamation.getStatus());
        dto.setPriorite(reclamation.getPriorite());
        dto.setReference(reclamation.getReference());
        dto.setLigne(reclamation.getLigne());
        dto.setImpactProduction(reclamation.getImpactProduction());

        //dto.setUserId(reclamation.getUserId()); // Quand tu ajoutes la relation plus tard
        return dto;
    }

    @Override
    public Reclamation findById(Long id) {
        return reclamationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reclamation not found with id " + id));

    }


    @Autowired
    private ReclamationRepository reclamationRepository;


    @Override
    public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Optional<Reclamation> getReclamationById(Long id) {
        return reclamationRepository.findById(id);
    }

    @Override
    public Reclamation updateReclamation(Long id, Reclamation updatedReclamation) {
        Optional<Reclamation> existingReclamationOpt = reclamationRepository.findById(id);

        if (existingReclamationOpt.isPresent()) {
            Reclamation existingReclamation = existingReclamationOpt.get();
            //existingReclamation.setId(updatedReclamation.getId());//supprimé definitivement

            existingReclamation.setDate(updatedReclamation.getDate());
            existingReclamation.setType(updatedReclamation.getType());
            existingReclamation.setDescription(updatedReclamation.getDescription());
            existingReclamation.setStatus(updatedReclamation.getStatus());
            existingReclamation.setPriorite(updatedReclamation.getPriorite());
            existingReclamation.setDemandeur(updatedReclamation.getDemandeur());
            existingReclamation.setReference(updatedReclamation.getReference());
            existingReclamation.setLigne(updatedReclamation.getLigne());
            existingReclamation.setImpactProduction(updatedReclamation.getImpactProduction());
            return reclamationRepository.save(existingReclamation);
        } else {
            throw new RuntimeException("Réclamation avec l'ID " + id + " non trouvée.");
        }
    }




    @Override
    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);

    }

    @Override
    public List<Reclamation> getReclamationsByStatus(String status) {
        return reclamationRepository.findByStatus(status);
    }


    //@Override
    //public List<Reclamation> getReclamationsByMachineNom(String nom) {
        //return reclamationRepository.findByMachine_Nom(nom);
    //}
    // Recherche par type
    public List<Reclamation> getReclamationsByType(String type) {
        return reclamationRepository.findByType(type);
    }

    // Recherche par priorité
    public List<Reclamation> getReclamationsByPriorite(String priorite) {
        return reclamationRepository.findByPriorite(priorite);
    }
    @Override
    public List<Object[]> countReclamationsGroupByMonth() {
        return reclamationRepository.countReclamationsGroupByMonth();
    }

    @Override
    public List<Object[]> countReclamationsByDemandeur() {
        return reclamationRepository.countReclamationsByDemandeur();
    }

    @Override
    public List<Reclamation> getAllReclamationsWithFilters(String status, String priorite, LocalDate dateDebut, LocalDate dateFin) {
        // Convertir LocalDate en String si nécessaire (selon votre format de date)
        String dateDebutStr = dateDebut != null ? dateDebut.toString() : null;
        String dateFinStr = dateFin != null ? dateFin.toString() : null;

        return reclamationRepository.findWithFilters(status, priorite, dateDebutStr, dateFinStr);
    }

}
