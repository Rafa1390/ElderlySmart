package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Prescription.
 */
@Entity
@Table(name = "prescription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_prescription")
    private Integer idPrescription;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "patient_age")
    private Integer patientAge;

    @Column(name = "prescription_drugs")
    private String prescriptionDrugs;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JsonIgnoreProperties("prescriptions")
    private Pharmacy pharmacy;

    @ManyToOne
    @JsonIgnoreProperties("prescriptions")
    private Doctor doctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPrescription() {
        return idPrescription;
    }

    public Prescription idPrescription(Integer idPrescription) {
        this.idPrescription = idPrescription;
        return this;
    }

    public void setIdPrescription(Integer idPrescription) {
        this.idPrescription = idPrescription;
    }

    public String getOfficeName() {
        return officeName;
    }

    public Prescription officeName(String officeName) {
        this.officeName = officeName;
        return this;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Prescription creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Prescription doctorName(String doctorName) {
        this.doctorName = doctorName;
        return this;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public Prescription patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public Prescription patientAge(Integer patientAge) {
        this.patientAge = patientAge;
        return this;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPrescriptionDrugs() {
        return prescriptionDrugs;
    }

    public Prescription prescriptionDrugs(String prescriptionDrugs) {
        this.prescriptionDrugs = prescriptionDrugs;
        return this;
    }

    public void setPrescriptionDrugs(String prescriptionDrugs) {
        this.prescriptionDrugs = prescriptionDrugs;
    }

    public String getState() {
        return state;
    }

    public Prescription state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public Prescription pharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
        return this;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Prescription doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prescription)) {
            return false;
        }
        return id != null && id.equals(((Prescription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + getId() +
            ", idPrescription=" + getIdPrescription() +
            ", officeName='" + getOfficeName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", doctorName='" + getDoctorName() + "'" +
            ", patientName='" + getPatientName() + "'" +
            ", patientAge=" + getPatientAge() +
            ", prescriptionDrugs='" + getPrescriptionDrugs() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
