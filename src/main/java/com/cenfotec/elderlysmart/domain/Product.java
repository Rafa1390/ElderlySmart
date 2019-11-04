package com.cenfotec.elderlysmart.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_product")
    private Integer idProduct;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private Integer type;

    @Column(name = "state")
    private String state;

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<InventoryProvider> inventoryProviders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public Product idProduct(Integer idProduct) {
        this.idProduct = idProduct;
        return this;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getCode() {
        return code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public Product type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public Product state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<InventoryProvider> getInventoryProviders() {
        return inventoryProviders;
    }

    public Product inventoryProviders(Set<InventoryProvider> inventoryProviders) {
        this.inventoryProviders = inventoryProviders;
        return this;
    }

    public Product addInventoryProvider(InventoryProvider inventoryProvider) {
        this.inventoryProviders.add(inventoryProvider);
        inventoryProvider.getProducts().add(this);
        return this;
    }

    public Product removeInventoryProvider(InventoryProvider inventoryProvider) {
        this.inventoryProviders.remove(inventoryProvider);
        inventoryProvider.getProducts().remove(this);
        return this;
    }

    public void setInventoryProviders(Set<InventoryProvider> inventoryProviders) {
        this.inventoryProviders = inventoryProviders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", idProduct=" + getIdProduct() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", description='" + getDescription() + "'" +
            ", type=" + getType() +
            ", state='" + getState() + "'" +
            "}";
    }
}
