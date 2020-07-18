package com.gestion.etudiant.service;

import com.gestion.etudiant.domain.Enseignant;
import com.gestion.etudiant.repository.EnseignantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Enseignant}.
 */
@Service
@Transactional
public class EnseignantService {

    private final Logger log = LoggerFactory.getLogger(EnseignantService.class);

    private final EnseignantRepository enseignantRepository;

    public EnseignantService(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }

    /**
     * Save a enseignant.
     *
     * @param enseignant the entity to save.
     * @return the persisted entity.
     */
    public Enseignant save(Enseignant enseignant) {
        log.debug("Request to save Enseignant : {}", enseignant);
        return enseignantRepository.save(enseignant);
    }

    /**
     * Get all the enseignants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Enseignant> findAll(Pageable pageable) {
        log.debug("Request to get all Enseignants");
        return enseignantRepository.findAll(pageable);
    }


    /**
     * Get one enseignant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Enseignant> findOne(Long id) {
        log.debug("Request to get Enseignant : {}", id);
        return enseignantRepository.findById(id);
    }
    
    public Enseignant findByMatricule(String matricule) {
    	log.debug("Request to get Enseignant : {}", matricule);
    	return enseignantRepository.findByMatricule(matricule);
    }

    /**
     * Delete the enseignant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Enseignant : {}", id);

        enseignantRepository.deleteById(id);
    }
}
