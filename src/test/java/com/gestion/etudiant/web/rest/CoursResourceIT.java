package com.gestion.etudiant.web.rest;

import com.gestion.etudiant.GestionEtudiantsApp;
import com.gestion.etudiant.domain.Cours;
import com.gestion.etudiant.repository.CoursRepository;
import com.gestion.etudiant.service.CoursService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoursResource} REST controller.
 */
@SpringBootTest(classes = GestionEtudiantsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CoursResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private CoursService coursService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoursMockMvc;

    private Cours cours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cours createEntity(EntityManager em) {
        Cours cours = new Cours()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return cours;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cours createUpdatedEntity(EntityManager em) {
        Cours cours = new Cours()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return cours;
    }

    @BeforeEach
    public void initTest() {
        cours = createEntity(em);
    }

    @Test
    @Transactional
    public void createCours() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();
        // Create the Cours
        restCoursMockMvc.perform(post("/api/cours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isCreated());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate + 1);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCours.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createCoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();

        // Create the Cours with an existing ID
        cours.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursMockMvc.perform(post("/api/cours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setCode(null);

        // Create the Cours, which fails.


        restCoursMockMvc.perform(post("/api/cours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setLibelle(null);

        // Create the Cours, which fails.


        restCoursMockMvc.perform(post("/api/cours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList
        restCoursMockMvc.perform(get("/api/cours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cours.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", cours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cours.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingCours() throws Exception {
        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCours() throws Exception {
        // Initialize the database
        coursService.save(cours);

        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Update the cours
        Cours updatedCours = coursRepository.findById(cours.getId()).get();
        // Disconnect from session so that the updates on updatedCours are not directly saved in db
        em.detach(updatedCours);
        updatedCours
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restCoursMockMvc.perform(put("/api/cours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCours)))
            .andExpect(status().isOk());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCours.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCours() throws Exception {
        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursMockMvc.perform(put("/api/cours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCours() throws Exception {
        // Initialize the database
        coursService.save(cours);

        int databaseSizeBeforeDelete = coursRepository.findAll().size();

        // Delete the cours
        restCoursMockMvc.perform(delete("/api/cours/{id}", cours.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
