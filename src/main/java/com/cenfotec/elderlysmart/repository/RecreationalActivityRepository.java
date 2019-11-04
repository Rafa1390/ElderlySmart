package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.RecreationalActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecreationalActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecreationalActivityRepository extends JpaRepository<RecreationalActivity, Long> {

}
