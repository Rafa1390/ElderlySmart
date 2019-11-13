package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A CaseFile.
 */
@Entity
@Table(name = "case_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CaseFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_case_file")
    private Integer idCaseFile;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "full_name_elderly")
    private String fullNameElderly;

    @Column(name = "age")
    private Integer age;

    @Column(name = "religion")
    private String religion;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "weight")
    private String weight;

    @Column(name = "height")
    private String height;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "resuscitation")
    private String resuscitation;

    @Column(name = "organ_donation")
    private String organDonation;

    @Column(name = "state")
    private String state;

    @OneToOne
    @JoinColumn(unique = true)
    private Elderly idElderly;

    @OneToMany(mappedBy = "caseFile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Allergies> alergies = new HashSet<>();

    @OneToMany(mappedBy = "caseFile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pathologies> pathologies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCaseFile() {
        return idCaseFile;
    }

    public CaseFile idCaseFile(Integer idCaseFile) {
        this.idCaseFile = idCaseFile;
        return this;
    }

    public void setIdCaseFile(Integer idCaseFile) {
        this.idCaseFile = idCaseFile;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public CaseFile creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getFullNameElderly() {
        return fullNameElderly;
    }

    public CaseFile fullNameElderly(String fullNameElderly) {
        this.fullNameElderly = fullNameElderly;
        return this;
    }

    public void setFullNameElderly(String fullNameElderly) {
        this.fullNameElderly = fullNameElderly;
    }

    public Integer getAge() {
        return age;
    }

    public CaseFile age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getReligion() {
        return religion;
    }

    public CaseFile religion(String religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNationality() {
        return nationality;
    }

    public CaseFile nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getWeight() {
        return weight;
    }

    public CaseFile weight(String weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public CaseFile height(String height) {
        this.height = height;
        return this;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public CaseFile birth(LocalDate birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public CaseFile gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public CaseFile bloodType(String bloodType) {
        this.bloodType = bloodType;
        return this;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getResuscitation() {
        return resuscitation;
    }

    public CaseFile resuscitation(String resuscitation) {
        this.resuscitation = resuscitation;
        return this;
    }

    public void setResuscitation(String resuscitation) {
        this.resuscitation = resuscitation;
    }

    public String getOrganDonation() {
        return organDonation;
    }

    public CaseFile organDonation(String organDonation) {
        this.organDonation = organDonation;
        return this;
    }

    public void setOrganDonation(String organDonation) {
        this.organDonation = organDonation;
    }

    public String getState() {
        return state;
    }

    public CaseFile state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Elderly getIdElderly() {
        return idElderly;
    }

    public CaseFile idElderly(Elderly elderly) {
        this.idElderly = elderly;
        return this;
    }

    public void setIdElderly(Elderly elderly) {
        this.idElderly = elderly;
    }

    public Set<Allergies> getAlergies() {
        return alergies;
    }

    public CaseFile alergies(Set<Allergies> allergies) {
        this.alergies = allergies;
        return this;
    }

    public CaseFile addAlergies(Allergies allergies) {
        this.alergies.add(allergies);
        allergies.setCaseFile(this);
        return this;
    }

    public CaseFile removeAlergies(Allergies allergies) {
        this.alergies.remove(allergies);
        allergies.setCaseFile(null);
        return this;
    }

    public void setAlergies(Set<Allergies> allergies) {
        this.alergies = allergies;
    }

    public Set<Pathologies> getPathologies() {
        return pathologies;
    }

    public CaseFile pathologies(Set<Pathologies> pathologies) {
        this.pathologies = pathologies;
        return this;
    }

    public CaseFile addPathologies(Pathologies pathologies) {
        this.pathologies.add(pathologies);
        pathologies.setCaseFile(this);
        return this;
    }

    public CaseFile removePathologies(Pathologies pathologies) {
        this.pathologies.remove(pathologies);
        pathologies.setCaseFile(null);
        return this;
    }

    public void setPathologies(Set<Pathologies> pathologies) {
        this.pathologies = pathologies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CaseFile)) {
            return false;
        }
        return id != null && id.equals(((CaseFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CaseFile{" +
            "id=" + getId() +
            ", idCaseFile=" + getIdCaseFile() +
            ", creationDate='" + getCreationDate() + "'" +
            ", fullNameElderly='" + getFullNameElderly() + "'" +
            ", age=" + getAge() +
            ", religion='" + getReligion() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", weight='" + getWeight() + "'" +
            ", height='" + getHeight() + "'" +
            ", birth='" + getBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", bloodType='" + getBloodType() + "'" +
            ", resuscitation='" + getResuscitation() + "'" +
            ", organDonation='" + getOrganDonation() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
