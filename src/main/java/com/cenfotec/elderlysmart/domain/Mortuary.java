package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Mortuary.
 */
@Entity
@Table(name = "mortuary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mortuary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_mortuary")
    private String idMortuary;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @OneToMany(mappedBy = "mortuary")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FuneralPackages> funeralPackages = new HashSet<>();

    @ManyToMany(mappedBy = "mortuaries")
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

    public String getIdMortuary() {
        return idMortuary;
    }

    public Mortuary idMortuary(String idMortuary) {
        this.idMortuary = idMortuary;
        return this;
    }

    public void setIdMortuary(String idMortuary) {
        this.idMortuary = idMortuary;
    }

    public String getEmail() {
        return email;
    }

    public Mortuary email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Mortuary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Mortuary address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Mortuary userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<FuneralPackages> getFuneralPackages() {
        return funeralPackages;
    }

    public Mortuary funeralPackages(Set<FuneralPackages> funeralPackages) {
        this.funeralPackages = funeralPackages;
        return this;
    }

    public Mortuary addFuneralPackages(FuneralPackages funeralPackages) {
        this.funeralPackages.add(funeralPackages);
        funeralPackages.setMortuary(this);
        return this;
    }

    public Mortuary removeFuneralPackages(FuneralPackages funeralPackages) {
        this.funeralPackages.remove(funeralPackages);
        funeralPackages.setMortuary(null);
        return this;
    }

    public void setFuneralPackages(Set<FuneralPackages> funeralPackages) {
        this.funeralPackages = funeralPackages;
    }

    public Set<Asylum> getAsylums() {
        return asylums;
    }

    public Mortuary asylums(Set<Asylum> asylums) {
        this.asylums = asylums;
        return this;
    }

    public Mortuary addAsylum(Asylum asylum) {
        this.asylums.add(asylum);
        asylum.getMortuaries().add(this);
        return this;
    }

    public Mortuary removeAsylum(Asylum asylum) {
        this.asylums.remove(asylum);
        asylum.getMortuaries().remove(this);
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
        if (!(o instanceof Mortuary)) {
            return false;
        }
        return id != null && id.equals(((Mortuary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mortuary{" +
            "id=" + getId() +
            ", idMortuary='" + getIdMortuary() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
