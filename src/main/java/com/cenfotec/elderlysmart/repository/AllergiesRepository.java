package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Allergies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Allergies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergiesRepository extends JpaRepository<Allergies, Long> {

}
