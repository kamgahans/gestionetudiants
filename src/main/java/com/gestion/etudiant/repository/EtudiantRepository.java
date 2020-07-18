package com.gestion.etudiant.repository;

import com.gestion.etudiant.domain.Etudiant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Etudiant entity.
 */
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    @Query(value = "select distinct etudiant from Etudiant etudiant left join fetch etudiant.cours",
        countQuery = "select count(distinct etudiant) from Etudiant etudiant")
    Page<Etudiant> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct etudiant from Etudiant etudiant left join fetch etudiant.cours")
    List<Etudiant> findAllWithEagerRelationships();

    @Query("select etudiant from Etudiant etudiant left join fetch etudiant.cours where etudiant.id =:id")
    Optional<Etudiant> findOneWithEagerRelationships(@Param("id") Long id);
}
