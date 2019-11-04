package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProviderHistory.
 */
@Entity
@Table(name = "provider_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProviderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_provider_history")
    private Integer idProviderHistory;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Provider provider;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProviderHistory() {
        return idProviderHistory;
    }

    public ProviderHistory idProviderHistory(Integer idProviderHistory) {
        this.idProviderHistory = idProviderHistory;
        return this;
    }

    public void setIdProviderHistory(Integer idProviderHistory) {
        this.idProviderHistory = idProviderHistory;
    }

    public String getCode() {
        return code;
    }

    public ProviderHistory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient() {
        return client;
    }

    public ProviderHistory client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone() {
        return phone;
    }

    public ProviderHistory phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPurchaseMade() {
        return purchaseMade;
    }

    public ProviderHistory purchaseMade(String purchaseMade) {
        this.purchaseMade = purchaseMade;
        return this;
    }

    public void setPurchaseMade(String purchaseMade) {
        this.purchaseMade = purchaseMade;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProviderHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Provider getProvider() {
        return provider;
    }

    public ProviderHistory provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProviderHistory)) {
            return false;
        }
        return id != null && id.equals(((ProviderHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProviderHistory{" +
            "id=" + getId() +
            ", idProviderHistory=" + getIdProviderHistory() +
            ", code='" + getCode() + "'" +
            ", client='" + getClient() + "'" +
            ", phone='" + getPhone() + "'" +
            ", purchaseMade='" + getPurchaseMade() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
