package com.gestion.etudiant.repository;

import com.gestion.etudiant.domain.Enseignant;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Enseignant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
	
	@Query("select e from Enseignant e where e.matricule = :matricule")
	Enseignant findByMatricule(@Param("matricule") String matricule);
	
}
