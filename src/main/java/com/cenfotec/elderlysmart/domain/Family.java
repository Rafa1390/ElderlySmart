package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Family.
 */
@Entity
@Table(name = "family")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Family implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_family")
    private String idFamily;

    @Column(name = "name")
    private String name;

    @Column(name = "name_2")
    private String name2;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_name_2")
    private String lastName2;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "age")
    private Integer age;

    @Column(name = "family_relation")
    private String familyRelation;

    @Column(name = "state")
    private String state;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "family_elderly",
               joinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "elderly_id", referencedColumnName = "id"))
    private Set<Elderly> elderlies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFamily() {
        return idFamily;
    }

    public Family idFamily(String idFamily) {
        this.idFamily = idFamily;
        return this;
    }

    public void setIdFamily(String idFamily) {
        this.idFamily = idFamily;
    }

    public String getName() {
        return name;
    }

    public Family name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public Family name2(String name2) {
        this.name2 = name2;
        return this;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLastName() {
        return lastName;
    }

    public Family lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName2() {
        return lastName2;
    }

    public Family lastName2(String lastName2) {
        this.lastName2 = lastName2;
        return this;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getPhone1() {
        return phone1;
    }

    public Family phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Family phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Integer getAge() {
        return age;
    }

    public Family age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public Family familyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
        return this;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public String getState() {
        return state;
    }

    public Family state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<Elderly> getElderlies() {
        return elderlies;
    }

    public Family elderlies(Set<Elderly> elderlies) {
        this.elderlies = elderlies;
        return this;
    }

    public Family addElderly(Elderly elderly) {
        this.elderlies.add(elderly);
        elderly.getFamilies().add(this);
        return this;
    }

    public Family removeElderly(Elderly elderly) {
        this.elderlies.remove(elderly);
        elderly.getFamilies().remove(this);
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
        if (!(o instanceof Family)) {
            return false;
        }
        return id != null && id.equals(((Family) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Family{" +
            "id=" + getId() +
            ", idFamily='" + getIdFamily() + "'" +
            ", name='" + getName() + "'" +
            ", name2='" + getName2() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", lastName2='" + getLastName2() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", age=" + getAge() +
            ", familyRelation='" + getFamilyRelation() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
