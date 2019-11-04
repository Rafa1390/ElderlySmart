package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.MedicationSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicationSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Long> {

}
