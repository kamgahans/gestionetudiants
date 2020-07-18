package com.gestion.etudiant.web.rest;

import com.gestion.etudiant.GestionEtudiantsApp;
import com.gestion.etudiant.domain.Examen;
import com.gestion.etudiant.repository.ExamenRepository;
import com.gestion.etudiant.service.ExamenService;

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
 * Integration tests for the {@link ExamenResource} REST controller.
 */
@SpringBootTest(classes = GestionEtudiantsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamenResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_POURCENTAGE = "AAAAAAAAAA";
    private static final String UPDATED_POURCENTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private ExamenService examenService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamenMockMvc;

    private Examen examen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createEntity(EntityManager em) {
        Examen examen = new Examen()
            .libelle(DEFAULT_LIBELLE)
            .pourcentage(DEFAULT_POURCENTAGE)
            .note(DEFAULT_NOTE);
        return examen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Examen createUpdatedEntity(EntityManager em) {
        Examen examen = new Examen()
            .libelle(UPDATED_LIBELLE)
            .pourcentage(UPDATED_POURCENTAGE)
            .note(UPDATED_NOTE);
        return examen;
    }

    @BeforeEach
    public void initTest() {
        examen = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamen() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();
        // Create the Examen
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isCreated());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate + 1);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testExamen.getPourcentage()).isEqualTo(DEFAULT_POURCENTAGE);
        assertThat(testExamen.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examenRepository.findAll().size();

        // Create the Examen with an existing ID
        examen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenMockMvc.perform(post("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get all the examenList
        restExamenMockMvc.perform(get("/api/examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].pourcentage").value(hasItem(DEFAULT_POURCENTAGE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getExamen() throws Exception {
        // Initialize the database
        examenRepository.saveAndFlush(examen);

        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", examen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examen.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.pourcentage").value(DEFAULT_POURCENTAGE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingExamen() throws Exception {
        // Get the examen
        restExamenMockMvc.perform(get("/api/examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamen() throws Exception {
        // Initialize the database
        examenService.save(examen);

        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // Update the examen
        Examen updatedExamen = examenRepository.findById(examen.getId()).get();
        // Disconnect from session so that the updates on updatedExamen are not directly saved in db
        em.detach(updatedExamen);
        updatedExamen
            .libelle(UPDATED_LIBELLE)
            .pourcentage(UPDATED_POURCENTAGE)
            .note(UPDATED_NOTE);

        restExamenMockMvc.perform(put("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamen)))
            .andExpect(status().isOk());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
        Examen testExamen = examenList.get(examenList.size() - 1);
        assertThat(testExamen.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testExamen.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
        assertThat(testExamen.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamen() throws Exception {
        int databaseSizeBeforeUpdate = examenRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenMockMvc.perform(put("/api/examen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examen)))
            .andExpect(status().isBadRequest());

        // Validate the Examen in the database
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamen() throws Exception {
        // Initialize the database
        examenService.save(examen);

        int databaseSizeBeforeDelete = examenRepository.findAll().size();

        // Delete the examen
        restExamenMockMvc.perform(delete("/api/examen/{id}", examen.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Examen> examenList = examenRepository.findAll();
        assertThat(examenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
