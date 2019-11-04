package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Pharmacy entity.
 */
@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Query(value = "select distinct pharmacy from Pharmacy pharmacy left join fetch pharmacy.providers",
        countQuery = "select count(distinct pharmacy) from Pharmacy pharmacy")
    Page<Pharmacy> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct pharmacy from Pharmacy pharmacy left join fetch pharmacy.providers")
    List<Pharmacy> findAllWithEagerRelationships();

    @Query("select pharmacy from Pharmacy pharmacy left join fetch pharmacy.providers where pharmacy.id =:id")
    Optional<Pharmacy> findOneWithEagerRelationships(@Param("id") Long id);

}
