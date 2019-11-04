package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.InventoryProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the InventoryProvider entity.
 */
@Repository
public interface InventoryProviderRepository extends JpaRepository<InventoryProvider, Long> {

    @Query(value = "select distinct inventoryProvider from InventoryProvider inventoryProvider left join fetch inventoryProvider.products",
        countQuery = "select count(distinct inventoryProvider) from InventoryProvider inventoryProvider")
    Page<InventoryProvider> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct inventoryProvider from InventoryProvider inventoryProvider left join fetch inventoryProvider.products")
    List<InventoryProvider> findAllWithEagerRelationships();

    @Query("select inventoryProvider from InventoryProvider inventoryProvider left join fetch inventoryProvider.products where inventoryProvider.id =:id")
    Optional<InventoryProvider> findOneWithEagerRelationships(@Param("id") Long id);

}
