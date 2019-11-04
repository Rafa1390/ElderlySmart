package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DonationHistory.
 */
@Entity
@Table(name = "donation_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DonationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_donation_history")
    private Integer idDonationHistory;

    @Column(name = "phone")
    private String phone;

    @Column(name = "donation_made")
    private Integer donationMade;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(unique = true)
    private Partner partner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDonationHistory() {
        return idDonationHistory;
    }

    public DonationHistory idDonationHistory(Integer idDonationHistory) {
        this.idDonationHistory = idDonationHistory;
        return this;
    }

    public void setIdDonationHistory(Integer idDonationHistory) {
        this.idDonationHistory = idDonationHistory;
    }

    public String getPhone() {
        return phone;
    }

    public DonationHistory phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDonationMade() {
        return donationMade;
    }

    public DonationHistory donationMade(Integer donationMade) {
        this.donationMade = donationMade;
        return this;
    }

    public void setDonationMade(Integer donationMade) {
        this.donationMade = donationMade;
    }

    public LocalDate getDate() {
        return date;
    }

    public DonationHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Partner getPartner() {
        return partner;
    }

    public DonationHistory partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationHistory)) {
            return false;
        }
        return id != null && id.equals(((DonationHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DonationHistory{" +
            "id=" + getId() +
            ", idDonationHistory=" + getIdDonationHistory() +
            ", phone='" + getPhone() + "'" +
            ", donationMade=" + getDonationMade() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
