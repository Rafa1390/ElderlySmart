package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MedicalAppointment.
 */
@Entity
@Table(name = "medical_appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicalAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medical_appointment")
    private Integer idMedicalAppointment;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private String time;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JsonIgnoreProperties("medicalAppointments")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("medicalAppointments")
    private Doctor doctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMedicalAppointment() {
        return idMedicalAppointment;
    }

    public MedicalAppointment idMedicalAppointment(Integer idMedicalAppointment) {
        this.idMedicalAppointment = idMedicalAppointment;
        return this;
    }

    public void setIdMedicalAppointment(Integer idMedicalAppointment) {
        this.idMedicalAppointment = idMedicalAppointment;
    }

    public LocalDate getDate() {
        return date;
    }

    public MedicalAppointment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public MedicalAppointment time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public MedicalAppointment state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Employee getEmployee() {
        return employee;
    }

    public MedicalAppointment employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public MedicalAppointment doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalAppointment)) {
            return false;
        }
        return id != null && id.equals(((MedicalAppointment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicalAppointment{" +
            "id=" + getId() +
            ", idMedicalAppointment=" + getIdMedicalAppointment() +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
