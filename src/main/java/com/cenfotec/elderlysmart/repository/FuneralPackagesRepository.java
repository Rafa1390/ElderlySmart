package com.cenfotec.elderlysmart.repository;
import com.cenfotec.elderlysmart.domain.FuneralPackages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FuneralPackages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuneralPackagesRepository extends JpaRepository<FuneralPackages, Long> {

}
