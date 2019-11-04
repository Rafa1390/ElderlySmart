package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FoodMenu.
 */
@Entity
@Table(name = "food_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FoodMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_food_menu")
    private Integer idFoodMenu;

    @Column(name = "feeding_time")
    private String feedingTime;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("foodMenus")
    private Asylum asylum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFoodMenu() {
        return idFoodMenu;
    }

    public FoodMenu idFoodMenu(Integer idFoodMenu) {
        this.idFoodMenu = idFoodMenu;
        return this;
    }

    public void setIdFoodMenu(Integer idFoodMenu) {
        this.idFoodMenu = idFoodMenu;
    }

    public String getFeedingTime() {
        return feedingTime;
    }

    public FoodMenu feedingTime(String feedingTime) {
        this.feedingTime = feedingTime;
        return this;
    }

    public void setFeedingTime(String feedingTime) {
        this.feedingTime = feedingTime;
    }

    public String getDescription() {
        return description;
    }

    public FoodMenu description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Asylum getAsylum() {
        return asylum;
    }

    public FoodMenu asylum(Asylum asylum) {
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
        if (!(o instanceof FoodMenu)) {
            return false;
        }
        return id != null && id.equals(((FoodMenu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FoodMenu{" +
            "id=" + getId() +
            ", idFoodMenu=" + getIdFoodMenu() +
            ", feedingTime='" + getFeedingTime() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
