package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Asylum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Asylum entity.
 */
@Repository
public interface AsylumRepository extends JpaRepository<Asylum, Long> {

    @Query(value = "select distinct asylum from Asylum asylum left join fetch asylum.pharmacies",
        countQuery = "select count(distinct asylum) from Asylum asylum")
    Page<Asylum> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct asylum from Asylum asylum left join fetch asylum.pharmacies")
    List<Asylum> findAllWithEagerRelationships();

    @Query("select asylum from Asylum asylum left join fetch asylum.pharmacies where asylum.id =:id")
    Optional<Asylum> findOneWithEagerRelationships(@Param("id") Long id);

}
