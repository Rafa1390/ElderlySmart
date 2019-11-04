package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_employee")
    private Integer idEmployee;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Asylum asylum;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Elderly> elderlies = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MedicalAppointment> medicalAppointments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public Employee idEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
        return this;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getAddress() {
        return address;
    }

    public Employee address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Asylum getAsylum() {
        return asylum;
    }

    public Employee asylum(Asylum asylum) {
        this.asylum = asylum;
        return this;
    }

    public void setAsylum(Asylum asylum) {
        this.asylum = asylum;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Employee userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Elderly> getElderlies() {
        return elderlies;
    }

    public Employee elderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
        return this;
    }

    public Employee addElderly(Elderly elderly) {
        this.elderlies.add(elderly);
        elderly.setEmployee(this);
        return this;
    }

    public Employee removeElderly(Elderly elderly) {
        this.elderlies.remove(elderly);
        elderly.setEmployee(null);
        return this;
    }

    public void setElderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
    }

    public Set<MedicalAppointment> getMedicalAppointments() {
        return medicalAppointments;
    }

    public Employee medicalAppointments(Set<MedicalAppointment> medicalAppointments) {
        this.medicalAppointments = medicalAppointments;
        return this;
    }

    public Employee addMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointments.add(medicalAppointment);
        medicalAppointment.setEmployee(this);
        return this;
    }

    public Employee removeMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointments.remove(medicalAppointment);
        medicalAppointment.setEmployee(null);
        return this;
    }

    public void setMedicalAppointments(Set<MedicalAppointment> medicalAppointments) {
        this.medicalAppointments = medicalAppointments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", idEmployee=" + getIdEmployee() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
