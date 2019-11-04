package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_doctor")
    private String idDoctor;

    @Column(name = "email")
    private String email;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Prescription> prescriptions = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MedicalAppointment> medicalAppointments = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "doctor_elderly",
               joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "elderly_id", referencedColumnName = "id"))
    private Set<Elderly> elderlies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public Doctor idDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
        return this;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getEmail() {
        return email;
    }

    public Doctor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeName() {
        return officeName;
    }

    public Doctor officeName(String officeName) {
        this.officeName = officeName;
        return this;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getAddress() {
        return address;
    }

    public Doctor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Doctor userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Doctor prescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    public Doctor addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
        prescription.setDoctor(this);
        return this;
    }

    public Doctor removePrescription(Prescription prescription) {
        this.prescriptions.remove(prescription);
        prescription.setDoctor(null);
        return this;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Set<MedicalAppointment> getMedicalAppointments() {
        return medicalAppointments;
    }

    public Doctor medicalAppointments(Set<MedicalAppointment> medicalAppointments) {
        this.medicalAppointments = medicalAppointments;
        return this;
    }

    public Doctor addMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointments.add(medicalAppointment);
        medicalAppointment.setDoctor(this);
        return this;
    }

    public Doctor removeMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointments.remove(medicalAppointment);
        medicalAppointment.setDoctor(null);
        return this;
    }

    public void setMedicalAppointments(Set<MedicalAppointment> medicalAppointments) {
        this.medicalAppointments = medicalAppointments;
    }

    public Set<Elderly> getElderlies() {
        return elderlies;
    }

    public Doctor elderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
        return this;
    }

    public Doctor addElderly(Elderly elderly) {
        this.elderlies.add(elderly);
        elderly.getDoctors().add(this);
        return this;
    }

    public Doctor removeElderly(Elderly elderly) {
        this.elderlies.remove(elderly);
        elderly.getDoctors().remove(this);
        return this;
    }

    public void setElderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", idDoctor='" + getIdDoctor() + "'" +
            ", email='" + getEmail() + "'" +
            ", officeName='" + getOfficeName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
