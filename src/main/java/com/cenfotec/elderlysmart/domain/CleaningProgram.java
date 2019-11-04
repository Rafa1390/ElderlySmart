package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CleaningProgram.
 */
@Entity
@Table(name = "cleaning_program")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CleaningProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cleaning_program")
    private Integer idCleaningProgram;

    @Column(name = "day")
    private LocalDate day;

    @Column(name = "time")
    private String time;

    @Column(name = "cleaning_task")
    private String cleaningTask;

    @ManyToOne
    @JsonIgnoreProperties("cleaningPrograms")
    private Asylum asylum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCleaningProgram() {
        return idCleaningProgram;
    }

    public CleaningProgram idCleaningProgram(Integer idCleaningProgram) {
        this.idCleaningProgram = idCleaningProgram;
        return this;
    }

    public void setIdCleaningProgram(Integer idCleaningProgram) {
        this.idCleaningProgram = idCleaningProgram;
    }

    public LocalDate getDay() {
        return day;
    }

    public CleaningProgram day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public CleaningProgram time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCleaningTask() {
        return cleaningTask;
    }

    public CleaningProgram cleaningTask(String cleaningTask) {
        this.cleaningTask = cleaningTask;
        return this;
    }

    public void setCleaningTask(String cleaningTask) {
        this.cleaningTask = cleaningTask;
    }

    public Asylum getAsylum() {
        return asylum;
    }

    public CleaningProgram asylum(Asylum asylum) {
        this.asylum = asylum;
        return this;
    }

    public void setAsylum(Asylum asylum) {
        this.asylum = asylum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CleaningProgram)) {
            return false;
        }
        return id != null && id.equals(((CleaningProgram) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CleaningProgram{" +
            "id=" + getId() +
            ", idCleaningProgram=" + getIdCleaningProgram() +
            ", day='" + getDay() + "'" +
            ", time='" + getTime() + "'" +
            ", cleaningTask='" + getCleaningTask() + "'" +
            "}";
    }
}
