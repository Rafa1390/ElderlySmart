package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Prescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
