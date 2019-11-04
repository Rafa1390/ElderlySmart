package com.cenfotec.elderlysmart.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A InventoryPharmacy.
 */
@Entity
@Table(name = "inventory_pharmacy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventoryPharmacy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_inventory_pharmacy")
    private String idInventoryPharmacy;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "purchase_price")
    private Integer purchasePrice;

    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "cuantity")
    private Integer cuantity;

    @OneToOne
    @JoinColumn(unique = true)
    private Pharmacy pharmacy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdInventoryPharmacy() {
        return idInventoryPharmacy;
    }

    public InventoryPharmacy idInventoryPharmacy(String idInventoryPharmacy) {
        this.idInventoryPharmacy = idInventoryPharmacy;
        return this;
    }

    public void setIdInventoryPharmacy(String idInventoryPharmacy) {
        this.idInventoryPharmacy = idInventoryPharmacy;
    }

    public String getCode() {
        return code;
    }

    public InventoryPharmacy code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public InventoryPharmacy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public InventoryPharmacy purchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public InventoryPharmacy salePrice(Integer salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getCuantity() {
        return cuantity;
    }

    public InventoryPharmacy cuantity(Integer cuantity) {
        this.cuantity = cuantity;
        return this;
    }

    public void setCuantity(Integer cuantity) {
        this.cuantity = cuantity;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public InventoryPharmacy pharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
        return this;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryPharmacy)) {
            return false;
        }
        return id != null && id.equals(((InventoryPharmacy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InventoryPharmacy{" +
            "id=" + getId() +
            ", idInventoryPharmacy='" + getIdInventoryPharmacy() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", purchasePrice=" + getPurchasePrice() +
            ", salePrice=" + getSalePrice() +
            ", cuantity=" + getCuantity() +
            "}";
    }
}
