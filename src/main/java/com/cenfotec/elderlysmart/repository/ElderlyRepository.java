package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Elderly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Elderly entity.
 */
@Repository
public interface ElderlyRepository extends JpaRepository<Elderly, Long> {

    @Query(value = "select distinct elderly from Elderly elderly left join fetch elderly.families",
        countQuery = "select count(distinct elderly) from Elderly elderly")
    Page<Elderly> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct elderly from Elderly elderly left join fetch elderly.families")
    List<Elderly> findAllWithEagerRelationships();

    @Query("select elderly from Elderly elderly left join fetch elderly.families where elderly.id =:id")
    Optional<Elderly> findOneWithEagerRelationships(@Param("id") Long id);

}
