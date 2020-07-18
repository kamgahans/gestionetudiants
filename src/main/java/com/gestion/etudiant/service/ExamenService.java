package com.gestion.etudiant.service;

import com.gestion.etudiant.domain.Examen;
import com.gestion.etudiant.repository.ExamenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Examen}.
 */
@Service
@Transactional
public class ExamenService {

    private final Logger log = LoggerFactory.getLogger(ExamenService.class);

    private final ExamenRepository examenRepository;

    public ExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    /**
     * Save a examen.
     *
     * @param examen the entity to save.
     * @return the persisted entity.
     */
    public Examen save(Examen examen) {
        log.debug("Request to save Examen : {}", examen);
        return examenRepository.save(examen);
    }

    /**
     * Get all the examen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Examen> findAll(Pageable pageable) {
        log.debug("Request to get all Examen");
        return examenRepository.findAll(pageable);
    }


    /**
     * Get one examen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Examen> findOne(Long id) {
        log.debug("Request to get Examen : {}", id);
        return examenRepository.findById(id);
    }

    /**
     * Delete the examen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Examen : {}", id);

        examenRepository.deleteById(id);
    }
}
