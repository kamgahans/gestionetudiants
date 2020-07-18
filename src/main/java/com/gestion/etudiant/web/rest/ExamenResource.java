package com.gestion.etudiant.web.rest;

import com.gestion.etudiant.domain.Examen;
import com.gestion.etudiant.service.ExamenService;
import com.gestion.etudiant.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gestion.etudiant.domain.Examen}.
 */
@RestController
@RequestMapping("/api")
public class ExamenResource {

    private final Logger log = LoggerFactory.getLogger(ExamenResource.class);

    private static final String ENTITY_NAME = "examen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamenService examenService;

    public ExamenResource(ExamenService examenService) {
        this.examenService = examenService;
    }

    /**
     * {@code POST  /examen} : Create a new examen.
     *
     * @param examen the examen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examen, or with status {@code 400 (Bad Request)} if the examen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examen")
    public ResponseEntity<Examen> createExamen(@RequestBody Examen examen) throws URISyntaxException {
        log.debug("REST request to save Examen : {}", examen);
        if (examen.getId() != null) {
            throw new BadRequestAlertException("A new examen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Examen result = examenService.save(examen);
        return ResponseEntity.created(new URI("/api/examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examen} : Updates an existing examen.
     *
     * @param examen the examen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examen,
     * or with status {@code 400 (Bad Request)} if the examen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examen")
    public ResponseEntity<Examen> updateExamen(@RequestBody Examen examen) throws URISyntaxException {
        log.debug("REST request to update Examen : {}", examen);
        if (examen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Examen result = examenService.save(examen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examen.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /examen} : get all the examen.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examen in body.
     */
    @GetMapping("/examen")
    public ResponseEntity<List<Examen>> getAllExamen(Pageable pageable) {
        log.debug("REST request to get a page of Examen");
        Page<Examen> page = examenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /examen/:id} : get the "id" examen.
     *
     * @param id the id of the examen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examen/{id}")
    public ResponseEntity<Examen> getExamen(@PathVariable Long id) {
        log.debug("REST request to get Examen : {}", id);
        Optional<Examen> examen = examenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examen);
    }

    /**
     * {@code DELETE  /examen/:id} : delete the "id" examen.
     *
     * @param id the id of the examen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examen/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        log.debug("REST request to delete Examen : {}", id);

        examenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
