package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AppointmentNotification.
 */
@Entity
@Table(name = "appointment_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppointmentNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_appointment_notification")
    private Integer idAppointmentNotification;

    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private String state;

    @OneToOne
    @JoinColumn(unique = true)
    private MedicalAppointment medicalAppointment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdAppointmentNotification() {
        return idAppointmentNotification;
    }

    public AppointmentNotification idAppointmentNotification(Integer idAppointmentNotification) {
        this.idAppointmentNotification = idAppointmentNotification;
        return this;
    }

    public void setIdAppointmentNotification(Integer idAppointmentNotification) {
        this.idAppointmentNotification = idAppointmentNotification;
    }

    public String getDescription() {
        return description;
    }

    public AppointmentNotification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public AppointmentNotification state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MedicalAppointment getMedicalAppointment() {
        return medicalAppointment;
    }

    public AppointmentNotification medicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
        return this;
    }

    public void setMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentNotification)) {
            return false;
        }
        return id != null && id.equals(((AppointmentNotification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AppointmentNotification{" +
            "id=" + getId() +
            ", idAppointmentNotification=" + getIdAppointmentNotification() +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
