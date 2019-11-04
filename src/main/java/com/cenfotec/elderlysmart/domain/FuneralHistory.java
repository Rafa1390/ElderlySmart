package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FuneralHistory.
 */
@Entity
@Table(name = "funeral_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FuneralHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_funeral_history")
    private Integer idFuneralHistory;

    @Column(name = "customer")
    private String customer;

    @Column(name = "phone")
    private String phone;

    @Column(name = "purchase_made")
    private String purchaseMade;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "state")
    private String state;

    @OneToOne
    @JoinColumn(unique = true)
    private Mortuary mortuary;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFuneralHistory() {
        return idFuneralHistory;
    }

    public FuneralHistory idFuneralHistory(Integer idFuneralHistory) {
        this.idFuneralHistory = idFuneralHistory;
        return this;
    }

    public void setIdFuneralHistory(Integer idFuneralHistory) {
        this.idFuneralHistory = idFuneralHistory;
    }

    public String getCustomer() {
        return customer;
    }

    public FuneralHistory customer(String customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public FuneralHistory phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurchaseMade() {
        return purchaseMade;
    }

    public FuneralHistory purchaseMade(String purchaseMade) {
        this.purchaseMade = purchaseMade;
        return this;
    }

    public void setPurchaseMade(String purchaseMade) {
        this.purchaseMade = purchaseMade;
    }

    public LocalDate getDate() {
        return date;
    }

    public FuneralHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public FuneralHistory state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Mortuary getMortuary() {
        return mortuary;
    }

    public FuneralHistory mortuary(Mortuary mortuary) {
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
        if (!(o instanceof FuneralHistory)) {
            return false;
        }
        return id != null && id.equals(((FuneralHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FuneralHistory{" +
            "id=" + getId() +
            ", idFuneralHistory=" + getIdFuneralHistory() +
            ", customer='" + getCustomer() + "'" +
            ", phone='" + getPhone() + "'" +
            ", purchaseMade='" + getPurchaseMade() + "'" +
            ", date='" + getDate() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
