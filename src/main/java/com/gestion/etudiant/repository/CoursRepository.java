package com.gestion.etudiant.repository;

import com.gestion.etudiant.domain.Cours;
import com.gestion.etudiant.domain.Enseignant;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
	
	//Cours assginerEnseignant(Long idCours, Enseignant enseignant);
	
	@Query("select c from Cours c where c.code = :code")
	Cours findByCode(@Param("code") String code);
	
	@Query("select c from Cours c where c.enseignant.matricule = :matricule")
	Set<Cours> findAllByEnseignant(@Param("matricule") String matricule);
	
}
