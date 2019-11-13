package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Partner.
 */
@Entity
@Table(name = "partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_partner")
    private Integer idPartner;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "partner_asylum",
               joinColumns = @JoinColumn(name = "partner_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "asylum_id", referencedColumnName = "id"))
    private Set<Asylum> asylums = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPartner() {
        return idPartner;
    }

    public Partner idPartner(Integer idPartner) {
        this.idPartner = idPartner;
        return this;
    }

    public void setIdPartner(Integer idPartner) {
        this.idPartner = idPartner;
    }

    public String getAddress() {
        return address;
    }

    public Partner address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Partner userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Asylum> getAsylums() {
        return asylums;
    }

    public Partner asylums(Set<Asylum> asylums) {
        this.asylums = asylums;
        return this;
    }

    public Partner addAsylum(Asylum asylum) {
        this.asylums.add(asylum);
        asylum.getPartners().add(this);
        return this;
    }

    public Partner removeAsylum(Asylum asylum) {
        this.asylums.remove(asylum);
        asylum.getPartners().remove(this);
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
        if (!(o instanceof Partner)) {
            return false;
        }
        return id != null && id.equals(((Partner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + getId() +
            ", idPartner=" + getIdPartner() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
