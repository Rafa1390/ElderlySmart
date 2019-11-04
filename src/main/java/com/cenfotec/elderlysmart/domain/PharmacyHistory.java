package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PharmacyHistory.
 */
@Entity
@Table(name = "pharmacy_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PharmacyHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pharmacy_history")
    private Integer idPharmacyHistory;

    @Column(name = "code")
    private String code;

    @Column(name = "client")
    private String client;

    @Column(name = "phone")
    private String phone;

    @Column(name = "purchase_made")
    private String purchaseMade;

    @Column(name = "date")
    private LocalDate date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPharmacyHistory() {
        return idPharmacyHistory;
    }

    public PharmacyHistory idPharmacyHistory(Integer idPharmacyHistory) {
        this.idPharmacyHistory = idPharmacyHistory;
        return this;
    }

    public void setIdPharmacyHistory(Integer idPharmacyHistory) {
        this.idPharmacyHistory = idPharmacyHistory;
    }

    public String getCode() {
        return code;
    }

    public PharmacyHistory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient() {
        return client;
    }

    public PharmacyHistory client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone() {
        return phone;
    }

    public PharmacyHistory phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurchaseMade() {
        return purchaseMade;
    }

    public PharmacyHistory purchaseMade(String purchaseMade) {
        this.purchaseMade = purchaseMade;
        return this;
    }

    public void setPurchaseMade(String purchaseMade) {
        this.purchaseMade = purchaseMade;
    }

    public LocalDate getDate() {
        return date;
    }

    public PharmacyHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PharmacyHistory)) {
            return false;
        }
        return id != null && id.equals(((PharmacyHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PharmacyHistory{" +
            "id=" + getId() +
            ", idPharmacyHistory=" + getIdPharmacyHistory() +
            ", code='" + getCode() + "'" +
            ", client='" + getClient() + "'" +
            ", phone='" + getPhone() + "'" +
            ", purchaseMade='" + getPurchaseMade() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
