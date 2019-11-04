package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pharmacy.
 */
@Entity
@Table(name = "pharmacy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pharmacy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pharmacy")
    private String idPharmacy;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @OneToMany(mappedBy = "pharmacy")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Prescription> prescriptions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pharmacy_provider",
               joinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "provider_id", referencedColumnName = "id"))
    private Set<Provider> providers = new HashSet<>();

    @ManyToMany(mappedBy = "pharmacies")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Asylum> asylums = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPharmacy() {
        return idPharmacy;
    }

    public Pharmacy idPharmacy(String idPharmacy) {
        this.idPharmacy = idPharmacy;
        return this;
    }

    public void setIdPharmacy(String idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    public String getEmail() {
        return email;
    }

    public Pharmacy email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Pharmacy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Pharmacy address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Pharmacy userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Pharmacy prescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    public Pharmacy addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
        prescription.setPharmacy(this);
        return this;
    }

    public Pharmacy removePrescription(Prescription prescription) {
        this.prescriptions.remove(prescription);
        prescription.setPharmacy(null);
        return this;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Set<Provider> getProviders() {
        return providers;
    }

    public Pharmacy providers(Set<Provider> providers) {
        this.providers = providers;
        return this;
    }

    public Pharmacy addProvider(Provider provider) {
        this.providers.add(provider);
        provider.getPharmacies().add(this);
        return this;
    }

    public Pharmacy removeProvider(Provider provider) {
        this.providers.remove(provider);
        provider.getPharmacies().remove(this);
        return this;
    }

    public void setProviders(Set<Provider> providers) {
        this.providers = providers;
    }

    public Set<Asylum> getAsylums() {
        return asylums;
    }

    public Pharmacy asylums(Set<Asylum> asylums) {
        this.asylums = asylums;
        return this;
    }

    public Pharmacy addAsylum(Asylum asylum) {
        this.asylums.add(asylum);
        asylum.getPharmacies().add(this);
        return this;
    }

    public Pharmacy removeAsylum(Asylum asylum) {
        this.asylums.remove(asylum);
        asylum.getPharmacies().remove(this);
        return this;
    }

    public void setAsylums(Set<Asylum> asylums) {
        this.asylums = asylums;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pharmacy)) {
            return false;
        }
        return id != null && id.equals(((Pharmacy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
            "id=" + getId() +
            ", idPharmacy='" + getIdPharmacy() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
