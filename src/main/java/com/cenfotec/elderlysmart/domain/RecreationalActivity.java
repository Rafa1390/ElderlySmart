package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A RecreationalActivity.
 */
@Entity
@Table(name = "recreational_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecreationalActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_recreational_activity")
    private Integer idRecreationalActivity;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JsonIgnoreProperties("recreationalActivities")
    private Asylum asylum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRecreationalActivity() {
        return idRecreationalActivity;
    }

    public RecreationalActivity idRecreationalActivity(Integer idRecreationalActivity) {
        this.idRecreationalActivity = idRecreationalActivity;
        return this;
    }

    public void setIdRecreationalActivity(Integer idRecreationalActivity) {
        this.idRecreationalActivity = idRecreationalActivity;
    }

    public String getName() {
        return name;
    }

    public RecreationalActivity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public RecreationalActivity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public RecreationalActivity date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public RecreationalActivity startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public RecreationalActivity endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public RecreationalActivity state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Asylum getAsylum() {
        return asylum;
    }

    public RecreationalActivity asylum(Asylum asylum) {
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
        if (!(o instanceof RecreationalActivity)) {
            return false;
        }
        return id != null && id.equals(((RecreationalActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RecreationalActivity{" +
            "id=" + getId() +
            ", idRecreationalActivity=" + getIdRecreationalActivity() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
