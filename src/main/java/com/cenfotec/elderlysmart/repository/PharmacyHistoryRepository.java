package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.PharmacyHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PharmacyHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmacyHistoryRepository extends JpaRepository<PharmacyHistory, Long> {

}
