package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PrescriptionNotification.
 */
@Entity
@Table(name = "prescription_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrescriptionNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_prescription_notification")
    private Integer idPrescriptionNotification;

    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private String state;

    @OneToOne
    @JoinColumn(unique = true)
    private Prescription prescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPrescriptionNotification() {
        return idPrescriptionNotification;
    }

    public PrescriptionNotification idPrescriptionNotification(Integer idPrescriptionNotification) {
        this.idPrescriptionNotification = idPrescriptionNotification;
        return this;
    }

    public void setIdPrescriptionNotification(Integer idPrescriptionNotification) {
        this.idPrescriptionNotification = idPrescriptionNotification;
    }

    public String getDescription() {
        return description;
    }

    public PrescriptionNotification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public PrescriptionNotification state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public PrescriptionNotification prescription(Prescription prescription) {
        this.prescription = prescription;
        return this;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescriptionNotification)) {
            return false;
        }
        return id != null && id.equals(((PrescriptionNotification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrescriptionNotification{" +
            "id=" + getId() +
            ", idPrescriptionNotification=" + getIdPrescriptionNotification() +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
