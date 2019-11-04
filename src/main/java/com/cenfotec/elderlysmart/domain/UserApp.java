package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserApp.
 */
@Entity
@Table(name = "user_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification")
    private String identification;

    @Column(name = "id_type_user")
    private Integer idTypeUser;

    @Column(name = "email")
    private String email;

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

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private String state;

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

    public UserApp identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Integer getIdTypeUser() {
        return idTypeUser;
    }

    public UserApp idTypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
        return this;
    }

    public void setIdTypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getEmail() {
        return email;
    }

    public UserApp email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public UserApp name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public UserApp name2(String name2) {
        this.name2 = name2;
        return this;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLastName() {
        return lastName;
    }

    public UserApp lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName2() {
        return lastName2;
    }

    public UserApp lastName2(String lastName2) {
        this.lastName2 = lastName2;
        return this;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getPhone1() {
        return phone1;
    }

    public UserApp phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public UserApp phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Integer getAge() {
        return age;
    }

    public UserApp age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public UserApp password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public UserApp state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApp)) {
            return false;
        }
        return id != null && id.equals(((UserApp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserApp{" +
            "id=" + getId() +
            ", identification='" + getIdentification() + "'" +
            ", idTypeUser=" + getIdTypeUser() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", name2='" + getName2() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", lastName2='" + getLastName2() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", age=" + getAge() +
            ", password='" + getPassword() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
