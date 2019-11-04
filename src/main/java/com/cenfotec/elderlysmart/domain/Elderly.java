package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Elderly.
 */
@Entity
@Table(name = "elderly")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Elderly implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_elderly")
    private Integer idElderly;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "address")
    private String address;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JsonIgnoreProperties("elderlies")
    private Employee employee;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "elderly_family",
               joinColumns = @JoinColumn(name = "elderly_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id"))
    private Set<Family> families = new HashSet<>();

    @ManyToMany(mappedBy = "elderlies")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Doctor> doctors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdElderly() {
        return idElderly;
    }

    public Elderly idElderly(Integer idElderly) {
        this.idElderly = idElderly;
        return this;
    }

    public void setIdElderly(Integer idElderly) {
        this.idElderly = idElderly;
    }

    public String getNationality() {
        return nationality;
    }

    public Elderly nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public Elderly address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public Elderly admissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
        return this;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getState() {
        return state;
    }

    public Elderly state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Elderly employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Elderly userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Family> getFamilies() {
        return families;
    }

    public Elderly families(Set<Family> families) {
        this.families = families;
        return this;
    }

    public Elderly addFamily(Family family) {
        this.families.add(family);
        family.getElderlies().add(this);
        return this;
    }

    public Elderly removeFamily(Family family) {
        this.families.remove(family);
        family.getElderlies().remove(this);
        return this;
    }

    public void setFamilies(Set<Family> families) {
        this.families = families;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Elderly doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public Elderly addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.getElderlies().add(this);
        return this;
    }

    public Elderly removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.getElderlies().remove(this);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Elderly)) {
            return false;
        }
        return id != null && id.equals(((Elderly) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Elderly{" +
            "id=" + getId() +
            ", idElderly=" + getIdElderly() +
            ", nationality='" + getNationality() + "'" +
            ", address='" + getAddress() + "'" +
            ", admissionDate='" + getAdmissionDate() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
