package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.PrescriptionNotification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrescriptionNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescriptionNotificationRepository extends JpaRepository<PrescriptionNotification, Long> {

}
