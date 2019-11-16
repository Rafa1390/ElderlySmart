package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Mortuary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Mortuary entity.
 */
@Repository
public interface MortuaryRepository extends JpaRepository<Mortuary, Long> {

    @Query(value = "select distinct mortuary from Mortuary mortuary left join fetch mortuary.asylums",
        countQuery = "select count(distinct mortuary) from Mortuary mortuary")
    Page<Mortuary> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct mortuary from Mortuary mortuary left join fetch mortuary.asylums")
    List<Mortuary> findAllWithEagerRelationships();

    @Query("select mortuary from Mortuary mortuary left join fetch mortuary.asylums where mortuary.id =:id")
    Optional<Mortuary> findOneWithEagerRelationships(@Param("id") Long id);

}
