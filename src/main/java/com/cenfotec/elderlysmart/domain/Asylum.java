package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Asylum.
 */
@Entity
@Table(name = "asylum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Asylum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification")
    private String identification;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userApp;

    @OneToMany(mappedBy = "asylum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CleaningProgram> cleaningPrograms = new HashSet<>();

    @OneToMany(mappedBy = "asylum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FoodMenu> foodMenus = new HashSet<>();

    @OneToMany(mappedBy = "asylum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RecreationalActivity> recreationalActivities = new HashSet<>();

    @OneToMany(mappedBy = "asylum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "asylum_mortuary",
               joinColumns = @JoinColumn(name = "asylum_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "mortuary_id", referencedColumnName = "id"))
    private Set<Mortuary> mortuaries = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "asylum_pharmacy",
               joinColumns = @JoinColumn(name = "asylum_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> pharmacies = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "asylum_partner",
               joinColumns = @JoinColumn(name = "asylum_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "partner_id", referencedColumnName = "id"))
    private Set<Partner> partners = new HashSet<>();

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

    public Asylum identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public Asylum email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Asylum name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Asylum address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Asylum userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<CleaningProgram> getCleaningPrograms() {
        return cleaningPrograms;
    }

    public Asylum cleaningPrograms(Set<CleaningProgram> cleaningPrograms) {
        this.cleaningPrograms = cleaningPrograms;
        return this;
    }

    public Asylum addCleaningProgram(CleaningProgram cleaningProgram) {
        this.cleaningPrograms.add(cleaningProgram);
        cleaningProgram.setAsylum(this);
        return this;
    }

    public Asylum removeCleaningProgram(CleaningProgram cleaningProgram) {
        this.cleaningPrograms.remove(cleaningProgram);
        cleaningProgram.setAsylum(null);
        return this;
    }

    public void setCleaningPrograms(Set<CleaningProgram> cleaningPrograms) {
        this.cleaningPrograms = cleaningPrograms;
    }

    public Set<FoodMenu> getFoodMenus() {
        return foodMenus;
    }

    public Asylum foodMenus(Set<FoodMenu> foodMenus) {
        this.foodMenus = foodMenus;
        return this;
    }

    public Asylum addFoodMenu(FoodMenu foodMenu) {
        this.foodMenus.add(foodMenu);
        foodMenu.setAsylum(this);
        return this;
    }

    public Asylum removeFoodMenu(FoodMenu foodMenu) {
        this.foodMenus.remove(foodMenu);
        foodMenu.setAsylum(null);
        return this;
    }

    public void setFoodMenus(Set<FoodMenu> foodMenus) {
        this.foodMenus = foodMenus;
    }

    public Set<RecreationalActivity> getRecreationalActivities() {
        return recreationalActivities;
    }

    public Asylum recreationalActivities(Set<RecreationalActivity> recreationalActivities) {
        this.recreationalActivities = recreationalActivities;
        return this;
    }

    public Asylum addRecreationalActivity(RecreationalActivity recreationalActivity) {
        this.recreationalActivities.add(recreationalActivity);
        recreationalActivity.setAsylum(this);
        return this;
    }

    public Asylum removeRecreationalActivity(RecreationalActivity recreationalActivity) {
        this.recreationalActivities.remove(recreationalActivity);
        recreationalActivity.setAsylum(null);
        return this;
    }

    public void setRecreationalActivities(Set<RecreationalActivity> recreationalActivities) {
        this.recreationalActivities = recreationalActivities;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Asylum employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Asylum addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setAsylum(this);
        return this;
    }

    public Asylum removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setAsylum(null);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Mortuary> getMortuaries() {
        return mortuaries;
    }

    public Asylum mortuaries(Set<Mortuary> mortuaries) {
        this.mortuaries = mortuaries;
        return this;
    }

    public Asylum addMortuary(Mortuary mortuary) {
        this.mortuaries.add(mortuary);
        mortuary.getAsylums().add(this);
        return this;
    }

    public Asylum removeMortuary(Mortuary mortuary) {
        this.mortuaries.remove(mortuary);
        mortuary.getAsylums().remove(this);
        return this;
    }

    public void setMortuaries(Set<Mortuary> mortuaries) {
        this.mortuaries = mortuaries;
    }

    public Set<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public Asylum pharmacies(Set<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
        return this;
    }

    public Asylum addPharmacy(Pharmacy pharmacy) {
        this.pharmacies.add(pharmacy);
        pharmacy.getAsylums().add(this);
        return this;
    }

    public Asylum removePharmacy(Pharmacy pharmacy) {
        this.pharmacies.remove(pharmacy);
        pharmacy.getAsylums().remove(this);
        return this;
    }

    public void setPharmacies(Set<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }

    public Set<Partner> getPartners() {
        return partners;
    }

    public Asylum partners(Set<Partner> partners) {
        this.partners = partners;
        return this;
    }

    public Asylum addPartner(Partner partner) {
        this.partners.add(partner);
        partner.getAsylums().add(this);
        return this;
    }

    public Asylum removePartner(Partner partner) {
        this.partners.remove(partner);
        partner.getAsylums().remove(this);
        return this;
    }

    public void setPartners(Set<Partner> partners) {
        this.partners = partners;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asylum)) {
            return false;
        }
        return id != null && id.equals(((Asylum) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Asylum{" +
            "id=" + getId() +
            ", identification='" + getIdentification() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
