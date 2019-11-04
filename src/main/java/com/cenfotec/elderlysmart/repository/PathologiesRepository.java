package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Pathologies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pathologies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathologiesRepository extends JpaRepository<Pathologies, Long> {

}
