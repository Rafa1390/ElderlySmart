package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A MedicationSchedule.
 */
@Entity
@Table(name = "medication_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicationSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medication_schedule")
    private Integer idMedicationSchedule;

    @Column(name = "metication_name")
    private String meticationName;

    @Column(name = "elderly")
    private String elderly;

    @Column(name = "dose")
    private String dose;

    @Column(name = "time")
    private String time;

    @Column(name = "state")
    private String state;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMedicationSchedule() {
        return idMedicationSchedule;
    }

    public MedicationSchedule idMedicationSchedule(Integer idMedicationSchedule) {
        this.idMedicationSchedule = idMedicationSchedule;
        return this;
    }

    public void setIdMedicationSchedule(Integer idMedicationSchedule) {
        this.idMedicationSchedule = idMedicationSchedule;
    }

    public String getMeticationName() {
        return meticationName;
    }

    public MedicationSchedule meticationName(String meticationName) {
        this.meticationName = meticationName;
        return this;
    }

    public void setMeticationName(String meticationName) {
        this.meticationName = meticationName;
    }

    public String getElderly() {
        return elderly;
    }

    public MedicationSchedule elderly(String elderly) {
        this.elderly = elderly;
        return this;
    }

    public void setElderly(String elderly) {
        this.elderly = elderly;
    }

    public String getDose() {
        return dose;
    }

    public MedicationSchedule dose(String dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTime() {
        return time;
    }

    public MedicationSchedule time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public MedicationSchedule state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicationSchedule)) {
            return false;
        }
        return id != null && id.equals(((MedicationSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicationSchedule{" +
            "id=" + getId() +
            ", idMedicationSchedule=" + getIdMedicationSchedule() +
            ", meticationName='" + getMeticationName() + "'" +
            ", elderly='" + getElderly() + "'" +
            ", dose='" + getDose() + "'" +
            ", time='" + getTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
