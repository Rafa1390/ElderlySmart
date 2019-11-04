package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Allergies.
 */
@Entity
@Table(name = "allergies")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Allergies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_allergies")
    private Integer idAllergies;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("allergies")
    private CaseFile caseFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdAllergies() {
        return idAllergies;
    }

    public Allergies idAllergies(Integer idAllergies) {
        this.idAllergies = idAllergies;
        return this;
    }

    public void setIdAllergies(Integer idAllergies) {
        this.idAllergies = idAllergies;
    }

    public String getName() {
        return name;
    }

    public Allergies name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Allergies description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseFile getCaseFile() {
        return caseFile;
    }

    public Allergies caseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
        return this;
    }

    public void setCaseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Allergies)) {
            return false;
        }
        return id != null && id.equals(((Allergies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Allergies{" +
            "id=" + getId() +
            ", idAllergies=" + getIdAllergies() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
