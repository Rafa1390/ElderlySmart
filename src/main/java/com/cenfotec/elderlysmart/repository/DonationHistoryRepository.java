package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.DonationHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DonationHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

}
