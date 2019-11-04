package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.FuneralHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FuneralHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuneralHistoryRepository extends JpaRepository<FuneralHistory, Long> {

}
