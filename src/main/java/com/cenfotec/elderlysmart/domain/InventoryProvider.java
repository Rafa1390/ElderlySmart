package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A InventoryProvider.
 */
@Entity
@Table(name = "inventory_provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventoryProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_inventory_provider")
    private String idInventoryProvider;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "cuantity")
    private Integer cuantity;

    @OneToOne
    @JoinColumn(unique = true)
    private Provider provider;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "inventory_provider_product",
               joinColumns = @JoinColumn(name = "inventory_provider_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdInventoryProvider() {
        return idInventoryProvider;
    }

    public InventoryProvider idInventoryProvider(String idInventoryProvider) {
        this.idInventoryProvider = idInventoryProvider;
        return this;
    }

    public void setIdInventoryProvider(String idInventoryProvider) {
        this.idInventoryProvider = idInventoryProvider;
    }

    public String getCode() {
        return code;
    }

    public InventoryProvider code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public InventoryProvider name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public InventoryProvider price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCuantity() {
        return cuantity;
    }

    public InventoryProvider cuantity(Integer cuantity) {
        this.cuantity = cuantity;
        return this;
    }

    public void setCuantity(Integer cuantity) {
        this.cuantity = cuantity;
    }

    public Provider getProvider() {
        return provider;
    }

    public InventoryProvider provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public InventoryProvider products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public InventoryProvider addProduct(Product product) {
        this.products.add(product);
        product.getInventoryProviders().add(this);
        return this;
    }

    public InventoryProvider removeProduct(Product product) {
        this.products.remove(product);
        product.getInventoryProviders().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryProvider)) {
            return false;
        }
        return id != null && id.equals(((InventoryProvider) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InventoryProvider{" +
            "id=" + getId() +
            ", idInventoryProvider='" + getIdInventoryProvider() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", cuantity=" + getCuantity() +
            "}";
    }
}
