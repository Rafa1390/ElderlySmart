package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Medicament.
 */
@Entity
@Table(name = "medicament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medicament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medicament")
    private Integer idMedicament;

    @Column(name = "presentation")
    private String presentation;

    @Column(name = "date_expiry")
    private LocalDate dateExpiry;

    @Column(name = "cuantity")
    private String cuantity;

    @OneToOne
    @JoinColumn(unique = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMedicament() {
        return idMedicament;
    }

    public Medicament idMedicament(Integer idMedicament) {
        this.idMedicament = idMedicament;
        return this;
    }

    public void setIdMedicament(Integer idMedicament) {
        this.idMedicament = idMedicament;
    }

    public String getPresentation() {
        return presentation;
    }

    public Medicament presentation(String presentation) {
        this.presentation = presentation;
        return this;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public LocalDate getDateExpiry() {
        return dateExpiry;
    }

    public Medicament dateExpiry(LocalDate dateExpiry) {
        this.dateExpiry = dateExpiry;
        return this;
    }

    public void setDateExpiry(LocalDate dateExpiry) {
        this.dateExpiry = dateExpiry;
    }

    public String getCuantity() {
        return cuantity;
    }

    public Medicament cuantity(String cuantity) {
        this.cuantity = cuantity;
        return this;
    }

    public void setCuantity(String cuantity) {
        this.cuantity = cuantity;
    }

    public Product getProduct() {
        return product;
    }

    public Medicament product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicament)) {
            return false;
        }
        return id != null && id.equals(((Medicament) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Medicament{" +
            "id=" + getId() +
            ", idMedicament=" + getIdMedicament() +
            ", presentation='" + getPresentation() + "'" +
            ", dateExpiry='" + getDateExpiry() + "'" +
            ", cuantity='" + getCuantity() + "'" +
            "}";
    }
}
