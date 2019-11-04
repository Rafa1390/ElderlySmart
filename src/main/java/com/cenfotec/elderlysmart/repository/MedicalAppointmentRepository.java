package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.MedicalAppointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalAppointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {

}
