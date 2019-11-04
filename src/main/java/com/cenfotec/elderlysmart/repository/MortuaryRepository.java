package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Mortuary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mortuary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MortuaryRepository extends JpaRepository<Mortuary, Long> {

}
