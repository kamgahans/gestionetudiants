package com.gestion.etudiant.service;

import com.gestion.etudiant.domain.Etudiant;
import com.gestion.etudiant.repository.EtudiantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Etudiant}.
 */
@Service
@Transactional
public class EtudiantService {

    private final Logger log = LoggerFactory.getLogger(EtudiantService.class);

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    /**
     * Save a etudiant.
     *
     * @param etudiant the entity to save.
     * @return the persisted entity.
     */
    public Etudiant save(Etudiant etudiant) {
        log.debug("Request to save Etudiant : {}", etudiant);
        return etudiantRepository.save(etudiant);
    }

    /**
     * Get all the etudiants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Etudiant> findAll(Pageable pageable) {
        log.debug("Request to get all Etudiants");
        return etudiantRepository.findAll(pageable);
    }


    /**
     * Get all the etudiants with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Etudiant> findAllWithEagerRelationships(Pageable pageable) {
        return etudiantRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one etudiant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Etudiant> findOne(Long id) {
        log.debug("Request to get Etudiant : {}", id);
        return etudiantRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the etudiant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Etudiant : {}", id);

        etudiantRepository.deleteById(id);
    }
}
