package com.gestion.etudiant.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cours.
 */
@Entity
@Table(name = "cours")
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "cours")
    private Set<Examen> examen = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "cours", allowSetters = true)
    private Enseignant enseignant;

    @ManyToMany(mappedBy = "cours")
    @JsonIgnore
    private Set<Etudiant> etudiants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Cours code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Cours libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Examen> getExamen() {
        return examen;
    }

    public Cours examen(Set<Examen> examen) {
        this.examen = examen;
        return this;
    }

    public Cours addExamen(Examen examen) {
        this.examen.add(examen);
        examen.setCours(this);
        return this;
    }

    public Cours removeExamen(Examen examen) {
        this.examen.remove(examen);
        examen.setCours(null);
        return this;
    }

    public void setExamen(Set<Examen> examen) {
        this.examen = examen;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Cours enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public Cours etudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
        return this;
    }

    public Cours addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.getCours().add(this);
        return this;
    }

    public Cours removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.getCours().remove(this);
        return this;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cours)) {
            return false;
        }
        return id != null && id.equals(((Cours) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
