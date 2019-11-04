package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Family.
 */
@Entity
@Table(name = "family")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Family implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_family")
    private Integer idFamily;

    @Column(name = "family_relation")
    private String familyRelation;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @ManyToMany(mappedBy = "families")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Elderly> elderlies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFamily() {
        return idFamily;
    }

    public Family idFamily(Integer idFamily) {
        this.idFamily = idFamily;
        return this;
    }

    public void setIdFamily(Integer idFamily) {
        this.idFamily = idFamily;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public Family familyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
        return this;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Family userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Elderly> getElderlies() {
        return elderlies;
    }

    public Family elderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
        return this;
    }

    public Family addElderly(Elderly elderly) {
        this.elderlies.add(elderly);
        elderly.getFamilies().add(this);
        return this;
    }

    public Family removeElderly(Elderly elderly) {
        this.elderlies.remove(elderly);
        elderly.getFamilies().remove(this);
        return this;
    }

    public void setElderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Family)) {
            return false;
        }
        return id != null && id.equals(((Family) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Family{" +
            "id=" + getId() +
            ", idFamily=" + getIdFamily() +
            ", familyRelation='" + getFamilyRelation() + "'" +
            "}";
    }
}
