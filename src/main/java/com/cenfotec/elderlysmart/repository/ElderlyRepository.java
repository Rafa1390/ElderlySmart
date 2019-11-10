package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Elderly;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Elderly entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElderlyRepository extends JpaRepository<Elderly, Long> {

}
