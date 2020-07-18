package com.gestion.etudiant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "pourcentage")
    private String pourcentage;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = "examen", allowSetters = true)
    private Cours cours;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Examen libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPourcentage() {
        return pourcentage;
    }

    public Examen pourcentage(String pourcentage) {
        this.pourcentage = pourcentage;
        return this;
    }

    public void setPourcentage(String pourcentage) {
        this.pourcentage = pourcentage;
    }

    public String getNote() {
        return note;
    }

    public Examen note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Cours getCours() {
        return cours;
    }

    public Examen cours(Cours cours) {
        this.cours = cours;
        return this;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        return id != null && id.equals(((Examen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", pourcentage='" + getPourcentage() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
