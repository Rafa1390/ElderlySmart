package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.AppointmentNotification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AppointmentNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentNotificationRepository extends JpaRepository<AppointmentNotification, Long> {

}
