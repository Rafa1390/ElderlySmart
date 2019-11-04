package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.ProviderHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProviderHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderHistoryRepository extends JpaRepository<ProviderHistory, Long> {

}
