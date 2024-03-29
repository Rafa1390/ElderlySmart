package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.UserApp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {

}
