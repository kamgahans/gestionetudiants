package com.gestion.etudiant.web.rest;

import com.gestion.etudiant.domain.Cours;
import com.gestion.etudiant.domain.Enseignant;
import com.gestion.etudiant.service.CoursService;
import com.gestion.etudiant.service.EnseignantService;
import com.gestion.etudiant.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.gestion.etudiant.domain.Cours}.
 */
@RestController
@RequestMapping("/api")
public class CoursResource {

    private final Logger log = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "cours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursService coursService;
    
    private final EnseignantService enseignantService;

    public CoursResource(CoursService coursService, EnseignantService enseignantService) {
        this.coursService = coursService;
        this.enseignantService = enseignantService;
    }

    /**
     * {@code POST  /cours} : Create a new cours.
     *
     * @param cours the cours to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cours, or with status {@code 400 (Bad Request)} if the cours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cours")
    public ResponseEntity<Cours> createCours(@Valid @RequestBody Cours cours) throws URISyntaxException {
        log.debug("REST request to save Cours : {}", cours);
        if (cours.getId() != null) {
            throw new BadRequestAlertException("A new cours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cours result = coursService.save(cours);
        return ResponseEntity.created(new URI("/api/cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cours} : Updates an existing cours.
     *
     * @param cours the cours to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cours,
     * or with status {@code 400 (Bad Request)} if the cours is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cours")
    public ResponseEntity<Cours> updateCours(@Valid @RequestBody Cours cours) throws URISyntaxException {
        log.debug("REST request to update Cours : {}", cours);
        if (cours.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cours result = coursService.save(cours);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cours.getId().toString()))
            .body(result);
    }
    
    @PutMapping("/cours-ens")
    public ResponseEntity<Cours> updateCoursWithEnseignant(@RequestParam String codeCours, @RequestParam String matricule) throws Exception{
    	
    	if(StringUtils.isEmpty(codeCours)) {
    		throw new Exception("code of course is null");
    	}
    	Cours cours = coursService.findByCode(codeCours);
    	Enseignant enseignant = enseignantService.findByMatricule(matricule);
    	log.debug("REST request to update Cours : {}","" +cours +","+matricule);
    	
    	//On assigne un enseignant au cours
    	cours.enseignant(enseignant);
    	Cours result = coursService.save(cours);
    	    	
    	return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cours.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /cours} : get all the cours.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cours in body.
     */
    @GetMapping("/cours")
    public ResponseEntity<List<Cours>> getAllCours(Pageable pageable) {
        log.debug("REST request to get a page of Cours");
        Page<Cours> page = coursService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cours/:id} : get the "id" cours.
     *
     * @param id the id of the cours to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cours, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cours/{id}")
    public ResponseEntity<Cours> getCours(@PathVariable Long id) {
        log.debug("REST request to get Cours : {}", id);
        Optional<Cours> cours = coursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cours);
    }

    /**
     * {@code DELETE  /cours/:id} : delete the "id" cours.
     *
     * @param id the id of the cours to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cours/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        log.debug("REST request to delete Cours : {}", id);

        coursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
