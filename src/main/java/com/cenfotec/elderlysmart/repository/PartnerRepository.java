package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.Partner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Partner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

}
