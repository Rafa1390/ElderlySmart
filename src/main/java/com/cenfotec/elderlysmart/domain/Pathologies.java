package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Pathologies.
 */
@Entity
@Table(name = "pathologies")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pathologies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pathologies")
    private Integer idPathologies;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("pathologies")
    private CaseFile caseFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPathologies() {
        return idPathologies;
    }

    public Pathologies idPathologies(Integer idPathologies) {
        this.idPathologies = idPathologies;
        return this;
    }

    public void setIdPathologies(Integer idPathologies) {
        this.idPathologies = idPathologies;
    }

    public String getName() {
        return name;
    }

    public Pathologies name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Pathologies description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseFile getCaseFile() {
        return caseFile;
    }

    public Pathologies caseFile(CaseFile caseFile) {
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
        if (!(o instanceof Pathologies)) {
            return false;
        }
        return id != null && id.equals(((Pathologies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pathologies{" +
            "id=" + getId() +
            ", idPathologies=" + getIdPathologies() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
