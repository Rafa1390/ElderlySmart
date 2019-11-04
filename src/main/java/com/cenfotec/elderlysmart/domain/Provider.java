package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification")
    private String identification;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @ManyToMany(mappedBy = "providers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Pharmacy> pharmacies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public Provider identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public Provider email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Provider name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderType() {
        return providerType;
    }

    public Provider providerType(String providerType) {
        this.providerType = providerType;
        return this;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getAddress() {
        return address;
    }

    public Provider address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Provider userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public Provider pharmacies(Set<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
        return this;
    }

    public Provider addPharmacy(Pharmacy pharmacy) {
        this.pharmacies.add(pharmacy);
        pharmacy.getProviders().add(this);
        return this;
    }

    public Provider removePharmacy(Pharmacy pharmacy) {
        this.pharmacies.remove(pharmacy);
        pharmacy.getProviders().remove(this);
        return this;
    }

    public void setPharmacies(Set<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provider)) {
            return false;
        }
        return id != null && id.equals(((Provider) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", identification='" + getIdentification() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", providerType='" + getProviderType() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
