package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.InventoryPharmacy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InventoryPharmacy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryPharmacyRepository extends JpaRepository<InventoryPharmacy, Long> {

}
