package com.gestion.etudiant.service;

import com.gestion.etudiant.domain.Cours;
import com.gestion.etudiant.domain.Enseignant;
import com.gestion.etudiant.repository.CoursRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link Cours}.
 */
@Service
@Transactional
public class CoursService {

    private final Logger log = LoggerFactory.getLogger(CoursService.class);

    private final CoursRepository coursRepository;

    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    /**
     * Save a cours.
     *
     * @param cours the entity to save.
     * @return the persisted entity.
     */
    public Cours save(Cours cours) {
        log.debug("Request to save Cours : {}", cours);
        return coursRepository.save(cours);
    }
    
    public Cours assignerEnseignant(String codeCours, Enseignant enseignant) {
    	Cours cours = coursRepository.findByCode(codeCours);
    	cours.setEnseignant(enseignant);
    	save(cours);
    	return cours;
    }

    /**
     * Get all the cours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Cours> findAll(Pageable pageable) {
        log.debug("Request to get all Cours");
        return coursRepository.findAll(pageable);
    }


    /**
     * Get one cours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cours> findOne(Long id) {
        log.debug("Request to get Cours : {}", id);
        return coursRepository.findById(id);
    }
    
    public Cours findByCode(String code) {
    	log.debug("Request to get Cours : {}", code);
    	return coursRepository.findByCode(code);
    }
    
    public Set<Cours> findAllCoursbyEnseignant(String matricule){
    	log.debug("Request to get Cours : {}", matricule);
    	return coursRepository.findAllByEnseignant(matricule);
    }

    /**
     * Delete the cours by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cours : {}", id);

        coursRepository.deleteById(id);
    }
}
