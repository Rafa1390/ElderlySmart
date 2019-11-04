package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FuneralPackages.
 */
@Entity
@Table(name = "funeral_packages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FuneralPackages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_funeral_packages")
    private Integer idFuneralPackages;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JsonIgnoreProperties("funeralPackages")
    private Mortuary mortuary;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFuneralPackages() {
        return idFuneralPackages;
    }

    public FuneralPackages idFuneralPackages(Integer idFuneralPackages) {
        this.idFuneralPackages = idFuneralPackages;
        return this;
    }

    public void setIdFuneralPackages(Integer idFuneralPackages) {
        this.idFuneralPackages = idFuneralPackages;
    }

    public String getName() {
        return name;
    }

    public FuneralPackages name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public FuneralPackages description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public FuneralPackages price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public FuneralPackages state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Mortuary getMortuary() {
        return mortuary;
    }

    public FuneralPackages mortuary(Mortuary mortuary) {
        this.mortuary = mortuary;
        return this;
    }

    public void setMortuary(Mortuary mortuary) {
        this.mortuary = mortuary;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuneralPackages)) {
            return false;
        }
        return id != null && id.equals(((FuneralPackages) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FuneralPackages{" +
            "id=" + getId() +
            ", idFuneralPackages=" + getIdFuneralPackages() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", state='" + getState() + "'" +
            "}";
    }
}
