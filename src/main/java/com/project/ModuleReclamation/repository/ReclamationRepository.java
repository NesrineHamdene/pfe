package com.project.ModuleReclamation.repository;

import com.project.ModuleReclamation.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    //trouver reclamtion par status
    List<Reclamation> findByStatus(String status);
    List<Reclamation> findByType(String type);
    List<Reclamation> findByPriorite(String priorite);
    //List<Reclamation> findByMachine_Nom(String nom);
    //List<Reclamation> findByTechnicienMaintenanceId(Long technicienMaintenaceId);
    // Requête pour compter les réclamations par mois (exemple si 'date' est une String)
    @Query("SELECT SUBSTRING(r.date, 1, 7) AS month, COUNT(r) FROM Reclamation r GROUP BY SUBSTRING(r.date, 1, 7) ORDER BY SUBSTRING(r.date, 1, 7)")
    List<Object[]> countReclamationsGroupByMonth();

    // Requête pour compter les réclamations par demandeur
    @Query("SELECT r.demandeur, COUNT(r) FROM Reclamation r GROUP BY r.demandeur ORDER BY COUNT(r) DESC")
    List<Object[]> countReclamationsByDemandeur();
    @Query("SELECT r FROM Reclamation r WHERE " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:priorite IS NULL OR r.priorite = :priorite) AND " +
            "(:dateDebut IS NULL OR r.date >= :dateDebut) AND " +
            "(:dateFin IS NULL OR r.date <= :dateFin)")
    List<Reclamation> findWithFilters(
            @Param("status") String status,
            @Param("priorite") String priorite,
            @Param("dateDebut") String dateDebut,
            @Param("dateFin") String dateFin);
    List<Reclamation> findByIdTechnicienAssigne(String idTechnicienAssigne);

    //List<Reclamation> findByDemandeur(String keycloakId);
    List<Reclamation> findByIdUtilisateurCreateur(String idUtilisateurCreateur);

}
